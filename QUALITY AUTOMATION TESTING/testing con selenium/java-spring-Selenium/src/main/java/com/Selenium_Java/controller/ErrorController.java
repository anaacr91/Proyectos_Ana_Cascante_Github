package com.Selenium_Java.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/*
Controlador especial para capturar excepciones y mostrar una pantalla de error
Centraliza la gestión de errores, para que sea más simple
 y no tener que repetirla en todos los controladores
*/
@ControllerAdvice
public class ErrorController {
    /**
    * metodo que captura cualquier excepción que se lance y
     * no sea capturada con try catch desde otro sitio
    * */

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 404
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    // Cargar un Status Http que viene del controlador cuando se lanza una excepción, y cargarlo en la respuesta que devolvemos de error
    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatus(ResponseStatusException e, Model model, HttpServletResponse response){
    //obtener status a partir excepcion
    response.setStatus(e.getStatusCode().value());
    model.addAttribute("message", e.getReason());
    return "error";
}
}
