package com.alura.aluralibros;

import com.alura.aluralibros.principal.Principal;
import com.alura.aluralibros.repository.IAutorRepository;
import com.alura.aluralibros.repository.ILibroRepository;
import com.alura.aluralibros.services.AutorService;
import com.alura.aluralibros.services.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluralibrosApplication implements CommandLineRunner {

	@Autowired
	private ILibroRepository libroRepository;
	@Autowired
	private IAutorRepository autorRepository;
	@Autowired
	private LibrosService serviceLibros;
	@Autowired
	private AutorService autorService;

	public static void main(String[] args) {
		SpringApplication.run(AluralibrosApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository, serviceLibros, autorService);
		principal.mostrarMenu();

	}
}
