
package Extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Delgado
 */
public class LeerCorpus {
    
    private int[] id;
    private String[] titulo;
    private String[] texto;
    
    public LeerCorpus(){
        id = new int[6004];
        titulo = new String[6004];
        texto = new String[6004];
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
     * Devuelve el tamaño de los arrays
     * @return
     */
    public int getTama(){
        return this.id.length;
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
        
        String actual = DirAct();
        String carpeta = "\\Coleccion LISA revisada";
        
        String path = actual+carpeta;
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
                        if(partes[partes.length-1].length() == 5){
                            res.add(aux); //se añade
                        }
                    }catch(Exception ex){
                    }
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
     * Devuelve el directorio de trabajo
     * @return
     */
    private String DirAct(){
        //Ver en que directorio estamos trabajando
        String dir = "";
        try {
            dir = new java.io.File( "." ).getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(ConexionSolr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dir;
    }
    
   
    
}
