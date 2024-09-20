package model;

public class PuzzleWord extends PuzzleItem implements Word {
    String word= null;
    int dir;


    public PuzzleWord(String word, int row, int col, int dir) {
        super(row,col);
        this.dir = dir;
        this.word=word;

    }

    public String getWord() {
        return word;
    }

    public int getDir(){
        return dir;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}