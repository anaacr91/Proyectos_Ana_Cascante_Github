package lasalle;

import lasalle.Hangman.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        boolean testMode = true;
        game.start(testMode);
    }

}