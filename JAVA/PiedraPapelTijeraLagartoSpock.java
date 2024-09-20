package com.company;


import java.util.Random;
import java.util.Scanner;

public class PiedraPapelTijeraLagartoSpock {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean exit = false;
        int option = 0;

        boolean exitGame = false;
        int user = 0;
        int enemy = 0;
        int userWins = 0;
        int enemyWins = 0;
        int games = 0;
        int gamesCount = 1;


        System.out.println("Rock Paper Scissors Lizard Spock");

        do {
            System.out.println("How many times do you want to play?");
            if (input.hasNextInt()){
                games = input.nextInt();
                input.nextLine();
            }else{
                System.out.println("Error. Invalid choice");
                input.nextLine();
            }
        }while(games<=0);



        do {
            System.out.println("Select your choice");
            System.out.println("[1] ROCK");
            System.out.println("[2] PAPER");
            System.out.println("[3] SCISSORS");
            System.out.println("[4] LIZARD");
            System.out.println("[5] SPOCK");
            System.out.println("[6] EXIT");
            if (input.hasNextInt()) {
                user = input.nextInt();
                input.nextLine();
                if (user>=1&&user<=5){
                    enemy = new Random().nextInt(6-1) + 1;
                    System.out.println("Enemy selection: "+enemy);
                    //Game logic
                    switch (user){
                        case 1: //user: ROCK
                            if (enemy==1){ //enemy: ROCK
                                System.out.print("Tie. ");
                            }else if (enemy==2){ //enemy: PAPER
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy == 3){ //enemy: SCISSORS
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy == 4){ //enemy: LIZARD
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy == 5){ //enemy: SPOCK
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }
                            break;
                        case 2: //user: PAPER
                            if (enemy==1){//enemy: ROCK
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy==2){ //enemy: PAPER
                                System.out.print("Tie. ");
                            }else if (enemy == 3){ //enemy: SCISSORS
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy == 4){ //enemy: LIZARD
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy == 5){ //enemy: SPOCK
                                userWins++;
                                System.out.print("You win. ");
                            }
                            break;
                        case 3: //user: SCISSORS
                            if (enemy==1){//enemy: ROCK
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy==2){ //enemy: PAPER
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy==3){ //enemy: SCISSORS
                                System.out.print("Tie. ");
                            }else if (enemy == 4){ //enemy: LIZARD
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy == 5){ //enemy: SPOCK
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }
                            break;
                        case 4: //user: LIZARD
                            if (enemy==1){//enemy: ROCK
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy==2){ //enemy: PAPER
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy == 3){ //enemy: SCISSORS
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy==4){ //enemy: LIZARD
                                System.out.print("Tie. ");
                            }else if (enemy == 5){ //enemy: SPOCK
                                userWins++;
                                System.out.print("You win. ");
                            }
                            break;
                        case 5: //user: SPOCK
                            if (enemy==1){//enemy: ROCK
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy==2){ //enemy: PAPER
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else if (enemy == 3){ //enemy: SCISSORS
                                userWins++;
                                System.out.print("You win. ");
                            }else if (enemy == 4){ //enemy: LIZARD
                                enemyWins++;
                                System.out.print("Enemy wins. ");
                            }else{ //enemy: SPOCK
                                System.out.print("Tie. ");
                            }
                            break;
                    }

                    System.out.println(" #"+gamesCount+"/"+games+". You: "+userWins+". Enemy: "+enemyWins);
                    gamesCount++;
                    if (gamesCount>games){
                        exitGame = true;
                    }

                }else if (user==6){
                    System.out.println("Bye....");
                    exitGame = true;
                }else{
                    System.out.println("Error. Invalid choice");
                }
            } else {
                input.nextLine();
                System.out.println("Error. Invalid choice");
            }
        } while (!exitGame);

        //Winner
        System.out.println("AND THE WINNER IS ...: ");
        if (userWins==enemyWins) {
            System.out.print("TIE");
        }else if (userWins>enemyWins) {
            System.out.print("USER");
        }else{
            System.out.print("ENEMY");

        }

    }
}
