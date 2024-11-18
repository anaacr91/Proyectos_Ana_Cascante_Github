package com.example.sopaletrasgui.models;

public abstract class PuzzelItem {
    private int indexRowInit;
    private int indexRowEnd;
    private int indexColumnInit;
    private int indexColumneEnd;
    private boolean discovered;

    public abstract int length();

    public int getIndexRowInit() {
        return indexRowInit;
    }

    public void setIndexRowInit(int indexRowInit) {
        this.indexRowInit = indexRowInit;
    }

    public int getIndexRowEnd() {
        return indexRowEnd;
    }

    public void setIndexRowEnd(int indexRowEnd) {
        this.indexRowEnd = indexRowEnd;
    }

    public int getIndexColumnInit() {
        return indexColumnInit;
    }

    public void setIndexColumnInit(int indexColumnInit) {
        this.indexColumnInit = indexColumnInit;
    }

    public int getIndexColumnEnd() {
        return indexColumneEnd;
    }

    public void setIndexColumneEnd(int indexColumneEnd) {
        this.indexColumneEnd = indexColumneEnd;
    }


    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered() {
        this.discovered = true;
    }

    /**
     * Method to look for an specific object in the puzzleItem
     *
     * @param o object to search in the puzzleItem
     * @return an array of ROW-COL coords of the object in puzzle, -1,-1 if not exists
     */
    public abstract int[] coordsOfMatch(Object o);

    /**
     * Item to check if is contained in the puzzle
     *
     * @param itemToGuess
     * @return true is it is on the puzzle, false if it is not
     */
    public abstract boolean tryGuess(Object itemToGuess);

    @Override
    public String toString() {
        return "PuzzelItem{" +
                "indexRowInit=" + indexRowInit +
                ", indexRowEnd=" + indexRowEnd +
                ", indexColumnInit=" + indexColumnInit +
                ", indexColumneEnd=" + indexColumneEnd +
                ", discovered=" + discovered +
                '}';
    }
}

