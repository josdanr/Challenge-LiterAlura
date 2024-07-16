package com.alura.aluralibros.repository;

import com.alura.aluralibros.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.death_year >= :fecha_death AND a.birth_year <= :fecha_death")
    Optional<List<Autor>> autoresVivos(Integer fecha_death);

}
