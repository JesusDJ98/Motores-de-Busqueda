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
    
    public Trec_Eval(/* Los datos */){
        //Le introduzco los datos
    }
    
    private void TratamientoDatos(){
        /*Tendremos que tratar los datos
        Pues solo voy a necesitar:
                el numero de la consulta
                el id de cada respuesta
                el score de cada respuesta
        */
        
        //Formato: NºConsulta, 0, idDoc, 0/1 -->Si es devuelto o no
        
        /*
        Lo que veo mejor
        Coger solo los id devueltos de cada consulta
        Ordenarlos
        Mientrs i sea menor q x[0] poner 0
        cuando sea igual añadimos 1 y eliminamos s[0]
        asi hasta que no quede ningun numero devuelto 
        y seguimos pponiendo 0 sin comparar hasta 6004 poniendo 0
        */
    }
    
    public void CreoFichero(String path){
        Existe(path+"\\trec_top_file.txt");//Si existe lo elimina
        //Creamos el fichero
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(path+"\\trec_top_file.txt");
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
