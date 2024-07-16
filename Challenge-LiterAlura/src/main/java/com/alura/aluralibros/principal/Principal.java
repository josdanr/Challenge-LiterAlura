package com.alura.aluralibros.principal;

import com.alura.aluralibros.entities.Autor;
import com.alura.aluralibros.entities.Libro;
import com.alura.aluralibros.model.Datos;
import com.alura.aluralibros.model.DatosLibro;
import com.alura.aluralibros.model.Idioma;
import com.alura.aluralibros.repository.IAutorRepository;
import com.alura.aluralibros.repository.ILibroRepository;
import com.alura.aluralibros.services.AutorService;
import com.alura.aluralibros.services.ConsumirApi;
import com.alura.aluralibros.services.ConversorDatosClase;
import com.alura.aluralibros.services.LibrosService;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leer = new Scanner(System.in);
    private ConsumirApi consumirApi = new ConsumirApi();
    private ConversorDatosClase conversor = new ConversorDatosClase();
    private String URL_BASE = "https://gutendex.com/books/";
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private LibrosService serviceLibros;
    private AutorService autorService;


    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository,
                     LibrosService serviceLibros, AutorService autorService) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.serviceLibros = serviceLibros;
        this.autorService = autorService;
    }


    public void mostrarMenu() {

        var opcion = -1;

        System.out.println("-- Bienvenido a la biblioteca!       --");
        while (opcion != 0) {

            String menu = """
                    MENU:
                    1. Añadir un nuevo libro por su titulo.
                    2. Listar los libros registrados.
                    3. Listar todos los autores registrados.
                    4. Filtrar autores vivos, estableciendo el año límite.
                    5. Filtrar libros por idioma.
                                        
                    0. Salir
                    """;

            System.out.println(menu);
            try{
                System.out.print("Digite el valor correspondiente a la acción: ");
                opcion = leer.nextInt();
                leer.nextLine();
            } catch (InputMismatchException e){
                System.out.println("\nParece q digitaste algo mal.\n");
                leer.nextLine();
            }

            switch (opcion) {
                case 1:
                    addLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    filtrarAutoresVivos();
                    break;
                case 5:
                    filtrarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opcion inválida, pruebe nuevamente.");
            }
        }
    }

    private Libro obtenerDatosApi() {
        System.out.print("Ingrese el titulo del libro que desea añadir: ");
        String nombreLibro = leer.nextLine();
        String json = consumirApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datos = conversor.conversorDatos(json, Datos.class);
        Optional<DatosLibro> datosLibro = datos.libros().stream()
                .filter(d -> d.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

        Libro libroNuevo = null;
        if (datosLibro.isPresent()) {
            List<Autor> autores = autorRepository.findAll();
            try {
                libroNuevo = serviceLibros.checkAutorRepetido(datosLibro.get(), autores);
            } catch (Exception e) {
                libroNuevo = new Libro(datosLibro.get());
                libroNuevo.setAutor(null);
            }
        }
        return libroNuevo;
    }

    private void addLibro() {
        Libro libroNuevo = obtenerDatosApi();
        if (libroNuevo != null && libroNuevo.getAutor() != null) {
            autorRepository.save(autorService.convertirName(libroNuevo.getAutor()));
            libroRepository.save(libroNuevo);
        } else if (libroNuevo != null && libroNuevo.getAutor() == null) {
            libroRepository.save(libroNuevo);
        } else {
            System.out.println("\n~ Ese libro no existe! (En la base de datos, al menos.)\n");
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        System.out.println("\n** Mostrando libros registrados **\n");
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        System.out.println("\n** Mostrando los autores registrados **\n");

        for (Autor autor : autores) {
//            autorService.convertirName(autor);
            autor.setLibros(libroRepository.buscarLibroPorAutor(autor));
        }

        autores.forEach(System.out::println);
    }

    private void filtrarAutoresVivos() {
        Integer limitYear = 0;
        System.out.print("\nDigite el año límite: ");
        try {
            limitYear = leer.nextInt();
        } catch (IllegalArgumentException e) {
            System.out.println("Dato invalido: " + e);

        }
        Optional<List<Autor>> autorOptional = autorRepository.autoresVivos(limitYear);

        if(autorOptional.isPresent()){
            List<Autor> autoresVivos = autorOptional.get();
            if(autoresVivos.isEmpty()){
                System.out.println("No hay autores disponibles para dicha fecha!\n");
            }
            for(Autor autor: autoresVivos){
                if(autor.getBirth_year() <= limitYear && autor.getDeath_year() >= limitYear){
                    System.out.println(autor);
                }
            }
        }
    }

    private void filtrarPorIdioma() {
        System.out.print("\nDigite el idioma en el que desea ver los libros \nSin abreviaciones: ");
        String idioma =  leer.nextLine();
        Idioma idiomaNuevo = Idioma.fromInputString(idioma);

        Optional<List<Libro>> buscarPorIdioma = libroRepository.buscarPorIdioma(idiomaNuevo);

        if (buscarPorIdioma.isPresent()){
            List<Libro> librosPorIdioma = buscarPorIdioma.get();
            if (librosPorIdioma.isEmpty()){
                System.out.println("\nNo tenemos libros en " + idioma + " aun! \n");
            }

            librosPorIdioma.forEach(System.out::println);
        }
    }
}