package com.example.sopaletrasgui.models;

public class PuzzelWord extends PuzzelItem {
    private String word;

    public PuzzelWord(int coordInitX, int coordEndX, int coordInitY, int coordEndY, String word) {
        this.setIndexRowInit(coordInitX);
        this.setIndexRowEnd(coordEndX);
        this.setIndexColumnInit(coordInitY);
        this.setIndexColumneEnd(coordEndY);
        this.word = word;
    }

    public boolean hasChar(char letter) {
        for (char c : word.toCharArray()) {
            if (c == letter) return true;
        }
        return false;
    }

    @Override
    public int length() {
        return word.length();
    }

    /**
     * The method coordsOf Match: check if te word is placed hotizontally or vertically to acoordingly return the coords
     * @param o object to search in the puzzleItem
     * @return
     */
    @Override
    public int[] coordsOfMatch(Object o) {
        if (word.indexOf((char) o) == -1) {
            return new int[]{-1, -1};
        } else {
            if (this.getIndexRowInit() == this.getIndexRowEnd()) {
                return new int[]{this.getIndexRowInit(), word.indexOf((char) o) + this.getIndexColumnInit()};
            } else {
                return new int[]{word.indexOf((char) o) + this.getIndexRowInit(), this.getIndexColumnInit()};
            }
        }
    }

    /**
     * @param itemToGuess
     * @return
     * El método tryGuess comprueba q la palabra q estas mirando sea igual a la que has cogido
     * coge el objeto y lo castea a string y lo compara con la word
     */

    @Override
    public boolean tryGuess(Object itemToGuess) {
        if (((String) itemToGuess).equals(word)) {
            this.setDiscovered();
            return true;
        }
        return false;
    }
}
