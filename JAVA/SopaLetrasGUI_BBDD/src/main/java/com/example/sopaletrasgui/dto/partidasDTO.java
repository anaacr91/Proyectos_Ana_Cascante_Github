package com.example.sopaletrasgui.dto;

import com.example.sopaletrasgui.models.WordSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * métodos constructores de la clase para crear objetos para instanciar partidas
 * que manden información a la base de datos de xampp
 */

public class partidasDTO {
    private int id;
    private List<String> tablero;
    private List<String> palabras_a_encontrar;
    private List<String> palabras_encontradas;
    private boolean terminado;

    public partidasDTO(int id, List<String> tablero, List<String> palabras_a_encontrar, List<String> palabras_encontradas, boolean terminado) {
        this.id = id;
        this.tablero = tablero;
        this.palabras_a_encontrar = palabras_a_encontrar;
        this.palabras_encontradas = palabras_encontradas;
        this.terminado=terminado;
    }

    public partidasDTO() {
        this.id = 0;
        this.tablero = new ArrayList<String>();
        this.palabras_a_encontrar = new ArrayList<String>();
        this.palabras_encontradas = new ArrayList<String>();
        this.terminado=false;
    }

    public partidasDTO(WordSearch wordSearch) {
        this.id = 0;
        this.setTablero(String.valueOf(wordSearch.getPuzzle()));
        this.setPalabras_a_encontrar(wordSearch.getDiscoverablePuzzelWordsAsList());
        this.palabras_encontradas = new ArrayList<String>();
        this.terminado=false;
    }

    @Override
    public String toString() {
        return "partidasDTO{" +
                "id=" + id +
                ", tablero=" + tablero +
                ", palabras_a_encontrar=" + palabras_a_encontrar +
                ", palabras_encontradas=" + palabras_encontradas +
                ", terminado=" + terminado +
                '}';
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public boolean isTerminado() {
        return terminado;
    }


    public int getId() {
        return id;
    }

    public List<String> getTablero() {
        return tablero;
    }

    public String getTableroasString(){
        return String.join(",", this.tablero);
    }

    public List<String> getPalabras_a_encontrar() {
        return palabras_a_encontrar;
    }

    public String getPalabras_a_encontrar_asString(){
        return String.join(",", this.palabras_a_encontrar);
    }

    public List<String> getPalabras_encontradas() {
        return palabras_encontradas;
    }

    public String getPalabras_encontradas_asString(){
        return String.join(",", this.palabras_encontradas);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTablero(List<String> tablero) {
        this.tablero = tablero;
    }

    /**
     * método que realiza un proceso de convertir 1 String a 1 Arraylist strings
     * del tablero
     * @return
     */

    public void setTablero(String tablero){
        String[] elementos = tablero.split(",");
        this.tablero.clear();
        for (String element: elementos){
            this.tablero.add(element);
        }
    }
    /**
     * método que realiza un proceso de convertir 1 matriz de chars a 1 Arraylist strings
     * del tablero
     * @return
     */
    public void setTablero(char[][] tablero) {
        for (int i = 0 ; i< tablero.length; i++){
            for (int j = 0 ; j< tablero[i].length; j++){
                this.tablero.add(String.valueOf(tablero[i][j]));
            }
        }
    }

    /**
     * método que realiza un proceso de convertir de ArrayList strings a 1 matriz de chars
     * del tablero
     * @return
     */
    public char[][] getTablerofromList(){
        char[][]tablerochar=new char[10][10];
        int index=0;
        for (int i = 0 ; i< tablerochar.length; i++) {
            for (int j = 0; j < tablerochar[i].length; j++) {
                tablerochar[i][j]= this.tablero.get(index).charAt(0);
                index++;
            }
        }
        return tablerochar;
    }

    public void setPalabras_a_encontrar(List<String> palabras_a_encontrar) {
        this.palabras_a_encontrar = palabras_a_encontrar;
    }

    /**
     * método que realiza un proceso de convertir 1 String a 1 Arraylist strings
     * de las palabras a encontrar
     * @return
     */

    public void setPalabras_a_encontrar(String palabras_a_encontrar){
        String[] palabras= palabras_a_encontrar.split(",");
        this.palabras_a_encontrar.clear();
        for (String palabra: palabras){
            this.palabras_a_encontrar.add(palabra);
        }
    }

    public void setPalabras_encontradas(List<String> palabras_encontradas) {
        this.palabras_encontradas = palabras_encontradas;
    }

    /**
     * método que realiza un proceso de convertir 1 String a 1 Arraylist strings
     * de las palabras encontradas
     * @return
     */

    public void setPalabras_encontradas(String palabras_encontradas){
        String[] words= palabras_encontradas.split(",");
        this.palabras_encontradas.clear();
        for (String word: words){
            this.palabras_encontradas.add(word);
        }
    }



}
