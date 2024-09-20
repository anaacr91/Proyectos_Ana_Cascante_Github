package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;


public class CascanteAnaRanking {

    public static void main (String[] args) {
        CascanteAnaRanking programa = new CascanteAnaRanking();
        programa.inicio();
    }

            public void inicio() {

                ArrayList <CascanteAnaPelicula> catalogo = new ArrayList<>();
                Scanner input = new Scanner(System.in);
                catalogo.add(new CascanteAnaPelicula("Los otros", "Miedo", "Nicole Kidman", 3));
                catalogo.add(new CascanteAnaPelicula("El padrino", "Drama", "Al Pacino", 5));
                catalogo.add(new CascanteAnaPelicula("Un italiano en Noruega", "Comedia", "Roberto Begnini", 4));


                boolean exit = false;
                do {
                System.out.println("Bienvenid@ a Movieflix");
                System.out.println("Escoge una opcion del 1 al 5 \n"+
                        "[1] Nueva Película \n"+
                        "[2] Visualizar películas \n"+
                        "[3] Top películas \n"+
                        "[4] Buscar películas \n"+
                        "[5] Salir");
                    int opcion = readInt();
                    switch(opcion){
                        case 1:
                            System.out.println("Introduce el Título");
                            String titulo = input.nextLine();
                            System.out.println("Introduce el Genero");
                            String genero = input.nextLine();
                            System.out.println("Introduce el Director");
                            String director = input.nextLine();
                            System.out.println("Introduce la Puntuacion");
                            Double puntuación = readDouble();
                            evitarDuplicados(catalogo, titulo);
                            catalogo.add(new CascanteAnaPelicula(titulo, genero, director, puntuación));
                            System.out.println("Película correctamente introducida");
                            break;
                        case 2:
                            System.out.println("Imprimiendo catálogo");
                            mostrarCatalogo(catalogo);
                            break;
                        case 3:
                            System.out.println("Imprimiendo películas TOP");
                            mostrarTop(catalogo);
                            break;
                        case 4:
                            System.out.println("Qué película quieres buscar. Introduce el título");
                            titulo = input.nextLine();
                            buscarPelicula(catalogo, titulo);
                            break;
                        case 5:
                            exit = true;
                            break;
                        default:
                            System.out.println("La acción no Existe");
                    }
                }while(!exit);
            }
    public boolean evitarDuplicados(ArrayList<CascanteAnaPelicula> catalogo, String titulo) {
        boolean encontrado = false;
        for (CascanteAnaPelicula pelicula : catalogo){
            if (titulo.equalsIgnoreCase(pelicula.titulo)) {
                System.out.println("La tenemos. No se puede duplicar");
                encontrado = true;
                break;
            } else if (!encontrado) {
                break;
            }
        }
        return encontrado;
    }
    public void mostrarCatalogo(ArrayList<CascanteAnaPelicula> catalogo)  {
        System.out.println("Catálogo de películas");
        for (CascanteAnaPelicula pelicula : catalogo){
            System.out.println(pelicula);}
        }
    public void mostrarTop(ArrayList<CascanteAnaPelicula> catalogo) {
        System.out.println("Películas TOP");
        for (CascanteAnaPelicula pelicula : catalogo){
            if (pelicula.puntuación >= 4) {
                System.out.println(pelicula);}
        }
    }
    public boolean buscarPelicula(ArrayList<CascanteAnaPelicula> catalogo, String titulo) {
        boolean encontrar = false;
        for (CascanteAnaPelicula pelicula : catalogo) {
            if (titulo.equalsIgnoreCase(pelicula.titulo)) {
                System.out.println("La tenemos. Si quiere visualizarla, por favor subscribase al plan mensual premium");
                encontrar = true;
                break;
            } else if (!encontrar) {
                System.out.println("No la tenemos disponible todavía, la tendremos en un mes");
                break;
                }
            }
        return encontrar;
    }
    public int readInt(){
                Scanner input = new Scanner(System.in);
                boolean exit = false;
                int a = 0;

                do {

                    if (input.hasNextInt()) {
                        a = input.nextInt();
                        input.nextLine();
                        exit=true;
                    } else {
                        input.nextLine();
                        System.out.println("Tipo de dato incorrecto. Introduce número del menú");
                    }
                }while(!exit);

                return a;
            }
    public double readDouble(){
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        double b = 0;

        do {

            if (input.hasNextDouble()) {
                b = input.nextDouble();
                input.nextLine();
                exit=true;
            } else {
                input.nextLine();
                System.out.println("Tipo de dato incorrecto. Introduce un numero");
            }
        }while(!exit);
        return b;
    }
      }






