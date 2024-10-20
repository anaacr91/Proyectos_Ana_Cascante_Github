package model;


public abstract class PuzzleItem {
     int row;
     int col;

    public PuzzleItem(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}