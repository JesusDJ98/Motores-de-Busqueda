package Extra;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
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
    private FileWriter fichero;
    
    
    public Trec_Eval(int documentos, int consultas/* Los datos */){
        //Le introduzco los datos
        doc=documentos;
        query=consultas;
    }
    
    public void TratamientoDatos(String[] salida){
        String s = DirAct();
        //CreoFichero(s);
        AbroFich(s);
        
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
                if(salida[pos].equals("------------------------")){
                    pos2=0; //Reinicio
                    pos++;
                    nuevo = true; //Sale
                }else{
                    if(pos2 == 0){
                        String[] partes = salida[pos].split(": ");
                        String[] partes2 = partes[1].split(" ");
                        query = partes2[0];
                    }else if(pos2 == 1){
                        String[] partes = salida[pos].split(": ");//0: Documentos: -1:num
                        numDoc = Integer.parseInt(partes[1]);
                    }else if(pos2>2){
                        aux = new String[numDoc];
                        int incremento = 5;
                        for (int j = 0; j < numDoc; j++) {
                            String[] partes = salida[pos].split(": ");  //0:id: 1:numId
                            String[] partes2 = salida[pos+3].split(": ");//0:score: 1: numScore
                            aux[j] = partes[1] + " , " +partes2[1];

                            pos+=incremento;
                            //pos2+=incremento; //No necesario
                        }pos--;
                    }
                    pos2++; //Solo me importa el 0 1 y cualquier otro
                    pos++;
                }
                
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
                    int Ranking = 0;
                    int NumDoc = k+1;
                    String Score = "0.0";
                    if(controlador < numDoc){
                        int m = controlador;
                        boolean encontrado = false;
                        while(!encontrado && m<numDoc){
                            String[] part = aux[m].split(" , ");
                            int ID = Integer.parseInt(part[0]);
                            if(NumDoc == ID){
                                //El NumDoc no hace falta cambiarlo
                                Ranking = (m+1);    //Cambiamos el ranking
                                Score = part[1];//Cambiamos el Score
                                controlador++;
                                encontrado=true;
                            }
                            m++;
                        }
                    }
                    //System.out.println(NumConsulta + " P1 " + NumDoc + " " + Ranking + " " + Score + " JDJ");    
                    //          NumConsulta+ " P1 " + NumDoc + Ranking + Score + " JDJ";
                    pw.println(NumConsulta + " P1 " + NumDoc + " " + Ranking + " " + Score + " JDJ");
                }
            i++; //Miro la siguiente query
        }
        CierroFich();
    }
    
    private void AbroFich(String path){
        Existe(path+"\\Trec_Eval\\trec_top_file.txt");//Si existe lo elimina
        //Creamos el fichero
        fichero = null;
        pw = null;
        try {
            fichero = new FileWriter(path+"\\Trec_Eval\\trec_top_file.txt");
            pw = new PrintWriter(fichero);
        } catch (IOException ex) {
            System.out.println("Error creando trec_top_file: ");
        }
    }
    
    private void CierroFich(){
        if (null != fichero){
            try {
                fichero.close();
            } catch (IOException ex) {
                System.out.println("Cerrando el fichero");
            }
        }
    }
    
    /**
     * Comprueba la existencia del fichero elegido
     * y lo elimina si existe
     * @param fichero
     */
    private void Existe(String fichero){
        File fich = new File(fichero);
        if(fich.exists()){
            if(fich.delete()){
            }
        }
    }
    
    
    public float Precision(){
        float a = 0.0f;
        String pathAct =DirAct();
        
        Existe(pathAct+"\\Trec_Eval\\Solucion.txt");
        //Comparo con treceval
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+pathAct+"\\Trec_Eval && treceval trec_rel_file.txt trec_top_file.txt >> Solucion.txt" );
            // [-q] genera un porcentaje por cada query
            // [-a] npi
            
            //Con este bucle evito realizar una espera
            //Leemos el fichero generado
            boolean encontrado = false;
            while(!encontrado){
                try{
                    Scanner sc = new Scanner(new File(pathAct+"\\Trec_Eval\\Solucion.txt"));
                    encontrado=true;
                    String aux = "";
                    while(sc.hasNextLine()){
                        aux = sc.nextLine();//Solo nos interesa la ultima posicion
                    }
                    String[] partes = aux.split(" ");
                    //Actualizamos el valor de a
                    a = Float.parseFloat(partes[partes.length-1]);
                }catch(Exception ex){
                }
            }
        } catch (IOException ex) {
            System.out.println("Error Precision: "+ex);
        }
            
        
        //Devolvemos la precision
        return a;
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
