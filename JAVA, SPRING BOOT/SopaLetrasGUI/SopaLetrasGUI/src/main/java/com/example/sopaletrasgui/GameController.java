package com.example.sopaletrasgui;

import com.example.sopaletrasgui.exception.CustomException;
import com.example.sopaletrasgui.models.WordSearch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import java.io.*;
import java.util.ArrayList;

public class GameController {
    private WordSearch wordSearch;
    int tryes = 0;
    ArrayList<String> listaColeccion = new ArrayList<>();
    Label[][] labelGrid = new Label[10][10];
    private static String FILENAME= "palabras.txt";

    @FXML
    TextField tryWords;

    @FXML
    private Button Try;

    @FXML
    private GridPane cuadricula;

    /**
     * El metodo onButtonClick gestiona el juego
     * y controla si el input tiene caracteres especiales
     * si la palabra es acertada o no
     * si el usuario ha ganado,
     * o si ha perdido por exceso de intentos
     * y saca los alertas
     */
    @FXML
    protected void onButtonClick() {

        do {
            String inputWord = tryWords.getText().toUpperCase();
            if (containsSpecialCharacters(inputWord)) {
                printPuzzle(wordSearch.getPuzzle());
                showAlert("No se permiten caracteres especiales en la palabra.");
                tryes++;

            }else if (wordSearch.checkWord(inputWord)) {
                printPuzzle(wordSearch.getPuzzle());
                showAlert("Well done");
                tryes++;
                if (wordSearch.gameOver()) {
                    showAlert("You win");
                    Platform.exit();
                }

            }else if (!wordSearch.checkWord(inputWord)){
                printPuzzle(wordSearch.getPuzzle());
                showAlert("Not lucky");
                tryes++;
                if (tryes>6){
                    showAlert("You lost");
                    Platform.exit();
                }
             }
        } while (tryes>6);

    }

    /**
     * este metodo showAlert gestiona los alertas del juego
     * @param message
     */
    private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(String.format(message));
            alert.showAndWait();
            alert.close();

    }

    /**
     * este metodo containsSpecialCharacters verifica si el input tiene caracteres especiales
     * @param input
     * @return
     */
    private boolean containsSpecialCharacters(String input) {
        // Utiliza una expresión regular para verificar si hay caracteres especiales
        if (input.matches(".*[^\\p{L}].*")){
            return true;
        }
        return false;
    }

    /**
     * este metodo init inicia el juego
     * @param words
     */
    public void init(String words) {

            wordSearch = new WordSearch(processInput(words));
            write();
            createGrid(wordSearch.getPuzzle());
            printPuzzle(wordSearch.getPuzzle());
            System.out.println("\nTry to find the words.");

    }

    /**
     * este metodo processInput procesa los inputs, los separa por espacios y los añade a la coleccion
     * @param words
     * @return
     */
    private ArrayList<String> processInput(String words) {
        String[] coleccion = words.split(" ");
        for (String palabra : coleccion) {
            listaColeccion.add(palabra);
        }
        return listaColeccion;
    }

    /**
     * este metodo printPuzzle recorre el puzzle, y verifica si la palabra ha sido descubierta o no
     * si es así, coge la label donde este la letra y la pinta en rojo
     * @param puzzle
     */
    private void printPuzzle(char[][] puzzle) {
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                if (wordSearch.isDiscoveredCell(row, col)) {
                    labelGrid[row][col].setTextFill(Color.RED);
                }
            }
        }
    }

    /**
     * este metodo arrayListToString transforma una arrayList en estring
     * @param listaColeccion
     * @return
     */
    private String arrayListToString(ArrayList<String> listaColeccion) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listaColeccion.size(); i++) {
            sb.append(listaColeccion.get(i));
        }
        return sb.toString();
    }

    /**
     * este metodo crea la cuadrilla
     * añade dimensiones de row / columns
     * recorre el puzzle, crea labels con el valor de las strings del puzzle de la misma posición de coordenadas[i][j]
     * las alinea en el centro, les da un fondo blanco y un borde negro
     * les da una dimension de altura y anchura para quedar mas centradas
     * y luego añade las labels a la cuadricula
     * y luego les da un indice para encontrarlas
     * @param puzzle
     */
    private void createGrid(char[][] puzzle) {

        for (int i = 0; i < 10; i++) {
            cuadricula.getColumnConstraints().add(new ColumnConstraints(30));
            cuadricula.getRowConstraints().add(new RowConstraints(30));
            for (int j = 0; j < 10; j++) {
                Label lb = new Label(String.valueOf(puzzle[i][j]));
                lb.setAlignment(Pos.CENTER);
                lb.setStyle("-fx-background-color: white;-fx-border-color: black;");
                lb.setMaxHeight(30);
                lb.setMaxWidth(30);
                cuadricula.add(lb, i, j);
                labelGrid[i][j] = lb;
            }
        }

    }

    /**
     * este método write escribe en un txt saliente las palabras elegidas por el usuario sin sobreescribirlas
     * simplemente, añadiendolas al final
     */
    private void write () {

        File fileOut= null;
        PrintWriter output= null;
        try {
            output = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME, true)));
            for (int i = 0; i < listaColeccion.size(); i++) {
                output.print(listaColeccion.get(i)+"; ");
            }
            output.println("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }if (fileOut != null) {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


