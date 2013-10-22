/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import src.Operaciones;
import persistencia.Query;
import src.Stemmer;

/**
 *
 * @author Me
 */
public class Consultas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in;
        Operaciones op;
        int i;
        String a, b;
        ArrayList<String> stemsArray = null;
        double queryMatrix[][];
        double normalisedQuery[][];
        Query queryArray[];
        ArrayList<Query> queryList;
        boolean stop;

        in = new Scanner(System.in);
        op = new Operaciones();
        op.SVD2();
        stop = false;


        while(!stop)
        {
            System.out.println("Elija una opción.");
            System.out.println("1. Comparar dos documentos.");
            System.out.println("2. Comparar una consulta contra todos los documentos.");
            System.out.println("3. Salir.");
            i = in.nextInt();
            in.nextLine();

            switch (i) {
                case 1: { //Comparar dos documentos
                    System.out.println("Comprar dos documentos.");
                    System.out.println("1. Representaciones mejoradas.");
                    System.out.println("2. LSI.");
                    System.out.println("3. Salir.");
                    i = in.nextInt();
                    in.nextLine();

                    switch (i) {//switch
                        case 1: {  //Representaciones mejoradas
                            System.out.println("Comparar dos documentos usando representaciones mejoradas.");
                            System.out.println("1. Peso de términos.");
                            System.out.println("2. Espacios vectoriales.");
                            System.out.println("3. Salir.");
                            i = in.nextInt();
                            in.nextLine();

                            switch (i) {
                                case 1: { //Peso de terminos
                                    System.out.println("Comparar dos documentos usando Peso de términos.");
                                    System.out.println("Similitud.");
                                    System.out.println("1. Producto interno.");
                                    System.out.println("2. Coseno.");
                                    System.out.println("3. Coeficientes de Dice");
                                    System.out.println("4. Coeficientes de Jaccard.");
                                    System.out.println("Disimilitud.");
                                    System.out.println("5. Distancia Euclidiana.");
                                    System.out.println("6. Distanca Manhattan.");
                                    i = in.nextInt();
                                    in.nextLine();

                                    System.out.println("Primer documento (numerados del 0 al 9)");
                                    a = in.nextLine();
                                    System.out.println("Segundo documento:");
                                    b = in.nextLine();

                                    switch (i) {
                                        case 1: //Producto interno
                                            System.out.println(op.Sim2Doc(a, b, "prodInterno2PT"));
                                            break;
                                        case 2://Coseno
                                            System.out.println(op.Sim2Doc(a, b, "coseno2PT"));
                                            break;
                                        case 3://Dice
                                            System.out.println(op.Sim2Doc(a, b, "Dice2PT"));
                                            break;
                                        case 4://Jaccard
                                            System.out.println(op.Sim2Doc(a, b, "jaccard2PT"));
                                            break;
                                        case 5://Euclidiana
                                            System.out.println(op.Sim2Doc(a, b, "distEucl2PT"));
                                            break;
                                        case 6://Manhattan
                                            System.out.println(op.Sim2Doc(a, b, "distManhattan2PT"));
                                            break;
                                    }//end switch
                                    break;
                                }//end Peso de terminos
                                case 2: {//Espacios vectoriales
                                    System.out.println("Comparar dos documentos usando Espacios vectoriales.");
                                    System.out.println("Similitud.");
                                    System.out.println("1. Producto interno.");
                                    System.out.println("2. Coseno.");
                                    System.out.println("3. Coeficientes de Dice");
                                    System.out.println("4. Coeficientes de Jaccard.");
                                    System.out.println("Disimilitud.");
                                    System.out.println("5. Distancia Euclidiana.");
                                    System.out.println("6. Distanca Manhattan.");
                                    i = in.nextInt();
                                    in.nextLine();

                                    System.out.println("Primer documento (numerados del 0 al 9)");
                                    a = in.nextLine();
                                    System.out.println("Segundo documento:");
                                    b = in.nextLine();

                                    switch (i) {
                                        case 1: //Producto interno
                                            System.out.println(op.Sim2Doc(a, b, "prodInterno2EV"));
                                            break;
                                        case 2://Coseno
                                            System.out.println(op.Sim2Doc(a, b, "coseno2EV"));
                                            break;
                                        case 3://Dice
                                            System.out.println(op.Sim2Doc(a, b, "Dice2EV"));
                                            break;
                                        case 4://Jaccard
                                            System.out.println(op.Sim2Doc(a, b, "jaccard2EV"));
                                            break;
                                        case 5://Euclidiana
                                            System.out.println(op.Sim2Doc(a, b, "distEucl2EV"));
                                            break;
                                        case 6://Manhattan
                                            System.out.println(op.Sim2Doc(a, b, "distManhattan2EV"));
                                            break;
                                    }//end switch
                                    break;
                                }//end Espacios vectoriales
                                case 3:{//salir
                                    stop = true;
                                    continue;
                                }//end salir
                            }//end switch
                            break;
                        }//end Representaciones mejoradas
                        case 2: {//LSI
                            System.out.println("Comparar dos documentos usando LSI.");
                            System.out.println("Similitud.");
                            System.out.println("1. Producto interno.");
                            System.out.println("2. Coseno.");
                            System.out.println("3. Coeficientes de Dice");
                            System.out.println("4. Coeficientes de Jaccard.");
                            System.out.println("Disimilitud.");
                            System.out.println("5. Distancia Euclidiana.");
                            System.out.println("6. Distanca Manhattan.");
                            i = in.nextInt();
                            in.nextLine();

                            System.out.println("Primer documento (numerados del 0 al 9)");
                            a = in.nextLine();
                            System.out.println("Segundo documento:");
                            b = in.nextLine();

                            switch (i) {
                                case 1: //Producto interno
                                    System.out.println(op.Sim2Doc(a, b, "prodInterno2dtk"));
                                    break;
                                case 2://Coseno
                                    System.out.println(op.Sim2Doc(a, b, "coseno2dtk"));
                                    break;
                                case 3://Dice
                                    System.out.println(op.Sim2Doc(a, b, "Dice2dtk"));
                                    break;
                                case 4://Jaccard
                                    System.out.println(op.Sim2Doc(a, b, "jaccard2dtk"));
                                    break;
                                case 5://Euclidiana
                                    System.out.println(op.Sim2Doc(a, b, "distEucl2dtk"));
                                    break;
                                case 6://Manhattan
                                    System.out.println(op.Sim2Doc(a, b, "distManhattan2dtk"));
                                    break;
                            }//end switch
                            break;
                        }//end LSI
                        case 3:{//salir
                            stop = true;
                            continue;
                        }//salir
                    }//end switch
                    break;
                }//end comparar dos documentos 
                case 2: {//comparar una consulta contra todos los documentos
                    op.GenericStoredProc("","","consulta");
                    op.GenericStoredProc("","","q");
                    System.out.println("Comparar una consulta contra todos los documentos.");
                    System.out.println("Escriba las palabras consulta separadas por un espacio:");
                    String consulta = in.nextLine();

                    Stemmer stemmer = new Stemmer();
                    try {
                        stemsArray = stemmer.Stem(consulta);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    //Inserta la consulta a la tabla consulta
                    op.createQuery(stemsArray);

                    System.out.println("Elija una opción:");
                    System.out.println("1. Representaciones mejoradas.");
                    System.out.println("2. LSI.");
                    System.out.println("3. Salir.");
                    i = in.nextInt();
                    in.nextLine();

                    switch (i) {
                        case 1: {//Representaciones mejoradas
                            System.out.println("Representaciones mejoradas.");
                            System.out.println("1. Peso de términos.");
                            System.out.println("2. Espacios vectoriales.");
                            System.out.println("3. Salir.");
                            i = in.nextInt();
                            in.nextLine();

                            switch (i) {
                                case 1: { //Peso de terminos
                                    System.out.println("Peso de términos.");
                                    System.out.println("1. Producto interno.");
                                    System.out.println("2. Coseno.");
                                    System.out.println("3. Coeficientes de Dice");
                                    System.out.println("4. Coeficientes de Jaccard.");
                                    i = in.nextInt();
                                    in.nextLine();
                                    System.out.println("Documentos ordenados por relevancia:");

                                    switch (i) {
                                        case 1: //Producto interno
                                            
                                            queryArray = op.SimDocCons("prodInternoPT", "consulta");
                                            /*for(int k = 0;k < queryArray.length;k++){
                                                System.out.println(queryArray[k].getFrecuencia());
                                            } */               
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 2://Coseno
                                            queryArray = op.SimDocCons("cosenoPT", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 3://Dice
                                            queryArray = op.SimDocCons("DicePT", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 4://Jaccard
                                            queryArray = op.SimDocCons("jaccardPT", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                    }//end switch
                                    break;
                                }//end Peso de terminos
                                case 2: { //Espacios vectoriales
                                    System.out.println("Espacios vectoriales.");
                                    System.out.println("1. Producto interno.");
                                    System.out.println("2. Coseno.");
                                    System.out.println("3. Coeficientes de Dice");
                                    System.out.println("4. Coeficientes de Jaccard.");
                                    i = in.nextInt();
                                    in.nextLine();
                                    System.out.println("Documentos ordenados por relevancia:");

                                    switch (i) {
                                        case 1: //Producto interno
                                            queryArray = op.SimDocCons("prodInternoEV", "consulta");
                                           /* for (int k =0;k<queryArray.length;k++){
                                                System.out.println(queryArray[k].getFrecuencia());
                                            }*/                                                                                     
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 2://Coseno
                                            queryArray = op.SimDocCons("cosenoEV", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 3://Dice
                                            queryArray = op.SimDocCons("DiceEV", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                        case 4://Jaccard
                                            queryArray = op.SimDocCons("jaccardEV", "consulta");
                                            queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                            Collections.sort(queryList);
                                            for (int j = 0; j < queryList.size(); j++) {
                                                System.out.println(queryList.get(j).getUri());
                                            }
                                            break;
                                    }//end switch
                                    break;
                                }//end Espacios vectoriales
                                case 3:{//salir
                                    stop = true;
                                    continue;
                                }//salir
                            }//end switch                 
                            break;
                        }//end representaciones mejoradas
                        case 2: {//LSI
                            op.InsertQuery("select * from q");
                            System.out.println("LSI.");
                            System.out.println("1. Producto interno.");
                            System.out.println("2. Coseno.");
                            System.out.println("3. Coeficientes de Dice");
                            System.out.println("4. Coeficientes de Jaccard.");
                            i = in.nextInt();
                            in.nextLine();
                            System.out.println("Documentos ordenados por relevancia:");

                            switch (i) {
                                case 1: //Producto interno
                                    queryArray = op.SimDocCons("prodInternodtk", "q");
                                    queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                    Collections.sort(queryList);
                                    for (int j = 0; j < queryList.size(); j++) {
                                        System.out.println(queryList.get(j).getUri());
                                    }
                                    break;
                                case 2://Coseno
                                    queryArray = op.SimDocCons("cosenodtk", "q");
                                    queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                    Collections.sort(queryList);
                                    for (int j = 0; j < queryList.size(); j++) {
                                        System.out.println(queryList.get(j).getUri());
                                    }
                                    break;
                                case 3://Dice
                                    queryArray = op.SimDocCons("Dicedtk", "q");
                                    queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                    Collections.sort(queryList);
                                    for (int j = 0; j < queryList.size(); j++) {
                                        System.out.println(queryList.get(j).getUri());
                                    }
                                    break;
                                case 4://Jaccard
                                    queryArray = op.SimDocCons("jaccarddtk", "q");
                                    queryList = new ArrayList<Query>(Arrays.asList(queryArray));
                                    Collections.sort(queryList);
                                    for (int j = 0; j < queryList.size(); j++) {
                                        System.out.println(queryList.get(j).getUri());
                                    }
                                    break;
                            }//end switch
                            break;
                        }//end LSI
                        case 3:{//salir
                            stop = true;
                            continue;
                        }//salir
                    }//end switch
                }//end consulta vs documentos
            }//end switch
        }//end while
    }
}
