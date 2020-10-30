
package Extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Delgado
 */
public class ConexionSolr {
    
    String inicio;
    String actual;
    String carpeta;
    String ConexionSolr;
    String CloseSolr;
    String Core;
    
    
    public ConexionSolr(){
        inicio = "cmd /c ";
        actual = "cd " + DirAct();
        carpeta = "\\solr-8.6.3";
        ConexionSolr = "bin\\solr start";
        //Core = " && .\\bin\\solr.cmd create -c micoleccion";//NO muestra nada pues se queda pillado
        Core = "bin\\solr.cmd create -c micoleccion"; 
        CloseSolr = " && .\\bin\\solr stop -all";
    }
    
    public void Conexion(){
        
        //Abro conexion con SOLR
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr.cmd start");
        } catch (IOException ex) {
            System.out.println("Error abriendo conexion con Solr: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(5000);//5 segundos
        }catch(Exception ex){
        }
        //Abro conexion con SOLR
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr.cmd create -c micoleccion");
        } catch (IOException ex) {
            System.out.println("Error creando CORE: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(3000);//3 segundos
        }catch(Exception ex){
        }
        
        
        String url = "http://localhost:8983/solr/#/micoleccion/core-overview";
        
        //Abro la url en el navegador
        //System.out.println("URL: "+url);
        goUrl(url);
        
    }
    
    /**
     * Cerramos la conexion con SOLR
     */
    public void CerrarConexion(){
        //Abro conexion con SOLR
        try {
            Runtime.getRuntime().exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr stop -all");
        } catch (IOException ex) {
            System.out.println("Error cerrando conexion con Solr: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(1000);
        }catch(Exception ex){
        }
    }
    
    /**
     * Coge el directorio actual de trabajo
     * @return
     */
    private String DirAct(){
        //Ver en que directorio estamos trabajando
        String dir = "";
        try {
            dir = new java.io.File( "." ).getCanonicalPath();
        } catch (IOException ex) {
            //Logger.getLogger(ConexionSolr.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error directorio actual: "+ex);
        }
        return dir;
    }
    
    /**
     * Funcion que abre el navegador y va a la url
     * pasada por parametro
     * @param url
     */
    private void goUrl(String url){
        if(java.awt.Desktop.isDesktopSupported())
        {
            java.awt.Desktop escritorio = java.awt.Desktop.getDesktop();
            if(escritorio.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI uri = new java.net.URI(url);
                    escritorio.browse(uri);
                }catch(Exception ex){
                    System.out.println("Error abriendo navegador: "+ex);
                }
            }
        }
    }
    
}
