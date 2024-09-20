package com.company;

import java.util.Scanner;

public class EstadísticasEspaña {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String[][] data = new String[21][4];
        String[] header = {"PROVINCIA DONDE RESIDE","ESTUDIOS PREVIOS","EDAD","CICLO"};

        boolean exit = false;

        //Datos iniciales alumnado
        data[0][0] = "Barcelona";
        data[0][1] = "CFGM";
        data[0][2] = "25";
        data[0][3] = "ASIR";

        data[1][0] = "Tarragona";
        data[1][1] = "Prueba de acceso";
        data[1][2] = "31";
        data[1][3] = "ASIR";

        data[2][0] = "Barcelona";
        data[2][1] = "Bachillerato";
        data[2][2] = "21";
        data[2][3] = "ASIR";

        data[3][0] = "Barcelona";
        data[3][1] = "CFGS";
        data[3][2] = "27";
        data[3][3] = "ASIR";

        data[4][0] = "Barcelona";
        data[4][1] = "Bachillerato";
        data[4][2] = "41";
        data[4][3] = "ASIR";

        data[5][0] = "Toledo";
        data[5][1] = "CFGS";
        data[5][2] = "37";
        data[5][3] = "ASIR";

        data[6][0] = "Barcelona";
        data[6][1] = "CFGM";
        data[6][2] = "21";
        data[6][3] = "ASIR";

        data[7][0] = "Alicante";
        data[7][1] = "Bachillerato";
        data[7][2] = "21";
        data[7][3] = "ASIR";

        data[8][0] = "Madrid";
        data[8][1] = "CFGS";
        data[8][2] = "26";
        data[8][3] = "ASIR";

        data[9][0] = "Madrid";
        data[9][1] = "Bachillerato";
        data[9][2] = "31";
        data[9][3] = "ASIR";

        data[10][0] = "Córdoba";
        data[10][1] = "CFGM";
        data[10][2] = "28";
        data[10][3] = "ASIR";

        data[11][0] = "Barcelona";
        data[11][1] = "Bachillerato";
        data[11][2] = "38";
        data[11][3] = "DAM";

        data[12][0] = "Tarragona";
        data[12][1] = "CFGM";
        data[12][2] = "19";
        data[12][3] = "DAM";

        data[13][0] = "Sevilla";
        data[13][1] = "CFGS";
        data[13][2] = "41";
        data[13][3] = "DAM";

        data[14][0] = "Zaragoza";
        data[14][1] = "Bachillerato";
        data[14][2] = "22";
        data[14][3] = "DAM";

        data[15][0] = "Valencia";
        data[15][1] = "CFGM";
        data[15][2] = "26";
        data[15][3] = "DAM";

        data[16][0] = "Madrid";
        data[16][1] = "Estudios universitarios";
        data[16][2] = "42";
        data[16][3] = "DAM";

        data[17][0] = "Toledo";
        data[17][1] = "CFGS";
        data[17][2] = "30";
        data[17][3] = "DAW";

        data[18][0] = "Madrid";
        data[18][1] = "Estudios universitarios";
        data[18][2] = "36";
        data[18][3] = "DAW";

        data[19][0] = "Guipuzcoa";
        data[19][1] = "CFGS";
        data[19][2] = "38";
        data[19][3] = "DAW";

        data[20][0] = "Barcelona";
        data[20][1] = "CFGS";
        data[20][2] = "20";
        data[20][3] = "DAW";

        final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        final String ANSI_WHITE = "\u001B[37m";
        final String ANSI_RESET = "\u001B[0m";

