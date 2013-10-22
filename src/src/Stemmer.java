/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.SnowballStemmer;

/**
 *
 * @author Me
 */
public class Stemmer {

    List<String> stopwords = new ArrayList<String>();

    public Stemmer() {
    }

    //Recibe una la consulta como una cadena y regresa un Vector<String> con los stems de las palabras
    public ArrayList<String> Stem(String s) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String str = "Select * from stopwords";
        ArrayList<String> stems = new ArrayList<String>();
        
        /* Obtener la lista de stopwords de la base de datos */
        try {
            DBConnection db = new DBConnection();
            stopwords = db.readDataBase(str);
        } catch (SQLException e) {
            System.out.println("Error recuperando stopwords");
        }
        
        Class stemClass = Class.forName("org.tartarus.snowball.ext." + "englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
           
        String[] result = s.split("\\s");
        
        for(int i = 0; i < result.length; i++){
            if(stopwords.contains(result[i])){
                continue;
            }
            stemmer.setCurrent(result[i]);
            stemmer.stem();
            stems.add(stemmer.getCurrent());
        }
        
        return stems;
    }
}
