package Extra;

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
        EliminarSP();   //Eliminamos los signos de puntuacion y las palabras de menos de 3 caracteres
        StopWords();    //Eliminamos palabras vacias de mas de 3 caracteres
    }
    
    /**
     * Eliminamos los signos de puntuacion ( ',' '-' '.' )
     * Y las "palabras" de 3 o menos caracteres
     */
    private void EliminarSP(){
        //ArrayList<String> palabrasInfo = new ArrayList<>();
        //String[] frases = consulta.split(". "); //Esto separa en un espacio y elimina el caracter anterior
        String[] frases = consulta.split("\\. ");
        //System.out.println("Lo que me llega: "+consulta);
        //System.out.println(" ");
        //Estudiamos cada una de las oraciones
        for (int i = 0; i < frases.length; i++) { //La ultima es punto y final
            //System.out.println("i: "+i+" "+ frases[i]);
            String[] frases2 = frases[i].split("\\, ");
            for (int j = 0; j < frases2.length; j++) {
                //System.out.println("j: "+j+" "+ frases2[j]);
                String[] palabra = frases2[j].split(" ");
                //Estudiamos las palabras
                for (int k = 0; k < palabra.length; k++) {
                    //System.out.println("k: "+k+" "+ palabra[k]);
                    if(!Ruido(palabra[k])){ //Si tiene menos de 3 carracteres lo considero ruido
                        //palabrasInfo.add(palabra[k]);
                        palabras += palabra[k] + " ";
                    }
                }
            }
        } 
        System.out.println("Solo ruido: "+palabras);
        
        //Pasamos todas las palabras a la variable global
        /*String conjunto = "";
        this.palabras = new String[palabrasInfo.size()];
        for(int i = 0; i < palabrasInfo.size(); i++){
            this.palabras[i] = palabrasInfo.get(i);
            conjunto += this.palabras[i]+" ";
        }
        //System.out.println("final: "+conjunto); */
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
    public void StopWords(){
        String stopWords[]={"INTERESTED","IDENTIFICATION","EVALUATION", "INSTANCE"};
        for(int i=0;i<stopWords.length;i++){
            if(palabras.contains(stopWords[i])){
                palabras=palabras.replaceAll(stopWords[i]+"\\s+", ""); //note this will remove spaces at the end
            }
        }
        System.out.println("Stop_words: "+palabras);
    }
    
    public String getPalabras(){
        return this.palabras;
    }
    
}
