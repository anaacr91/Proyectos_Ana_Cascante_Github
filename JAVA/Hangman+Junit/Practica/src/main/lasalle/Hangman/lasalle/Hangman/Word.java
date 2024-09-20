package lasalle.Hangman;

import java.util.Random;

public class Word {
    private Random random = new Random();
    private String guesses[] = {
            "reddit", "facebook", "java", "assignment",
            "game", "hello", "islam", "religion", "internet", "face"};
    private char[] randomWordToGuess;
    private int ammountOfGuesses;

    Word() {
        int randomNumber = random.nextInt(guesses.length);
        randomWordToGuess = guesses[randomNumber].toCharArray(); // java -> j,a,v,a
        ammountOfGuesses = randomWordToGuess.length; //total tries to guess a word.
    }

    public int getAmmountOfGuesses() {
        return ammountOfGuesses;
    }

    public int getWordLenght() {
        return randomWordToGuess.length;
    }

    public String getWord(){
        return new String(randomWordToGuess);
    }

    public boolean checkChar(int i, char input) {
        return input == randomWordToGuess[i];
    }
}
