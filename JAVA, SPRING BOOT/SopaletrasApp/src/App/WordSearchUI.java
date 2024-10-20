package App;

import java.util.ArrayList;
import java.util.Scanner;

public class WordSearchUI {
    static String name = null;
    static String input = null;
    static ArrayList<String> wordsSoup = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    /**
     * Metodo que da la bienvenida y capta el nombre
     *
     */
    public static void wellcome(){
        System.out.println("/////////////////////////////////////////////////");
        System.out.println("////////////BIENVENIDO A WORDSEARCH//////////////");
        System.out.println("/////////////////////////////////////////////////");
        System.out.println();
        System.out.println("Por favor dime cual es tu nombre para poder dirijirme a ti.");
        name = sc.nextLine();
    }

    public static ArrayList<String> init(int TAM_WORD) {
        System.out.println("Comencemos a jugar *"+ name + "*");
        wordsSoup = insertWords(TAM_WORD);
        return wordsSoup;
    }

    /**
     * Metodo que interactua con el usuario para poder leer todas (5) las palabras introducidas y devolverlas en un array
     *
     * @return array de Strings que contiene las 5 palabras.
     */
    public static ArrayList<String> insertWords(int TAM_WORD) {

        System.out.println("Por favor inserte unas palabras para configurar nuestra sopa de letras");
        System.out.println("Para acabar de insertar palabras escriba end ");
        int numWords=0;
        do {
            input = sc.nextLine();

            if (input.equals("end")) {
                break;
            }

            if (wordValidator(input, TAM_WORD)) {
                input = input.toUpperCase();
                wordsSoup.add(input);
                numWords++;
            }


        } while (!input.equals("end"));


        if (!rightWords(wordsSoup)){
            wordsSoup.clear();
            insertWords(TAM_WORD);
        }
        System.out.println("numero de palabras :"+numWords);
        return wordsSoup;
    }
    /**
     * Metodo que interactua con el usuario para que introduzca 1 palabra
     *
     * @return String que contiene la palabra.
     */
    public static String tryGuess (int TAM_WORD) {

        do {
            System.out.println("¿Encuentras alguna palabra en la sopa? ESCRÍBELA!");
            System.out.println("Si desea salir escriba end ");
            input = sc.nextLine();

            if (input.equals("end")) {
                bye();
            }

            wordValidator(input,TAM_WORD);

        } while (!wordValidator(input, TAM_WORD));


        return input.toUpperCase();
    }

    /**
     * Metodo que verifica que la palabra sea correcta. Que no contenga ningún caracter especial ni espacios. Acepta acentos.
     * TIENE LOS MENSAJES INCLUIIDOS
     *
     * @return boolean si la palabra es correcta
     */
    private static boolean wordValidator(String word, int TAM_WORD){
        boolean r = true;
        if (word.length() > TAM_WORD) {
            System.out.println("Tamaño no soportado,  no puede ser superior a: " + TAM_WORD);
            r = false;
        }

        if (word.matches(".*[^\\p{L}].*")) {
            System.out.println("El string contiene caracteres especiales, PALABRA NO VALIDA");
            r = false;
        }

        return r;
    }
    /**
     * Metodo que Imprime por pantalla todas las palabras introducidas, y devuelve si está de acuerdo o no con lo introducido.
     *
     * @return boolean si está de acuerdo o no con las palabras.
     */
    public static boolean rightWords(ArrayList<String> wordsSoup) {
        while(true) {
            System.out.println("Las palabras elegidas son: " + wordsSoup);
            System.out.println("¿Está de acuerdo con estas palabras?\n SI/NO.");
            input = sc.nextLine();
            input = input.toUpperCase();

            if (input.equals("S")||input.equals("SI")) {
                return true;
            } else if (input.equals("N")||input.equals("NO")) {
                return false;
            }else {
                System.out.println("Ingrese una respuesta válida");
            }
        }
    }
    public static void gameFinished() {
        System.out.println("*************************************************");
        System.out.println("********* ENHORABUENA LO HAS CONSEGUIDO *********");
        System.out.println("*************************************************");
        System.out.println();
        System.out.println("Gracias por jugar "+name);
        bye();
    }
    /**
     * Metodo de despedida, cierra el programa.
     **/
    private static void bye() {
        System.out.println("Un placer haberte conocido.");
        System.exit(0);
    }
}
