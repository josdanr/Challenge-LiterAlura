package com.alura.aluralibros.services;

public interface IConversorDatosClase {
    <T> T conversorDatos(String json, Class<T> clase);

}
