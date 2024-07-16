package com.alura.aluralibros.model;

//Estableci el idioma teniendo en cuenta el proporcionado por gutendex
//Pero también los posibles inputs en español del usuario.
public enum Idioma {

    Inglés ("en", "Inglés"),
    Español ("es", "Español"),
    Francés ("fr", "Francés"),
    Portugués ("pt", "Portugués");

    private String idiomaGutendex;
    private String idiomaEspañol;

    Idioma(String idiomaGutendex, String idiomaEspañol){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspañol = idiomaEspañol;
    }

    public static Idioma fromApiString(String text){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun libro en el idioma: " + text);
    }

    public static Idioma fromInputString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEspañol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun libro en el idioma: " + text);
    }

}
