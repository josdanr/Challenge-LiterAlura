package com.alura.aluralibros.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Long numeroDescargas,
        @JsonAlias("authors") List<DatosAutor> autor
) {
}
