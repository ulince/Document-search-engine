package src;

import persistencia.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

/**
 *
 * @author Me
 */
public class Operaciones {

    static public double VT[][];
    static public double S[][];
    static public double U[][];
    static public double VTk[][];
    static public double Sk[][];
    static public double Uk[][];

    public Operaciones() {
    }

    //Similitud entre dos documentos usando representaciones mejoradas
    public float Sim2Doc(String d1, String d2, String medida) {
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
    public float Dis2Doc(String d1, String d2, String medida) {
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

    //Calcula la distancia entre una consulta y todos los documentos 
    //result[0] corresponde ala distancia con documento 1, result[1] corresponde al documento 2...
    //el parametro medida se refiere a la medida de similitud que se empleará
    //y tabla se refiere a la tabla consulta que se usará
    public Query[] SimDocCons(String medida, String tabla) {
        DBConnection db = new DBConnection();
        Query result[] = new Query[10];

        try {
            result[0] = db.StoredProc(medida);

            for (int i = 1; i < 10; i++) {
                db.SP(new Integer(i).toString(),new Integer(i-1).toString(),tabla);
                result[i] = db.StoredProc(medida);
            }
            return result;
        } catch (SQLException e) {
            System.out.println("There was an error.");
            return result;
        }
    }

    //Inserta vT a la base de datos
    //String puede ser 
    //Select * from Dt
    public void createDT(double d[], String str) {
        try {
            DBConnection db = new DBConnection();
            db.InsertDTMatrix(d, str);
        } catch (Exception e) {
            //DO domething
        }
    }

    //Inserta la consulta (ArrayList de Strings) a la tabla consulta
    public void createQuery(ArrayList<String> str) {
        try {
            DBConnection db = new DBConnection();
            for (int i = 0; i < str.size(); i++) {
                Query q = new Query();
                q.setRaiz(str.get(i));
                q.setUri("0");
                db.InsertQuery(q);
            }

        } catch (SQLException e) {
            //Do something
        }
    }

    //Obtiene de la tabla consultas una matriz java como un double[][]
    public double[][] ConsultaAmatriz() {
        double d[][] = null;
        try {
            DBConnection db = new DBConnection();
            List<Frecuencia> consultas = db.ReadFrecuencia("select * from consulta");
            d = new double[consultas.size()][1];

            //Crea la matriz doubles a partir de consulta
            for (int i = 0; i < consultas.size(); i++) {
                d[i][0] = (double) consultas.get(i).getFrecuencia();
                //System.out.println("OK");
            }

            return d;

        } catch (SQLException e) {
            return d;
            //Do something
        }
    }

    //Lee la matriz de frecuencias de la base de datos y la regresa como un double[]
    public double[] frecTaArray() {
        double d[] = null;
        try {
            DBConnection db = new DBConnection();
            List<Frecuencia> frecT = db.ReadFrecuencia("select * from frecuencia");
            int M = frecT.size() / 10;
            int N = 10;
            d = new double[M * N];

            //Crea la matriz doubles a partir de frecT
            for (int i = 0; i < M * N; i++) {
                d[i] = (double) frecT.get(i).getFrecuencia();
                //System.out.println("OK");
            }

            return d;
        } catch (SQLException e) {
            return d;
            //Handle error
        }
    }

    //Lee la matriz de frecuencias de la base de datos y la regresa como un double[][]
    public double[][] frecTaMatriz() {
        double d[][] = null;
        try {
            DBConnection db = new DBConnection();
            List<Frecuencia> frecT = db.ReadFrecuencia("select * from frecuencia");
            int M = frecT.size() / 10;
            int N = 10;
            d = new double[M][N];

            int k = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (k < frecT.size()) {
                        d[j][i] = (double) frecT.get(k).getFrecuencia();
                        k++;
                    }
                }
            }
            return d;
        } catch (SQLException e) {
            return d;
            //Handle error
        }
    }

    //Ejecuta SVD
    public void SVD2() {
        RealMatrix A = MatrixUtils.createRealMatrix(frecTaMatriz());
        SingularValueDecomposition svd = new SingularValueDecomposition(A);
        this.VT = svd.getVT().getData();
        this.S = svd.getS().getData();
        this.U = svd.getU().getData();
    }

    //Reduce las matrices S, U y VT en k
    //Ejecutar despues de SVD2
    public void TransformarMatrices(int k) {
        this.Sk = new double[k][k];
        this.Uk = new double[this.U.length][k];
        this.VTk = new double[k][this.VT[0].length];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                Sk[i][j] = S[i][j];
            }
        }

        for (int i = 0; i < this.U.length; i++) {
            for (int j = 0; j < k; j++) {
                Uk[i][j] = U[i][j];
            }
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < this.VT[0].length; j++) {
                VTk[i][j] = VT[i][j];
            }
        }
    }

    //Regresa la consulta transformada como un double[][]
    //Ejecutar despues de TransformarMatrices()
    public double[][] TransformarConsulta() {
        RealMatrix q = MatrixUtils.createRealMatrix(ConsultaAmatriz());
        RealMatrix s = MatrixUtils.createRealMatrix(this.S);
        RealMatrix u = MatrixUtils.createRealMatrix(this.U);
        RealMatrix qT = q.transpose();

        RealMatrix sInverse = new LUDecomposition(s).getSolver().getInverse();

        RealMatrix temp1 = qT.multiply(u);
        RealMatrix temp2 = temp1.multiply(sInverse);

        return temp2.getData();
    }

    //El string es el query, por ejemplo select * from q
    public void InsertQuery(String sql) {
        try {
            DBConnection db = new DBConnection();
            db.InsertQmatrix(TransformarConsulta(), sql);
        } catch (SQLException e) {
        }
    }

    //Restaura consulta a su  estado original
    public void GenericStoredProc(String str1, String str2,String tabla) {
        try{
            DBConnection db = new DBConnection();
            db.SP(str1,str2,tabla);
        }catch(SQLException e){
            //Do something
        }
    }
}
