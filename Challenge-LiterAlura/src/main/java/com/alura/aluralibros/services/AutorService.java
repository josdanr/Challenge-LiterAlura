package com.alura.aluralibros.services;

import com.alura.aluralibros.entities.Autor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    public Autor convertirName(Autor autor){
        try{
            autor.setName(autor.getName().split(",")[1]
                    .replace(" ", "") + " " +
                    autor.getName().split(",")[0]);
        }catch (Exception e){
            /*No pongo nada aquí porque no quiero que imprima nada en consola
            Este método es para darle un formato al nombre de los autores, ya que
            a veces viene con un formato separado por comas.
            ~ Solo es por estética*/
        }

        return autor;
    }

}
