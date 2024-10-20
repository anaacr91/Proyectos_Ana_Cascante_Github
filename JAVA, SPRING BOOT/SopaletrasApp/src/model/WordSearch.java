package model;

import errors.PositioningError;

import java.util.ArrayList;
import java.util.Random;


public class WordSearch {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private String[][] soup;
    private ArrayList<PuzzleWord> puzzleWords = new ArrayList<>();

    public WordSearch(int size, ArrayList<String> words) throws PositioningError {

        this.soup = new String[size][size];
        this.puzzleWords = puzzleWordsGenerator(words);
        fillWordSearch();

    }

    /**
     * Metodo que devuelve un true si la palabra se encuetra en la sopa de letras.
     *
     * @param word Palabra a buscar
     */
    public boolean findWord(String word) {
        for (PuzzleWord puzzleWord : this.puzzleWords) {
            if (puzzleWord.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que encuentra la palabra para decir en que coordenadas esta y llama al método para colorearla de rojo
     *
     * @param word
     */
    public void coordsOfMatch(String word) {
        for (PuzzleWord puzzleWord : this.puzzleWords) {
            if (puzzleWord.getWord().equals(word)) {
                System.out.println("Tu palabra se encuentra en la posicion:\nX=" + puzzleWord.getCol() + "\nY=" + puzzleWord.getRow());

                colorPuzzleWord(puzzleWord);
            }
        }
    }
    /**
     * Metodo que colorea la palabra en la sopa de letras
     *
     * @param puzzleWord
     */
    private void colorPuzzleWord(PuzzleWord puzzleWord) {
        String word = puzzleWord.getWord();
        int row = puzzleWord.getRow();
        int col = puzzleWord.getCol();
        int dir = puzzleWord.getDir();
        if (dir == 0) {
            colorHorizontalPuzzleWord(word, row, col);
        } else if (dir == 1) {
            colorVerticalPuzzleWord(word, row, col);
        } else {
            colorDiagonalPuzzleWord(word, row, col);
        }
    }

    private void colorDiagonalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row + i][col + i] = redLetter(String.valueOf(word.charAt(i)));
        }
    }

    private void colorVerticalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row + i][col] = redLetter(String.valueOf(word.charAt(i)));
        }
    }

    private void colorHorizontalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row][col + i] = redLetter(String.valueOf(word.charAt(i)));
        }
    }
    /**
     * Metodo que devuelve una letra en rojo
     *
     * @return Letra roja.
     */
    private String redLetter(String letra) {
        return ANSI_RED + letra + ANSI_RESET;
    }
    /**
     * Método que rellena todos los huecos de la sopa de letras
     **/
    public void fillWordSearch() {
        for (int i = 0; i < this.soup.length; i++) {
            for (int j = 0; j < soup.length; j++) {
                if (this.soup[i][j] == null || this.soup[i][j].isEmpty()) {
                    this.soup[i][j] = getLetterRdm();
                }
            }
        }
    }
    /**
     * Metodo que devuelve una letra aleatorio de la A-Z con la Ñ incluida
     *
     * @return String que contiene una letra
     */
    private static String getLetterRdm() {
        Random random = new Random();
        int rdm = random.nextInt(27);
        if (rdm == 26) {
            return "Ñ";
        } else return Character.toString((char) ('A' + rdm));
    }
    /**
     * Método que devuelve un array de PuzzleWords, con sus posiciones correctas o lanza un error PositioningError
     *
     * @param words Palabras
     * @return boolean True si la posición es válida, false si no lo es
     */
    private ArrayList<PuzzleWord> puzzleWordsGenerator(ArrayList<String> words) {
        ArrayList<PuzzleWord> wordList = new ArrayList<>();
        Random random = new Random();
        for (String word : words) {
            int row;
            int col;
            int dir;
            int i = 0;
            do {
                row = random.nextInt(this.soup.length);
                col = random.nextInt(this.soup.length);
                dir = random.nextInt(3);
                i++;
            } while (!positionValidator(row, col, dir, word, this.soup) || i > 400);


            if (!positionValidator(row, col, dir, word, this.soup)) {
                throw new PositioningError("Imposible colocar esta combinación de palabras.");
            } else {
                PuzzleWord puzzleWord = new PuzzleWord(word, row, col, dir);
                wordList.add(puzzleWord);
                addPuzzleWord(puzzleWord);
            }
        }
        return wordList;
    }
    /**
     * Método que verifica si la posición de la palabra dentro de la sopa de letras es válida.
     * Verifica el largo de la palabra, la dirección y si las letras ya existentes dentro de la sopa de letras
     * son compatibles con la nueva palabra a ingresar.
     *
     * @param row     Posición de la Fila
     * @param col     Posición de la Columna
     * @param dir     Dirección a ingresar (0 para horizontal, 1 para vertical, 2 para diagonal)
     * @param word    Palabra a verificar
     * @param soup    Tablero de la sopa de letras
     * @return boolean True si la posición es válida, false si no lo es
     */
    public static boolean positionValidator(int row, int col, int dir, String word, String[][] soup) {

        int len = word.length();


        if ((dir == 0 && col + len > soup[0].length) ||
                (dir == 1 && row + len > soup.length) ||
                (dir == 2 && (col + len > soup[0].length || row + len > soup.length)))
        {
            return false;
        }


        for (int i = 0; i < len; i++) {
            String letra = Character.toString(word.charAt(i));
            String celda = soup[row][col];

            if (celda != null && !celda.equals(letra)) {
                return false;
            }

            if (dir == 0) {
                col++;
            } else if (dir == 1) {
                row++;
            } else {
                row++;
                col++;
            }
        }
        return true;
    }
    /**
     * Método que añade PuzzleWords
     *
     * @param puzzleWord
     *
     */
    private void addPuzzleWord(PuzzleWord puzzleWord) {
        String word = puzzleWord.getWord();
        int row = puzzleWord.getRow();
        int col = puzzleWord.getCol();
        int dir = puzzleWord.getDir();
        if (dir == 0) {
            addHorizontalPuzzleWord(word, row, col);
        } else if (dir == 1) {
            addVerticalPuzzleWord(word, row, col);
        } else {
            addDiagonalPuzzleWord(word, row, col);
        }

    }
    private void addDiagonalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row + i][col + i] = String.valueOf(word.charAt(i));
        }
    }

    private void addVerticalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row + i][col] = String.valueOf(word.charAt(i));
        }
    }

    private void addHorizontalPuzzleWord(String word, int row, int col) {
        int len = word.length();
        for (int i = 0; i < len; i++) {
            this.soup[row][col + i] = String.valueOf(word.charAt(i));
        }
    }
    /**
     * Metodo que sobreescribe el metodo toString, de la clase String
     * creando un objeto del constructor StringBuilder de la clase String
     * recorre todas las columnas y filas de la matriz
     * comprueba que la celda este llena y añade un espacio
     * y añade saltos de linea al final de cada fila
     * @return el objeto stringbuilder
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (String[] row : soup) {
            for (String cell : row) {
                if (cell != null) {
                    sb.append(cell).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}