        do{
            System.out.println("Bienvenid@ al departamento de estadística");
            System.out.println("¿Qué datos quieres conocer?");
            System.out.println("[1] Mostrar todos los datos");
            System.out.println("[2] % Alumnado con estudios previos Bachillerato o CFGM o CFGS");
            System.out.println("[3] Media de edad del alumnado");
            System.out.println("[4] Mostrar datos por ciudad");
            System.out.println("[5] Corregir");
            System.out.println("[6] Salir");
            if (input.hasNextInt()){
                int opcion = input.nextInt();
                input.nextLine();
                switch (opcion) {
                    case 1://Mostrar todos los datos
                        for(int i=0;i< header.length;i++){
                            System.out.print(header[i]+" |  ");
                        }
                        System.out.println();
                        for (int i=0;i< data.length;i++){
                            for  (int j=0;j<data[i].length;j++){
                                System.out.print(data[i][j]+" | ");
                            }
                            System.out.println();
                        }
                        break;
                    case 2://% Alumnado con estudios previos Bachillerato o CFGM o CFGS

                        int bachillerato = 0;
                        int cfgm = 0;
                        int cfgs = 0;
                        for (int i=0;i< data.length;i++){
                            if (data[i][1].equals("Bachillerato")){
                                bachillerato++;
                            }else if (data[i][1].equals("CFGM")){
                                cfgm++;
                            }else if (data[i][1].equals("CFGS")){
                                cfgs++;
                            }
                        }
                        //limitar visualización 1 decimal
                        System.out.printf("Bachillerato %.1f %n",(double)100*bachillerato/ data.length);
                        System.out.printf("CFGM %.1f %n",(double)100*cfgm/ data.length);
                        System.out.printf("CFGS %.1f %n",(double)100*cfgs/ data.length);
                        break;
                    case 3: //Media de edad del alumnado
                        double mediaEdad = 0;
                        for (int i=0;i< data.length;i++){
                            mediaEdad = mediaEdad + Integer.parseInt(data[i][2]);
                        }
                        //limitar la visualización con 1 decimal
                        System.out.printf("%.1f %n",mediaEdad/ data.length);
                        break;
                    case 4: //Mostrar datos por ciudad
                        System.out.println("Introduce la ciudad de consulta");
                        String ciudad = input.nextLine();
                        boolean existenDatos = false;
                        for (int i=0;i< data.length;i++){
                            if (data[i][0].toLowerCase().contains(ciudad.toLowerCase())){
                                existenDatos = true;
                                for (int j=0;j<data[i].length;j++){
                                    System.out.print(data[i][j]+" | ");
                                }
                                System.out.println();
                            }
                        }
                        System.out.println();
                        if (!existenDatos){
                            System.out.println("No existen datos en la ciudad seleccionada");
                        }
                        break;

                    case 5: //Correcciones estudios previos
                        for (int i=0;i< data.length;i++){
                            for  (int j=0;j<data[i].length;j++) {
                                System.out.print(ANSI_BLACK_BACKGROUND+ANSI_WHITE+data[i][j] + " | ");
                            }
                                System.out.println(ANSI_RESET);
                                System.out.println("    Quieres cambiar estudios previos? Si/No/Terminar");
                                String respuesta = input.next();
                                input.nextLine();
                                if (respuesta.equals("Si")){
                                    System.out.println("    Introduce estudios previos: Bachillerato, CFGM, CFGS, Prueba de acceso, Estudios universitarios");
                                    String seleccion = input.nextLine();
                                    if (seleccion.equals("Bachillerato") ||  seleccion.equals("CFGM") ||  seleccion.equals("CFGS") || seleccion.equals("Prueba de acceso") || seleccion.equals("Estudios universitarios")){
                                        data[i][1] = seleccion;
                                        System.out.println("   Guardados cambios: "+data[i][0] + " | "+data[i][1] + " | "+data[i][2] + " | "+data[i][3] + " | ");
                                    }

                                }else if (respuesta.equals("Terminar")){
                                    System.out.println("Saliendo");
                                    break;
                                }
                            }
                            break;
                    case 6: //Salir
                        exit = true;
                        break;
                    default:
                        System.out.println("Error. Opción incorrecta");
                }
            }else{
                System.out.println("Error. Opción incorrecta");
                input.nextLine();
            }


        }while(!exit);
    }
}
