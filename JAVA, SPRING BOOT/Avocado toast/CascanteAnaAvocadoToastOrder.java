package com.company;

import java.util.Scanner;


public class CascanteAnaAvocadoToastOrder {

    public static void main (String[] args) {
        CascanteAnaAvocadoToastOrder programa = new CascanteAnaAvocadoToastOrder();
        programa.inicio();
    }
    public void inicio() {
            CascanteAnaAvocadoToast avocadoToast = new CascanteAnaAvocadoToast();
            Scanner input = new Scanner(System.in);
            boolean exit = false;


            do {
                System.out.println("Avocado Toast Order");
                System.out.println("[1] Bread");
                System.out.println("[2] Toast");
                System.out.println("[3] Avocado");
                System.out.println("[4] Topping");
                System.out.println("[5] Salt");
                System.out.println("[6] Pepper");
                System.out.println("[7] Order");
                if (input.hasNextInt()) {
                    int opcion = input.nextInt();
                    input.nextLine();
                    switch (opcion) {
                        case 1:
                            boolean out1 = false;
                            do {
                                System.out.println("Select Bread: Wheat, Multigrain");
                                String bread = input.nextLine();
                                if (bread.equalsIgnoreCase("wheat")) {
                                    avocadoToast.setBread(bread);
                                    out1= true;
                                } else if (bread.equalsIgnoreCase("multigrain")) {
                                    avocadoToast.setBread(bread);
                                    out1= true;
                                } else {
                                    System.out.println("Option out of menu. ");
                                }
                            } while (!out1);
                            break;
                        case 2:
                            boolean out2 = false;
                            do {
                            System.out.println("Select Toast: Lightly toasted, Crunchy");
                            String toast = input.nextLine();
                            if (toast.equalsIgnoreCase("ligthly toast")) {
                                avocadoToast.setToast(toast);
                                out2=true;
                            } else if (toast.equalsIgnoreCase("crunchy")) {
                                avocadoToast.setToast(toast);
                                out2=true;
                            } else {
                                System.out.println("Option out of menu");
                            }
                            } while (!out2);
                            break;
                        case 3:
                            boolean out3 = false;
                            do {
                            System.out.println("Select Avocado: mashed, sliced");
                            String avocado = input.nextLine();
                            if (avocado.equalsIgnoreCase("mashed")) {
                                avocadoToast.setAvocado(avocado);
                                out3=true;
                            } else if (avocado.equalsIgnoreCase("sliced")) {
                                avocadoToast.setAvocado(avocado);
                                out3=true;
                            } else {
                                System.out.println("Option out of menu");
                            }
                            } while (!out3);
                            break;
                        case 4:
                            boolean out4 = false;
                            do {
                            System.out.println("Select Topping: Olive oil, None");
                            String topping = input.nextLine();
                            if (topping.equalsIgnoreCase("Olive Oil")) {
                                System.out.println("Adding Olive oil");
                                avocadoToast.setTopping(topping);
                                out4=true;
                            } else if (topping.equalsIgnoreCase("None")) {
                                System.out.println("No topping added");
                                out4=true;
                            } else {
                                System.out.println("Option out of menu");
                            }
                            } while (!out4);
                            break;
                        case 5:
                            boolean out5 = false;
                            do {
                            System.out.println("Select Salt: Salt, none");
                            String includeSalt= input.nextLine();
                            if (includeSalt.equalsIgnoreCase("Salt")) {
                                System.out.println("Adding Salt");
                                avocadoToast.setIncludeSalt(includeSalt);
                                out5=true;
                            } else if (includeSalt.equalsIgnoreCase("None")) {
                                System.out.println("No Salt");
                                out5=true;
                            } else {
                                System.out.println("Option out of menu");
                            }
                            } while (!out5);
                            break;
                        case 6:
                            boolean out6 = false;
                            do {
                            System.out.println("Select Pepper: Pepper, None");
                            String includePepper =input.nextLine();
                            if (includePepper.equalsIgnoreCase("Pepper")) {
                                System.out.println("Adding Pepper");
                                avocadoToast.setIncludePepper(includePepper);
                                out6=true;
                            } else if (includePepper.equalsIgnoreCase("None")) {
                                System.out.println("No Pepper");
                                out6=true;
                            } else {
                                System.out.println("Option out of menu");
                            }
                            } while (!out6);
                            break;
                        case 7:
                            System.out.println("Showing toast Ingredients");
                            avocadoToast.displayInformation();

                            System.out.println("Showing toast Price");
                            avocadoToast.calculatePrice();

                            exit = true;
                            break;
                        default:
                            System.out.println("Error. Option out of the menu");
                    }
                } else {
                    System.out.println("Error. Character not a number");
                    input.nextLine();
                }


            } while (!exit);


        }

        }









