package App;

import model.WordSearch;

import java.util.ArrayList;

public class WordSearchConsoleApp {
    private static final int TAM_WORD = 10;
    boolean playing= true;

    public void start() {
        WordSearchUI.wellcome();

        ArrayList<String> wordsSoup = WordSearchUI.init(TAM_WORD);

        WordSearch game = new WordSearch(TAM_WORD, wordsSoup);
        int wordsDiscovered = 0;

        do{
            System.out.println(game);
            String word = WordSearchUI.tryGuess(TAM_WORD);
            if (game.findWord(word)){
                game.coordsOfMatch(word);
                wordsDiscovered++;
            }else{
                System.out.println("Palabra no encontrada, vuelve a intentarlo");
            }
            if (wordsDiscovered==wordsSoup.toArray().length) playing=false;
        }while (playing);

        System.out.println(game);
        WordSearchUI.gameFinished();

    }
}