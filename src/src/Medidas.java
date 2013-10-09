/*
 * Esta clase contiene las operaciones de las medidas de similtud y disimilitud entre matrices.
 */
package src;

/**
 *
 * @author Me
 */
public class Medidas {
    
    public Medidas(){
        
    }
    
    //Calcula el producto interno entre 2 documentos
    //La matriz que se pasa es dT, resultado de la aplicación de la técnica LSI a
    //La matriz tiene dimension 10 x k, donde k es...
    public float ProdInterno2Doc(float[][] dT, int docA, int docB){
        float result = 0;
        
        for(int i = 0; i < 10; i++){
            result = result + dT[i][docA]*dT[i][docB];
        }
        return result;
    }
    
     /*public float Coseno2Doc(float[][] dT, int docA, int docB){
        float numerador = 0;
        float temp1 = 0;
        float temp2 = 0;
        
        for(int i = 0; i < 10; i++){
            numerador = numerador + dT[i][docA]*dT[i][docB];
        }
        
        for(int i = 0; i < 10; i++){
            temp1 = temp1 + 
        }      
    }*/
     
}
