package Extra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Delgado
 */
public class Tratamiento_Query {
    
    private String consulta;
    private String palabras;
    public Tratamiento_Query(String s){
        consulta = s;
        palabras = "";
    }
    
    public void Limpiar(){
        /*
            Lo que creo que sra mas util, seria elimiarSP, 
            luego un analisis morfologico, quedarnos solo con
            los sutantivos y a estos, hacerle stemming (reduccion a raiz)
        */
        
        EliminarSP();   //Eliminamos los signos de puntuacion y las palabras de menos de 3 caracteres
        StopWords();    //Eliminamos palabras vacias de mas de 3 caracteres
        //Reduccion a la raiz   -->No creo que me sea util
        //Analisis morfologico  --> Lo mas util, cogeria solo los sustantivos
        //Analisis sintactico   -->No creo que me sea util
    }
    
    /**
     * Eliminamos los signos de puntuacion ( ',' '-' '.' )
     * Y las "palabras" de 3 o menos caracteres
     */
    private void EliminarSP(){
        //String[] frases = consulta.split(". "); //Esto separa en un espacio y elimina el caracter anterior
        String[] frases = consulta.split("\\. ");
        //Estudiamos cada una de las oraciones
        for (int i = 0; i < frases.length; i++) { //La ultima es punto y final
            String[] frases2 = frases[i].split("\\, ");
            for (int j = 0; j < frases2.length; j++) {
                String[] palabra = frases2[j].split(" ");
                //Estudiamos las palabras
                for (int k = 0; k < palabra.length; k++) {
                    if(!Ruido(palabra[k])){ //Si tiene menos de 3 carracteres lo considero ruido
                        palabras += palabra[k] + " ";
                    }
                }
            }
        } 
        //System.out.println("Solo ruido: "+palabras);
    }
    
    /**
     * Si tiene 3 caracteres o menos las considero ruido
     * @param s
     * @return
     */
    private boolean Ruido(String s){
        boolean r = false;
        if(s.length() <= 3){
            r = true;
        }
        return r;
    }
    
    /**
     * Elimina las palabras vacias
     * 
     * Voy a coger una lista ya hecha y le añadire alguna que otra palabra
     */
    private void StopWords(){
        String stopWords[]=LeerSW();
        for(int i=0;i<stopWords.length;i++){
            if(palabras.contains(stopWords[i])){
                palabras=palabras.replaceAll(stopWords[i]+"\\s+", ""); //note this will remove spaces at the end
            }
        }
        //System.out.println("Stop_words: "+palabras);
    }
    
    /**
     * Leemos el fichero de stop words
     * @return
     */
    public String[] LeerSW(){
        ArrayList<String> SW = new ArrayList<>();
        String actual = DirAct();
        String archivo = actual+"\\stopwords_en.txt";
        
        File file = new File(archivo);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String s = sc.nextLine();
                if(s.length()>3){
                    SW.add(s.toUpperCase());
                }
            }
        }catch(Exception ex){
        }
        //System.out.println("Total de Stop_Words: "+SW.size());
        String[] Stop_words = new String[SW.size()];
        for(int i =0; i<SW.size(); i++){
            Stop_words[i] = SW.get(i);
        }
        
        return Stop_words;
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
    
    /**
     * Devolvemos la consulta ya limpiada
     * @return
     */
    public String getPalabras(){
        return this.palabras;
    }
    
}
