package lasalle.Hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Word word;
    char[] playerGuess;

    @BeforeEach
    void setUp() {

        word = new Word();
    }

    @Test
    void isTheWordGuessed() {
        playerGuess= new char[word.getAmmountOfGuesses()];
        for (int i = 0; i < playerGuess.length; i++) { // Assign empty dashes at start "_ _ _ _ _ _ _ _"
            playerGuess[i] = '_';
        }
        assertFalse(Game.isTheWordGuessed(playerGuess));
    }
}