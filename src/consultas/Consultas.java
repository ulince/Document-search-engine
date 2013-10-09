/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import src.Operaciones;
import persistencia.Query;


/**
 *
 * @author Me
 */
public class Consultas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Operaciones op = new Operaciones();
        //System.out.println(op.Sim2DocRepMej("d1", "d2", "coseno2"));
        Query[] q = new Query[10];
        q = op.SimDocConsRepMej("coseno");
        System.out.println(q[0].getUri());
        System.err.println(q[0].getFrecuencia());
        
    }
}
