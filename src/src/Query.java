/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author Me
 */
public class Query {
    private String uri;
    private String raiz;
    private float frecuencia;
    
    public Query(String uri, String raiz, float frecuencia){
        this.uri = uri;
        this.raiz = raiz;
        this.frecuencia = frecuencia;
    }
    
    public Query(){
        
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRaiz() {
        return raiz;
    }

    public void setRaiz(String raiz) {
        this.raiz = raiz;
    }

    public float getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(float frecuencia) {
        this.frecuencia = frecuencia;
    }    
}
