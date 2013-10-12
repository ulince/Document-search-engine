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

    //Este metodo se va a llamar para cambiar la uri de la tabla consulta, de modo que sea igual a la del siguiente
    //documento con el que se comparará
    public void AlterConsulta(int a, int b) throws SQLException {
        String query = "update table consulta set uri = d" + a + " where uri = d" + b;

        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.execute();
        } catch (Exception e) {
            //
        } finally {
            conn.close();
        }
    }

    //Insertar una tupla a la tabla consulta por cada palabra que tenga la consulta. Todas se crean con 1 como uri
    //Para ejecutar este metodo, fue necesario parsear la consulta y crear un objeto de tipo Query por cada palabra,
    //y ejecutar este metodo por cada palabra
    public void InsertQuery(Query q) throws SQLException {
        String query = "insert into frecuencia (uri, raíz) values (?,?) on duplicate key update frecuencia = frecuencia + 1";
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
    public List<Frecuencia> ReadFrecuencia() throws SQLException {
        List<Frecuencia> list = new ArrayList<Frecuencia>();
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            String query = "select * from frecuencia";


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
    //resultado de ladescomposicion SVD.       
    public void InsertDTMatrix(double[] d) throws SQLException {
        String sql = "SELECT * FROM DT";
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

}
