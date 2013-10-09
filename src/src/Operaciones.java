package src;

import persistencia.Query;
import java.sql.*;
import java.util.Arrays;

/**
 *
 * @author Me
 */
public class Operaciones {

    public Operaciones() {
    }

    //Similitud entre dos documentos usando representaciones mejoradas
    public float Sim2DocRepMej(String d1, String d2, String medida) {
        DBConnection db = new DBConnection();
        float result = -1;

        try {
            result = db.StoredProc2(d1, d2, medida);
            return result;
        } catch (SQLException e) {
            System.out.println("There was an error.");
            return result;
        }
    }

    //Disimilitud entre 2 documentos usando representaciones mejoradas
    public float Dis2DocRepMej(String d1, String d2, String medida) {
        DBConnection db = new DBConnection();
        float result = -1;

        try {
            result = db.StoredProc2(d1, d2, medida);
            return result;
        } catch (SQLException e) {
            System.out.println("There was an error.");
            return result;
        }
    }

    //Calcula la distancia entre una consulta y todos los documentos usando representaciones mejoradas
    //result[0] corresponde ala distancia con documento 1, result[1] corresponde al documento 2...
    //el parametro medida se refiere a la medida de similitud que se empleará
    public Query[] SimDocConsRepMej(String medida){
        DBConnection db = new DBConnection();
        Query result[] = new Query[10];
        
        try {
            result[0] = db.StoredProc(medida);

        for (int i = 2; i < 11; i++) {
            db.AlterConsulta(i-1, i);
            result[i-1] = db.StoredProc(medida);       
        }             
        return result;      
            } catch (SQLException e) {
                System.out.println("There was an error.");
                return result;
            }
        }
    }

