package lasalle.Hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    Word word;
    @BeforeEach
    void setUp() {
        word = new Word();
    }
    @Test
    void getWordLenght() {
        assertTrue(word.getWordLenght()>=4 && word.getWordLenght()<=10);
    }
    @Test
    void getWord() {
        assertTrue(word.getWord().matches("(reddit|facebook|java|assignment|game|hello|islam|religion|internet|face)"));
    }
    @Test
    void checkChar() {
        for(int i=0; i< word.getWordLenght(); i++){
          assertFalse(word.checkChar(i, 'z'));
        }
    }
}