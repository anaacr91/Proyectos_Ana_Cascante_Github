package com.movies.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;

import static org.springframework.web.servlet.function.ServerResponse.status;

@ControllerAdvice
public class CustomErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");
        HttpStatus httpStatus = status != null ? HttpStatus.valueOf(Integer.parseInt(status.toString())) : HttpStatus.INTERNAL_SERVER_ERROR;

        model.addAttribute("status", httpStatus.value());
        model.addAttribute("error", httpStatus.getReasonPhrase());
        model.addAttribute("message", "La página que buscas no está disponible o ocurrió un error.");
        return "error"; // Nombre de la vista de error
    }

    /**
     * Manejo de IllegalArgumentException (Errores 400).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(
            IllegalArgumentException ex, Model model, HttpServletResponse response) {
        setResponseAttributes(response, model, HttpStatus.BAD_REQUEST, ex.getMessage());
        return "error";
    }

    /**
     * Manejo de NoSuchElementException (Errores 404).
     */
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(
            NoSuchElementException ex, Model model, HttpServletResponse response) {
        setResponseAttributes(response, model, HttpStatus.NOT_FOUND, ex.getMessage());
        return "error";
    }

    /**
     * Manejo de ResponseStatusException (Errores definidos por ResponseStatus).
     */
    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(
            ResponseStatusException ex, Model model, HttpServletResponse response) {
        setResponseAttributes(response, model, (HttpStatus) ex.getStatusCode(), ex.getReason());
        return "error";
    }

    /**
     * Manejo de RuntimeException (Errores 500).
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            RuntimeException ex, Model model, HttpServletResponse response) {
        setResponseAttributes(response, model, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return "error";
    }

    /**
     * Método auxiliar para configurar atributos comunes de respuesta.
     */
    private void setResponseAttributes(HttpServletResponse response, Model model, HttpStatus status, String message) {
        response.setStatus(status.value());
        model.addAttribute("status", status.value());
        model.addAttribute("error", status.getReasonPhrase());
        model.addAttribute("message", message != null ? message : "Ha ocurrido un error inesperado.");
    }


}


