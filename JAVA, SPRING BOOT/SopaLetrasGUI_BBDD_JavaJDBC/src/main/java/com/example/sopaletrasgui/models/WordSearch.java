package com.example.sopaletrasgui.models;
import com.example.sopaletrasgui.dto.partidasDTO;
import com.example.sopaletrasgui.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordSearch {
    Random random = new Random();
    private final int width = 10;
    private final int lenght = 10;
    private final int maxAllowedWords = 5;
    private char[][] puzzle;

    private ArrayList<int[]> discoveredCells;
    private ArrayList<PuzzelWord> discoverablePuzzelWords;
    private partidasDTO partida;


    /**
     * metodo constructor de wordsearch que identifica si la partida es nueva o anterior. si es nueva se construye el puzzle
     * y se guarda el tablero. si la partida es anterior se recupera el tablero anterior junto con las palabras encontradas y
     * por encontrar para continuar la partida
     * @param partida
     */

    public WordSearch(partidasDTO partida) {
        this.partida=partida;
        puzzle = new char[width][lenght];
        discoveredCells = new ArrayList<>();

        if (partida.getId()==0) {

            buildPuzzle((ArrayList<String>) partida.getPalabras_a_encontrar());
            fillBlanks();
            partida.setTablero(puzzle);

        }else{
            puzzle=partida.getTablerofromList();
            discoverablePuzzelWords= new ArrayList<>();
            for (String wordstoFind: partida.getPalabras_a_encontrar()){
                discoverablePuzzelWords.add(getPuzzleWordinTablero(wordstoFind));
            }
            for ( String wordsfinded: partida.getPalabras_encontradas()){
              checkWord(wordsfinded);
            }

        }
    }

    /**
     * The method fillblanks iterates in all the puzzle looking for blank spaces to fill with random letters
     */
    private void fillBlanks() {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (this.puzzle[i][j] == '\u0000') {
                    //u = caracter vacio
                    this.puzzle[i][j] = (char) (random.nextInt(26) + 'A');
                }
            }
        }
    }

    /**
     * The method buildpuzzle convert all the words to uppercase
     * if and throws a exception if the word is larger than 10 letters (the puzzle size is 10*10)
     * also it places the longest word in the middle of the puzzle randomly vertically or horizontally
     * and verifies that the word to fit is not larger than the puzzle lenght
     * finally if it cannot place the word matching any other it will try to randomly place it in an empty place
     *
     * @param words
     * @throws CustomException
     */

    private void buildPuzzle(ArrayList<String> words) throws CustomException {

        for (int i = 0; i < words.size(); i++) {
            String originalString = words.get(i);
            if (originalString.length() > lenght || originalString.length() > width) {
                throw new CustomException("any of the provided words dont fit the puzzle ");
            }
            String uppercaseString = originalString.toUpperCase();
            words.set(i, uppercaseString);

        }

        discoverablePuzzelWords = new ArrayList<>();

        String firstWordToPlace = words.get(0);

        if (random.nextBoolean()) {
            int coordInitX = (puzzle.length / 2) / (firstWordToPlace.length() / 2);
            if (coordInitX < 0) return;
            int coordInitY = puzzle.length / 2;
            colocarWords(true, coordInitX - 1, coordInitY - 1, firstWordToPlace);
        } else {
            int coordInitX = puzzle.length / 2;
            int coordInitY = (puzzle.length / 2) / (firstWordToPlace.length() / 2);
            if (coordInitY < 0) return;
            colocarWords(false, coordInitX - 1, coordInitY - 1, firstWordToPlace);
        }

        for (int i = 1; i < words.size(); i++) {
            for (PuzzelWord placedWord : discoverablePuzzelWords) {
                if (match(words.get(i), placedWord)) {
                    break;
                } else {
                    for (int j = 0; j < 10; j++) {
                        if (colocarWords(random.nextBoolean(), random.nextInt(0, puzzle.length), random.nextInt(0, puzzle.length), words.get(i)))
                            break;
                    }
                    break;
                }
            }
        }
    }

    /**
     * El metodo match comprueba si la palabra colocada coincide con las que vas a colocar,
     * comprueva coordenadas, si la coordenada del index 0 es mayor a -1, quiere decir q ya esta colocada
     * luego comprueba la orientacion, para colocar la palabra en vertical o horizontal
     * compara las palabras ya colocadas con las palabras por colocar para no sobreescribirlas
     * despues si la palabra por colocar es horizontal, la palabra por colocar la pone en vertical y al revés
     *
     * @param wordToPlace
     * @param placedWord
     * @return
     */

    private boolean match(String wordToPlace, PuzzelWord placedWord) {
        System.out.println();
        for (int i = 0; i < wordToPlace.length(); i++) {
            if (placedWord.hasChar(wordToPlace.charAt(i))) {
                int[] coords = placedWord.coordsOfMatch(wordToPlace.charAt(i));
                if (coords[0] > -1) {
                    if (placedWord.getIndexRowInit() == placedWord.getIndexRowEnd()) {
                        if (colocarWords(true, coords[0] - i, coords[1], wordToPlace))
                            return true;
                    } else {
                        if (colocarWords(false, coords[0], coords[1] - i, wordToPlace))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * el metodo colocar words coloca la palabra en la sopa de letras
     *
     * @param vo
     * @param indexRowInit
     * @param indexColumnInit
     * @param wordToPlace
     * @return
     */

    private boolean colocarWords(boolean vo, int indexRowInit, int indexColumnInit, String wordToPlace) {
        if (wordFits(vo, indexRowInit, indexColumnInit, wordToPlace)) {
            int indexColumn = indexColumnInit;
            int indexRow = indexRowInit;
            for (char c : wordToPlace.toCharArray()) {
                this.puzzle[indexRow][indexColumn] = c;
                if (vo) indexRow++;
                else indexColumn++;
            }
            if (vo)
                discoverablePuzzelWords.add(new PuzzelWord(indexRowInit, indexRowInit + wordToPlace.length() - 1, indexColumnInit, indexColumnInit, wordToPlace));

            else
                discoverablePuzzelWords.add(new PuzzelWord(indexRowInit, indexRowInit, indexColumnInit, indexColumnInit + +wordToPlace.length() - 1, wordToPlace));
            return true;
        } else {
            return false;
        }
    }

    /**
     * el metodo wordFits comprueva si cabe o no la palabra en la sopa de letras: aqui mira si las palabras se solapan o no
     * recorre la palabra por cada char, y mira si esta vacio o la palabra a colocar coincide con alguna letra
     *
     * @param vo
     * @param indexRowInit
     * @param indexColumnInit
     * @param wordToPlace
     * @return
     */

    private boolean wordFits(boolean vo, int indexRowInit, int indexColumnInit, String wordToPlace) {
        if (indexRowInit < 0 || indexColumnInit < 0)
            return false;
        if (vo && indexRowInit + wordToPlace.length() > puzzle.length)
            return false;
        if (!vo && indexColumnInit + wordToPlace.length() > puzzle.length)
            return false;
        int indexColumn = indexColumnInit;
        int indexRow = indexRowInit;

        for (char c : wordToPlace.toCharArray()) {
            if (this.puzzle[indexRow][indexColumn] == '\u0000' || this.puzzle[indexRow][indexColumn] == c) {
                if (vo) indexRow++;
                else indexColumn++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * el método wordsToFind retorna la cantidad de las palabras a descubrir
     *
     * @return
     */

    public int wordsToFind() {
        return discoverablePuzzelWords.size();
    }

    /**
     * el método gameOver retorna si las palabras totales se han adivinado o no
     *
     * @return
     */

    public boolean gameOver() {
        for (PuzzelWord word : discoverablePuzzelWords) {
            if (!word.isDiscovered())
                return false;
        }
        return true;
    }

    /**
     * el método checkWord chequea si la palabra insertada es parte de la array de palabras a descubrir o no
     * En caso de ser una palabra descubierta la añade al array de celdas descubiertas
     *
     * @param wordToGuess
     * @return
     */
    public boolean checkWord(String wordToGuess) {
        for (PuzzelWord word : discoverablePuzzelWords) {
            if (word.tryGuess(wordToGuess)) {
                if (word.getIndexColumnInit() == word.getIndexColumnEnd()) {
                    for (int i = word.getIndexRowInit(); i <= word.getIndexRowEnd(); i++) {
                        discoveredCells.add(new int[]{i, word.getIndexColumnInit()});

                    }
                } else {
                    for (int i = word.getIndexColumnInit(); i <= word.getIndexColumnEnd(); i++) {
                        discoveredCells.add(new int[]{word.getIndexRowInit(), i});

                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * El metodo isDiscoveredCell mira si la palabra esta descubierta o no
     *
     * @param row
     * @param column
     * @return
     */
    public boolean isDiscoveredCell(int row, int column) {
        for (int[] cell : discoveredCells) {
            if (row == cell[0] && column == cell[1])
                return true;
        }
        return false;
    }

    ArrayList<String> tableroStr = new ArrayList<String>();
    /**
     * metodo que guarda toda la sopa de chars en 1 ArrayList de strings
     * en el caso de q sea partida nueva
     */

    public ArrayList<String> getTablerofromChars() {

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                tableroStr.add(String.valueOf(puzzle[i][j]));
            }
        }
        return tableroStr;
    }
    /**
     * método q nos pasa de ArrayList de strings a 1 ArrayList de chars
     * en el caso de partida guardada
     */
    public ArrayList<Character> getTablerofromStrings(ArrayList<String>tableroStr){
        ArrayList<Character> listaChars = new ArrayList<>();
        for (String str : tableroStr) {
            int i = 0;
            listaChars.add(str.charAt(i));
            i++;
        }return listaChars;
    }

    /**
     * metodo que obtiene las puzzelwords como 1 ArrayList
     * @return
     */
    public List<String> getDiscoverablePuzzelWordsAsList() {
        List<String> lista = null;
        for (PuzzelWord word: discoverablePuzzelWords) {
            lista.add(word.getWord());
        }
        return lista;
    }

    /**
     * metodo que obtiene el puzzle en matriz de chars
     * @return
     */
    public char[][] getPuzzle() {
        return puzzle;
    }

    /**
     * este método recorre el puzzle entero, si en la posicion q esta el iterador encuentra la primera palabra,
     * lo que hará es comprobar si tiene espacio para estar en esa fila, si cabe o no por longitud,
     * si cabe 1 es vertical, la otra es horizontal, comprueba si coincide la palabra completa, es que hemos encontrado la palabra
     * y como ya tenemos y podemos coger las variables /coordenadas[i][j]
     * @param word
     * @return
     */

    private PuzzelWord getPuzzleWordinTablero(String word) {
        PuzzelWord puzzelWord = new PuzzelWord(word);

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == word.charAt(0)) {
                    if (j + word.length() <= puzzle[i].length) {
                        boolean wordFound = true;
                        for (int k = 0; k < word.length(); k++) {
                            if (puzzle[i][j + k] != word.charAt(k)) {
                                wordFound = false;
                                break;
                            }
                        }
                        if (wordFound) {

                            puzzelWord.setIndexRowInit(i);
                            puzzelWord.setIndexRowEnd(i);
                            puzzelWord.setIndexColumnInit(j);
                            puzzelWord.setIndexColumneEnd(j + word.length() - 1);
                            return puzzelWord;
                        }
                    }


                    if (i + word.length() <= puzzle.length) {
                        boolean wordFound = true;
                        for (int k = 0; k < word.length(); k++) {
                            if (puzzle[i + k][j] != word.charAt(k)) {
                                wordFound = false;
                                break;
                            }
                        }
                        if (wordFound) {
                            puzzelWord.setIndexRowInit(i);
                            puzzelWord.setIndexRowEnd(i + word.length() - 1);
                            puzzelWord.setIndexColumnInit(j);
                            puzzelWord.setIndexColumneEnd(j);
                            return puzzelWord;
                        }
                    }
                }
            }
        }

        return puzzelWord;
    }

}




