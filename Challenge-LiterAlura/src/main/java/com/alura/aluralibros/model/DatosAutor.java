package com.alura.aluralibros.model;

//En este caso no utilice ni JsonIgnoreUnknown ni JsonAlias.
//Solo porque utilicé todos los datos de la Api en el objeto person
//También porque dejé las mismas claves
public record DatosAutor(
        String name,
        Integer birth_year,
        Integer death_year
) {
}
