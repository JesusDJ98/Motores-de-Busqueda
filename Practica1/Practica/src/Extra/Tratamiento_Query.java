package Extra;



import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jesus Delgado
 */
public class Tratamiento_Query {
    
    private String consulta;
    private ArrayList<String> palabras;
    public Tratamiento_Query(String s){
        consulta = s;
        palabras = new ArrayList<>();
    }
    
    public void Limpiar(){
        Tokenizer();            //Eliminamos los signos de puntuacion
        StopWords();            //Eliminamos palabras vacias
        MaxDuplicatedToken();   //Cogemos unicamente las palabras mas repetidas
        PalabrasClaves();       //Si tiene + de 2 palabras coger solo los sutantivos y adjetivos
        /*DuplicateToken();     //Eliminamos palabras repetidas
        PalabrasClaves();//*/
    }
    
    /**
     * Eliminamos los signos de puntuacion ( ',' '-' '.' )
     * Y las "palabras" de 3 o menos caracteres
     */
    private void Tokenizer(){
        String[] words = consulta.split(" ");
        for (int i = 0; i < words.length; i++) {
            if(!Ruido(words[i]) && !palabras.contains(words[i])){ 
                palabras.add(words[i].toLowerCase());
            }
        }
        //System.out.println("Cantidad Inicial: "+palabras.size());
        
        //Eliminamos signos de puntuacion
        ArrayList<String> SP = new ArrayList<>();
        SP.add(".");SP.add(",");SP.add(":");SP.add(";");
        SP.add("!");SP.add("¡");SP.add("?");SP.add("¿");SP.add("&");
        SP.add(")");SP.add("(");SP.add("$");SP.add("%");SP.add("_");
        SP.add("[");SP.add("]");SP.add("{");SP.add("}");SP.add("=");
        
        
        for (int i = 0; i < palabras.size(); i++) {
            String P = palabras.get(i);
            //Cojemos el primer caracter de cada y si es un de estos tb miramos el siguiente
            boolean Primer = true;
            int j = 0;
            while(j < P.length() && Primer){
                String letra = ""+P.charAt(j);
                if(!SP.contains(letra)){
                    Primer = false;
                }
                j++;
            }
            
            //Cojemos el ultimo caracter de cada y si es un de estos tb miramos el anterior
            boolean Ultimo = true;
            int k = P.length()-1;
            while(k >= 0 && Ultimo){
                String letra = ""+P.charAt(k);
                if(!SP.contains(letra)){
                    Ultimo = false;
                }k--;
            }
            
            
            //Recontruimos la palabra
            if( ((k+1) == (P.length()-1)) && ((j-1) == 0) ){    //La palabra no cambia
                
            }else{
                String WordFinal = "";
                for (int l = j-1; l <= k+1; l++) {
                    WordFinal+=P.charAt(l);
                }
                palabras.set(i, WordFinal);
            }
            
        }
        
    }
    
    /**
     * Si tiene 3 caracteres o menos las considero ruido
     * @param s
     * @return
     */
    private boolean Ruido(String s){
        boolean r = false;
        if(s.equals(" ") || s.length() == 0){
            r = true;
        }
        return r;
    }
    
    private void MaxDuplicatedToken(){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();         //Orende introducido
        for (int i = 0; i < palabras.size(); i++) {
            String s = palabras.get(i);
            if( !map.containsKey(s)){ //Para tenerlo en orden
                int cant = 1;
                map.put(palabras.get(i), cant);
            }else{
                int cant = map.get(s);
                cant++;
                map.replace(s, cant);
            }
        }
        //Cogemos el que tenga el value mayor
        String w= "";
        int mayor = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String clave = entry.getKey();
            int veces = entry.getValue();
            //System.out.println("clave=" + clave + ", valor=" + veces);
            if(veces > mayor){
                mayor = veces;
                w = clave;
            }
        }
        //System.out.println("La palabra mas repetida es: "+w+" con "+mayor);
        
        //Cogemos TODAS las palabras que tengan una repeticion igual
        ArrayList<String> Aux = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String clave = entry.getKey();
            int veces = entry.getValue();
            if(veces == mayor){ 
                Aux.add(clave);
            }
        }
        //palabras = new ArrayList<>();
        palabras = Aux;
    }
    
    /*
    Elimina palabras duplicadas, para esto, lo introduzco todo en un hashmap
    */
    private void DuplicateToken(){
        //HashMap<String, Integer> map = new HashMap<String, Integer>();    //Orden alfabetico
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();         //Orende introducido
        for (int i = 0; i < palabras.size(); i++) {
            String s = palabras.get(i);
            if( !map.containsKey(s)){ //Para tenerlo en orden
                map.put(palabras.get(i), i);
            }
        }
        palabras = new ArrayList<>();
        palabras.addAll(map.keySet());
    }
    
    /**
     * Elimina las palabras vacias
     * 
     * Voy a coger una lista ya hecha y le añadire alguna que otra palabra
     */
    private void StopWords(){
        ArrayList<String> stopWords = LeerSW();
        //Añado palabras vacias especificas de estas consultas
        stopWords.add("identification"); stopWords.add("investigation");
        stopWords.add("instance");stopWords.add("information");
        stopWords.add("documentation"); stopWords.add("evaluation");
        stopWords.add("interested");
        palabras.removeAll(stopWords);
    }
    
    private void PalabrasClaves(){
        if(palabras.size() > 2){
            ArrayList<String> aux = new ArrayList<>();
            String s = "Tag/english-left3words-distsim.tagger";
            MaxentTagger tag = new MaxentTagger(s);
            //tag.addTag(s);
            tag.tagString(s);
            for (int i = 0; i < palabras.size(); i++) { //Recorremos todo nuestros token
                String palabra = palabras.get(i);
                String t = tag.tagString(palabra);
                String[] partes = t.split(" ");
                for (int j = 0; j < partes.length; j++) {   //Realmente solo es una pero por si acaso
                    String salida = partes[j];  //palabra/Tipo
                    String[] p2 = salida.split("_");
                    String tipo = ""+p2[1].charAt(0)+p2[1].charAt(1);
                    if(tipo.equals("NN") || tipo.equals("JJ")){
                        aux.add(palabra);
                    }
                }
            }
            palabras = aux;
        } 
    }
    
    /**
     * Leemos el fichero de stop words
     * @return
     */
    public ArrayList<String> LeerSW(){
        ArrayList<String> SW = new ArrayList<>();
        String actual = DirAct();
        String archivo = actual+"\\stopwords.txt";
        
        File file = new File(archivo);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String s = sc.nextLine();
                SW.add(s);
            }
        }catch(Exception ex){
        }
        
        return SW;
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
    public ArrayList<String> getPalabras(){
        return this.palabras;
    }
    
    public String PalabrasString(){
        String s = "";
        for(int i = 0; i< this.palabras.size(); i++){
            s += this.palabras.get(i)+" ";
        }
        
        return s;
    }
    
}
