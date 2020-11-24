package Extra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Delgado
 */
public class Trec_Eval {
    //private Dato
    private int doc;
    private int query;
    private PrintWriter pw;
    
    
    public Trec_Eval(int documentos, int consultas/* Los datos */){
        //Le introduzco los datos
        doc=documentos;
        query=consultas;
    }
    
    private void TratamientoDatos(String[] salida){
        String s = DirAct();
        CreoFichero(s);
        
        int i = 0; //Numero de la query
        int pos = 0; //Posicion de salida
        int pos2 = 0; //va de 0 a 4+5por cada documento devuelto
        String id = "";
        while(i < query){
            String query = "";
            //Determino cual es la query que estamos realizando
            boolean nuevo = false;
            int numDoc = 0; //Numero de documentos de la consulta actual
            String[] aux = new String[numDoc];   //Contiene el id y score de todos los doc de la consulta
            while(!nuevo){
                if(salida[pos].equals("--------------")){
                    pos2=0; //Reinicio
                    nuevo = true; //Sale
                }else{
                    if(pos2 == 0){
                        String[] partes = salida[pos].split(": ");
                        String[] partes2 = partes[1].split(" ");
                        query = partes2[0];
                    }else if(pos2 == 1){
                        String[] partes = salida[pos].split(": ");//0: Documentos: -1:num
                        numDoc = Integer.parseInt(partes[1]);
                    }else{
                        aux = new String[numDoc];
                        int incremento = 5;
                        for (int j = 0; j < numDoc; j++) {
                            String[] partes = salida[pos].split(": ");  //0:id: 1:numId
                            String[] partes2 = salida[pos+2].split(": ");//0:score: 1: numScore
                            aux[j] = partes[1] + " , " +partes2[1];

                            pos+=incremento;
                            //pos2+=incremento; //No necesario
                        }
                    }
                    pos2++; //Solo me importa el 0 1 y cualquier otro
                    pos++;
                }
                
                /*
                Ahora escribo todos los documentos comparandolos
                con los del aux, y si es igual escribo su score
                Sino simplemente escribo lo normal
                */
                //Escribo
                String NumConsulta = query;
                int controlador = 0;    //el proximo id de la consulta
                for(int k=0; k<doc; k++){
                    int Ranking = k+1;
                    int NumDoc = k+1;
                    String Score = "0.0";
                    if(controlador < numDoc){
                        for(int m = controlador; m<numDoc; m++){
                            String[] part = aux[m].split(" , ");
                            int ID = Integer.parseInt(part[0]);
                            if(NumDoc == ID){
                                //El NumDoc no hace falta cambiarlo
                                Score = part[1];//Cambiamos el Score
                                controlador++;
                            }
                        }
                    }
                        
                    //          NumConsulta+ " P1 " + NumDoc + Ranking + Score + " JDJ";
                    pw.println(NumConsulta + " P1 " + NumDoc + Ranking + Score + " JDJ");
                }
            }
                
            
            i++; //Miro la siguiente query
        }
        
        
        
        int contador = 0;   //indice para controlar cuando es inicio 
        boolean fin = false;//Llenamos de escribir
        int salid = 0;      //indice de la salida
        while(!fin){
            String NumConsulta = "";
            int Ranking = 1; 
            String Score = "";
            //Leemos las consultas
            boolean nuevo = false; //Nueva consulta
            while(!nuevo){
            

                //Escribimos
                //for(int i=0; i<doc; i++){
                    //Escribo
                    //          NumConsulta+ " P1 " + NumDoc+ Ranking + Score + " JDJ";
                    pw.println(NumConsulta + " P1 " + (i+1) + Ranking + Score + " JDJ");
                //}
            }
            
        }
    }
    
    private void CreoFichero(String path){
        Existe(path+"\\Trec_Eval\\trec_top_file.txt");//Si existe lo elimina
        //Creamos el fichero
        FileWriter fichero = null;
        pw = null;
        try {
            fichero = new FileWriter(path+"\\Trec_Eval\\trec_top_file.txt");
            pw = new PrintWriter(fichero);
            //Insertamos los datos
            /*for (int i = 0; i < 10; i++) {
                pw.println("Lo que sea");
            }*/
            
            
            
        } catch (IOException ex) {
            System.out.println("Error creando trec_top_file: ");
        }finally{
            if (null != fichero){
                try {
                    fichero.close();
                } catch (IOException ex) {
                    System.out.println("Cerrando el fichero");
                }
            }
              
        }
    }
    
    
    private void Existe(String fichero){
        System.out.println("Ruta: "+fichero);
        File fich = new File(fichero);
        if(fich.exists()){
            if(fich.delete()){
                System.out.println("Eliminado correctamente");
            }else{
                System.out.println("Existe pero no se ha eliminado");
            }
        }else{
            System.out.println("NO existe aun");
        }
    }
    
    
    public double Precision(){
        double a = 0.0;
        String pathAct =DirAct();
        CreoFichero(pathAct); //Creo el fichero en formato trec_Eval
        
        
        //Comparo con treceval
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+pathAct+"\\Trec_Eval\\treceval.exe trec_rel_file.txt trec_top_file.txt" );
            //Nos deberia devolver la efectividad y mas cosas
            
            //Actualizamos el valor de a
            //a = ;
        } catch (IOException ex) {
            System.out.println("Error abriendo conexion con Solr: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(20000);//20 segundos
        }catch(InterruptedException ex){
        }
        
        //Devolvemos la precision
        return a;
    }
    
    
    
    /**
     * Devuelve el directorio de trabajo
     * @return
     */
    public String DirAct(){
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
