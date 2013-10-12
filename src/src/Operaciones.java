package src;

import persistencia.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import org.netlib.util.*;
import org.netlib.lapack.Dgesvd;

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
    public Query[] SimDocConsRepMej(String medida) {
        DBConnection db = new DBConnection();
        Query result[] = new Query[10];

        try {
            result[0] = db.StoredProc(medida);

            for (int i = 2; i < 11; i++) {
                db.AlterConsulta(i - 1, i);
                result[i - 1] = db.StoredProc(medida);
            }
            return result;
        } catch (SQLException e) {
            System.out.println("There was an error.");
            return result;
        }
    }

    //Regresa DT de la descomposicion SVD
    public double[] SVD() {
        double d[] = null;
        try {
            DBConnection db = new DBConnection();
            List<Frecuencia> frecT = db.ReadFrecuencia();
            int M = frecT.size()/10;
            int N = 10;
            d = new double[M * N];
            for (int i = 0; i < M * N; i++) {
                d[i] = (double)frecT.get(i).getFrecuencia();
                //System.out.println("OK");
            }

            double[] s = new double[d.length];
            double[] u = new double[M * M];
            double[] vt = new double[N * N];
            double[] work = new double[Math.max(3 * Math.min(M, N) + Math.max(M, N), 5 * Math.min(M, N))];
            org.netlib.util.intW info = new org.netlib.util.intW(2);

            Dgesvd.dgesvd("A", "A", M, N, d, 0, M, s, 0, u, 0, M, vt,
                    0, N, work, 0, work.length, info);

            System.out.println("info = " + info.val);
             return vt;
        }catch(SQLException e){
            return d;
           //Handle error
        }     
    }
    
    public void createDT(double d[]){
        try{
            DBConnection db = new DBConnection();
            db.InsertDTMatrix(d);
        }catch(Exception e){
            //DO domething
        }
    }
    
    
    
}
