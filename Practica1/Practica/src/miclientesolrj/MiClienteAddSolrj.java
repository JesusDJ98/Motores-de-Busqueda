
package miclientesolrj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
/**
 *
 * @author Jesus Delgado
 */
public class MiClienteAddSolrj {
    
    private int[] id;
    private String[] titulo;
    private String[] texto;
    
    /**
     * Constructor
     */
    public MiClienteAddSolrj(){
        id = new int[6004];
        titulo = new String[6004];
        texto = new String[6004];
    }
    
    /**
     * Lee todos los ficheros del directorio predeterminado propio
     * @throws FileNotFoundException
     */
    public void LeerFicheros() throws FileNotFoundException{
        boolean fin_titulo = false;
        int identificador = 0; 
        String tit="";
        String tex="";
        
        String path = "C:\\Users\\Jesus Delgado\\Desktop\\Prueba\\Coleccion LISA revisada";
        String[] files = getFiles( path );
        
        for(int k=0; k<files.length; k++){
            String fin = path+"\\"+files[k];
            //System.out.println("Directorio: "+fin);
            Scanner sc = new Scanner(new File(fin));
            int i = 0;
            while(sc.hasNextLine()) {
                if(i==0){ //Donde se encuentra el documento
                    String aux = sc.nextLine();
                    //System.out.println(aux);
                    String partes[] = aux.split(" ");
                    identificador = Integer.parseInt(partes[partes.length-1]);
                    this.id[identificador-1] = identificador; //No muy importante pues estan en orden

                }else{
                    String aux = sc.nextLine();
                    if(aux.equals("********************************************")){ //Fin del documento
                        //Guardamos los datos
                        this.titulo[identificador-1]=tit;
                        this.texto[identificador-1]=tex;
                        //Reiniciamos los valores
                        tit="";
                        tex="";
                        fin_titulo = false;
                        i=-1; 
                    }else{
                        if(aux.equals("")){ //Separacion entre tirulo y texto
                            fin_titulo = true;
                        }
                        if(fin_titulo){ //Lo agregamos al texto
                            tex += aux;
                        }else{          //Lo agregamos al titulo
                            tit += aux;
                        }
                    }
                }
                i++;
            }
            sc.close();
        }
        System.out.println("Titulo del ultimo: "+this.titulo[this.titulo.length-1]);
    }
    /**
     * Coge TODOS los archivos del directorio pasado por parametro
     * @param path
     * @return
     */
    private String[] getFiles(String path){
        String[] lista_archivos = null;
        
        File f = new File(path);
        
        if(f.isDirectory()){ //Si es un directorio
            
            List<String> res = new ArrayList<>();
            File[] arr_content = f.listFiles();
            
            for(int i=0; i<arr_content.length; i++){
                if( arr_content[i].isFile()){ //Si es un archivo
                    String aux = arr_content[i].getName();
                    
                    String partes[] = aux.split("LISA");
                    try{
                        //System.out.println("Longitud: "+partes[partes.length-1].length());
                        if(partes[partes.length-1].length() == 5){
                            res.add(aux); //se añade
                            /* No me deja convertirlo a punto por eso he separado en 
                            LISA y luego el if del tamaño de 5
                            
                            int a = Integer.parseInt(partes[partes.length-1]);
                            System.out.println("Numero: "+a);*/
                        }

                    }catch(Exception ex){
                        //System.out.println(aux+" : Fallo");
                    }
                    //System.out.println(" ");
                }
            }
            //Paso res a lista_archivos
            lista_archivos = new String[res.size()];
            for(int i=0; i<res.size(); i++){
                lista_archivos[i] = res.get(i);
            }
        }else{
            System.out.println("Directorio incorrecto");
        }
        return lista_archivos;
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
    
    /**
     * Devuelve el tamaño de los arrays
     * @return
     */
    public int getTama(){
        return this.id.length;
    }
    
    /**
     * Devuelve los identificadores de los documentos
     * @return
     */
    public int[] getId(){
        return this.id;
    }
    
    /**
     * Devuelve los titulos de los documentos
     * @return
     */
    public String[] getTitle(){
        return this.titulo;
    }
    
    /**
     * Devuelve los textos de los documentos
     * @return
     */
    public String[] getText(){
        return this.texto;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SolrServerException{
        //Objetos que usare
        int[] IdDoc;
        String[] Titulo;
        String[] Texto;
        int tama;
        MiClienteAddSolrj p = new MiClienteAddSolrj();
        
        try{
            //Ver en que directorio estamos trabajando
            String current = new java.io.File( "." ).getCanonicalPath();
            System.out.println("Current dir:"+current);
            //Ejecuto el cambio de directorio y el comando para abrir la conexion con solr
            //en la misma linea de comando y lo guardo en un fichero
            /*String cmd = "cmd /K cd C:\\Users\\Jesus Delgado\\Desktop\\2020-2021\\"
                    + "1erCuatrimestre\\MiGitHub\\MB\\Motores-de-Busqueda\\Practica1\\"
                    + "Practica\\solr-8.6.3"
                    + " && .\\bin\\solr start -e cloud -noprompt>>./prueba.txt";
            Runtime.getRuntime().exec(cmd);
            
            //Leo dicho fichero para coger la url
            String url = "";
            
            //Lanzo la url para ver los cambios
            //p.goUrl(url);*/
            
            
            //Thread.sleep(5000);
        
        }catch(Exception ex){
            System.out.println("Error: "+ex);
        }
        
        
        //Creo el cliente
        /*HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983"
                + "/solr/gettingstarted").build();
        
        //Leo los archivos
        p.LeerFicheros();
        tama = p.getTama();
        IdDoc = p.getId();
        Titulo = p.getTitle();
        Texto = p.getText();
        
        //Creo los documentos SOLR
        for(int i=0; i<tama; i++){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", IdDoc[i]);
            doc.addField("title", Titulo[i]);
            doc.addField("text", Texto[i]);
            solr.add(doc);
            solr.commit();
        }
        //*/
    }
    
}
