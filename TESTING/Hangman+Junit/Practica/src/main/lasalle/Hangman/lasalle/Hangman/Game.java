package lasalle.Hangman;

import java.util.Scanner;

public class Game {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public void start(boolean testMode){
        Scanner scanner = new Scanner(System.in);
        char[] playerGuess;
        boolean weArePlaying = true;
        boolean wordIsGuessed = false;
        boolean opcionCorrecta;
        int allowedTries = 0;
        while(weArePlaying) {
            opcionCorrecta = true;
            System.out.println("Lets Start Playing Hangman ver 0.3");
            Word wordtoGuess = new Word();
            allowedTries = (int)(wordtoGuess.getAmmountOfGuesses()*1.5);
            if (testMode) {
                System.out.println("Test mode, word to guess:" + "\033[33m" + wordtoGuess.getWord() + "\u001B[0m");
            }
            int tries = 0, fails =0;
            playerGuess = new char[wordtoGuess.getAmmountOfGuesses()]; // "_ _ _ _ _ _ _ _"

            for (int i = 0; i < playerGuess.length; i++) { // Assign empty dashes at start "_ _ _ _ _ _ _ _"
                playerGuess[i] = '_';
            }

            while(!wordIsGuessed && tries < allowedTries){
                System.out.println("Current Guesses: ");
                print(playerGuess);
                System.out.printf("You have %d ammount of tries left.\n", allowedTries-tries);
                System.out.println("Enter a single character: ");
                if(scanner.hasNext()) { // se crea estructura if para controlar que se mete un char
                    char input = scanner.next().charAt(0); // se cambia el scanner a next para que solo coja 1 caracter
                    tries++;

                    boolean found = false;
                    for(int i=0; i< wordtoGuess.getWordLenght(); i++){
                        if(wordtoGuess.checkChar(i,input)){
                            playerGuess[i] = input;
                            found = true;
                        }
                    }
                    if(!found){
                        fails++;
                    }
                    if(isTheWordGuessed(playerGuess)){
                        wordIsGuessed = true;
                        System.out.println(ANSI_CYAN +"Congratulations" + ANSI_RESET); //se cambia en color para que se vea en azul
                    }
                }

            }/* End of wordIsGuessed */
            if(!wordIsGuessed){
                System.out.println(ANSI_RED + "You ran out of guesses." + ANSI_RESET); //se cambia a color para que se vea en rojo
            }
            scanner.nextLine();


            while (opcionCorrecta){ //se crea un bucle while para controlar que se introduce una opcion valida
                System.out.println("Would you like to play again? (yes/no) ");
                String choice = scanner.nextLine();
                if (choice.equals("no") || choice.contains("n")){
                    weArePlaying = false;
                    opcionCorrecta = false;
                } else if(choice.equals("yes") || choice.contains("y")){
                    weArePlaying = true;
                    wordIsGuessed = false;
                    opcionCorrecta = false;
                }else{
                    System.out.println("Please insert a valid option");
                    weArePlaying = false;
                    opcionCorrecta= true;

                }
            }

        }/*End of We Are Playing*/
        /*End of We Are Playing*/


        System.out.println("Game Over!");
    }

    public void print(char array[]){
        for(int i=0; i<array.length; i++){ // Assign empty dashes at start "_ _ _ _ _ _ _ _"
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static boolean isTheWordGuessed(char[] array){
        boolean condition = true;
        for(int i=0; i<array.length; i++){
            if(array[i] == '_'){
                condition = false;
            }
        }
        return condition;
    }
}
