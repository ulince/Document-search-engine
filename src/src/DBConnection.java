package src;

import java.sql.*;

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

    //Insert query
    public void InsertQuery(Query q) throws SQLException {
        String query = "insert into frecuencia (uri, raíz) values (?,?) on duplicate key update frecuencia = frecuencia + 1";
        try {
            conn = DriverManager.getConnection(myUrl, "root", "admin");
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, q.getUri());
            preparedStmt.setString(2, q.getRaiz());
            // execute the preparedstatement
            preparedStmt.execute();
        }catch (Exception e){
            //Do something
        }finally{
            conn.close();
        }
    }
}
