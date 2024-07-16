package com.alura.aluralibros.repository;

import com.alura.aluralibros.entities.Autor;
import com.alura.aluralibros.entities.Libro;
import com.alura.aluralibros.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long>{

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    List<Libro> buscarLibroPorAutor(Autor autor);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    Optional<List<Libro>> buscarPorIdioma(Idioma idioma);

}
