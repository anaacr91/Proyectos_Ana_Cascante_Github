package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CascanteAnaPelicula {
    public  String titulo;
    public  String genero;
    public  String director;
    public  double puntuación;


    public CascanteAnaPelicula(String titulo, String genero, String director, double puntuación) {
        this.titulo = titulo;
        this.genero = genero;
        this.director = director;
        this.puntuación = puntuación;
    }

    public CascanteAnaPelicula() {
        this.titulo = "sinTitulo";
        this.genero = "sinGénero";
        this.director = "sinDirector";
        this.puntuación = 0;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPuntuación(double Puntuación) {
        this.puntuación = puntuación;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getDirector() {
        return director;
    }

    public double getPuntuación() {
        return puntuación;
    }

    public String toString() {
        return "CascanteAnaPelicula{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", director='" + director + '\'' +
                ", puntuación='" + puntuación + '\'' +
                '}';
    }

    public void add(CascanteAnaPelicula cascanteAnaPelicula) {
        Scanner input = new Scanner(System.in);

        String titulo = "";
        do {
            System.out.println("Introduce titulo");
            titulo = input.nextLine();
        } while (titulo.isBlank());

        String genero = "";
        do {
            System.out.println("Introduce genero");
            genero = input.nextLine();
        } while (genero.isBlank());

        String director = "";
        do {
            System.out.println("Introduce director");
            director = input.nextLine();
        } while (director.isBlank());

        boolean exit = false;
        do {
            System.out.println("Introduce puntuación");
            double puntuacion = input.nextDouble();
            if (0 >= puntuacion && puntuacion <= 5) {
                System.out.println("La puntuación es" + puntuacion);
            } else {
                System.out.println("Valor no aceptado");
            }
        } while (!exit);
    }
}




