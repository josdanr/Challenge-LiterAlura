package com.alura.aluralibros.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumirApi {

    public String obtenerDatos(String url){

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder(URI.create(url)).build();

        HttpResponse<String> respuesta = null;

        try{
            respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e){
            throw new RuntimeException("Libro no encontrado, excepcion:  " + e);
        }

        String json = respuesta.body();
        return json;

    }

}
