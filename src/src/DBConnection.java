package src;

import persistencia.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Me
 */
public class DBConnection {

    private String myUrl;
    private Connection conn;

    public DBConnection() {
        myUrl = "jdbc:mysql://localhost:3306/documentos";

    }

    //Metodo que regresa el resultado de la ejecución de una medida de similitud entre 2 documentos
    public float StoredProc2(String d1, String d2, String medida) throws SQLException {
        float result = -1;

        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            CallableStatement cs = null;
            cs = this.conn.prepareCall("{call " + medida + "(?, ?)}");
            cs.setString(1, d1);
            cs.setString(2, d2);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                result = rs.getFloat(medida);
            }
            return result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return result;
        } finally {
            conn.close();
        }
    }

    //Ejecuta la stored procedure medida y regresa el resultado como un objeto de tipo Query; el resultado esta en 
    //el field con el mismo nombre de la medida
    public Query StoredProc(String medida) throws SQLException {
        Query q = new Query();

        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            CallableStatement cs = null;
            cs = this.conn.prepareCall("{call " + medida + "()}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                q.setFrecuencia(rs.getFloat(medida));
                q.setUri(rs.getString("uri1"));
            }
            return q;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return q;
        } finally {
            conn.close();
        }
    }

    public void SP(String str1, String str2, String tabla) throws SQLException {
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            CallableStatement cs = null;

            if (str1.equals("") && str2.equals("")) {
                if (tabla.equals("consulta")) {
                    cs = this.conn.prepareCall("{call restore()}");
                }
                if(tabla.equals("q")){
                    cs = this.conn.prepareCall("{call restore2()}");
                }
            } else {
                if (tabla.equals("consulta")) {
                    cs = this.conn.prepareCall("{call alterar('" + str1 + "','" + str2 + "')}");
                }
                if (tabla.equals("q")) {
                    cs = this.conn.prepareCall("{call alterar2('" + str1 + "','" + str2 + "')}");
                }
            }

            int i = cs.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conn.close();
        }
    }

    //Este metodo se va a llamar para cambiar la uri de la tabla consulta, de modo que sea igual a la del siguiente
    //documento con el que se comparará
    public void AlterConsulta(int nuevo, int anterior, String tabla) throws SQLException {
        String query = "update table " + tabla + " set uri = '" + nuevo + "' where uri = '" + anterior + "'";

        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.execute();
        } catch (Exception e) {
            //
        } finally {
            conn.close();
        }
        //System.out.println("ok1");

    }

    //Insertar una tupla a la tabla consulta por cada palabra que tenga la consulta. Todas se crean con 1 como uri
    //Para ejecutar este metodo, fue necesario parsear la consulta y crear un objeto de tipo Query por cada palabra,
    //y ejecutar este metodo por cada palabra
    public void InsertQuery(Query q) throws SQLException {
        String query = "insert into consulta (uri, raíz) values (?,?) on duplicate key update frecuencia = frecuencia + 1";
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, q.getUri());
            preparedStmt.setString(2, q.getRaiz());
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (Exception e) {
            //Do something
        } finally {
            conn.close();
        }
    }

    //Lee toda la tabla de frecuencias y la regresa como un ArrayList
    public List<Frecuencia> ReadFrecuencia(String query) throws SQLException {
        List<Frecuencia> list = new ArrayList<Frecuencia>();
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            //String query = "select * from frecuencia";


            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String uri = rs.getString("uri");
                String raiz = rs.getString("raíz");
                float frecuencia = rs.getFloat("frecuencia");
                Frecuencia frec = new Frecuencia(uri, raiz, frecuencia);
                list.add(frec);
            }
            return list;

        } catch (Exception e) {
            //System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return list;
        } finally {
            conn.close();
        }
    }

    //Este metdodo inserta en la tabla DT los valores de el arreglo d. d es la matriz DT, 
    //resultado de la descomposicion SVD.       
    public void InsertDTMatrix(double[] d, String sql) throws SQLException {
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            rs.beforeFirst();
            int i = 0;
            while (rs.next() && i < d.length) {
                rs.updateDouble("frecuencia", d[i]);
                rs.updateRow();
                i++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            //Do something
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    //Este método recupera las stopwords de la base de datos y las regresa en una List 
    public List<String> readDataBase(String str) throws SQLException {

        List<String> list = new ArrayList<String>();
        try {
            conn = DriverManager.getConnection(myUrl, "root",
                    "admin");
            // the mysql select statement
            String query = str;

            // Statements allow to issue SQL queries to the database
            Statement statement = conn.createStatement();
            // Result set get the result of the SQL query
            ResultSet rs = statement.executeQuery(query);
            // writeResultSet(resultSet);	

            //Tomar las stopwords del ResultSet y ponerlos en una lista
            while (rs.next()) {
                String temp = rs.getString("words");
                list.add(temp);
            }
            return list;
        } catch (Exception e) {
            //System.err.println("Got an exception!");
            //System.err.println(e.getMessage());		
            return list;
        } finally {
            conn.close();
        }
    }

    //Este metdodo inserta en la tabla q o qk los valores de mas matrices de consultas. 
    //Ejecutar antes y después de hace las transformaciones
    public void InsertQmatrix(double[][] d, String sql) throws SQLException {
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            rs.beforeFirst();
            int i = 0;
            while (rs.next() && i < d[0].length) {
                rs.updateDouble("frecuencia", d[0][i]);
                rs.updateRow();
                i++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            //Do something
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
