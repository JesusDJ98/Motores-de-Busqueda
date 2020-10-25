
package Extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Delgado
 */
public class ConexionSolr {
    
    
    
    public ConexionSolr(){
        
    }
    
    public String Conexion(){
        
        /*
        Exista o no la carpeta siempre en la ultima linea pone la direccion url
        Pero para que no tarde mucho leyendo, borraremos la carpeta
        */
        String inicio = "cmd /K cd ";
        String actual = DirAct();
        String carpeta = "\\solr-8.6.3";
        String nameArch = "prueba.txt";
        String ConSolr = " && .\\bin\\solr start -e cloud -noprompt>>./"+nameArch;
        //System.out.println("Conexion: "+inicio+actual+carpeta+ConSolr);
        
        //Abro conexion y creo un archivo con la info de url
        try {
            Runtime.getRuntime().exec(inicio+actual+carpeta+ConSolr);
            //exec.waitFor(); //Espera indefinida
        } catch (IOException ex) {
            //Logger.getLogger(LeerCorpus.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error abriendo conexion con Solr: "+ex);
        }
        
        //Leo el archivo
        String path = actual+carpeta+"\\"+nameArch;
        String url = "";
        
        //Hay que esperar
        String a = "";
        while (a.isEmpty()){
            a = LeerArchivo(path);
        }
        try{
            System.out.println("Conectando");
            Thread.sleep(10000);//10 segundos
        }catch(Exception ex){
        } 
        System.out.println("Conectado");
        a =LeerArchivo(path);
        //Separar esta ultima linea
        StringTokenizer st = new StringTokenizer(a);
        while(st.hasMoreTokens()){
            url = st.nextToken();
        }
        
        //Abro la url en el navegador
        System.out.println("URL: "+url);
        goUrl(url);
        
        return url;
    }
    
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
    
    private String LeerArchivo(String path){
        String url = "";
        
        try {
            File archivo = new File(path);
            Scanner sc = new Scanner(archivo);
            //int i = 0;
            while(sc.hasNextLine()) {
                String actual = sc.nextLine();
                //System.out.println(i+": "+sc.nextLine());
                if(actual.equals("")){
                    //System.out.println(i+": En blanco");
                }else{
                    url = actual; //Devolvera la ultima
                    //System.out.println(i+": "+actual);
                }
                //i++;
            }
            sc.close(); //Cerramos
            archivo.delete();//Eliminamos
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ConexionSolr.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Error leyendo: "+ex);
            url = "";
        }
        return url;
    }
    
    
    /**
     * Funcion que abre el navegador y va a la url
     * pasada por parametro
     * @param url
     */
    public void goUrl(String url){
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
