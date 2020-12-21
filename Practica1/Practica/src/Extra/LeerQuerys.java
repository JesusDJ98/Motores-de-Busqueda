package Extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class LeerQuerys {

    private String[] querys;
    private int cantidad;
    
    /**
     * Constructor
     * @param cantidad
     */
    public LeerQuerys(int cantidad) {
        this.cantidad=cantidad;
        querys = new String[cantidad];
    }
    
    /**
     * Leer el archivo de Querys
     */
    public void Leer(){
        String actual = DirAct();
        String carpeta = "\\Coleccion LISA revisada";
        String archivo = actual+carpeta+"\\LISA.QUE";
        
        File file = new File(archivo);
        try {
            Scanner sc = new Scanner(file);
            
            //Leemos
            int i = 0;
            String consulta = "";
            int pos = 0;
            while(sc.hasNextLine() && pos<this.cantidad){
                String s = sc.nextLine();
                
                if(i > 0){
                    char charAt = s.charAt(s.length()-1);
                    if(charAt=='#'){                //Terminamos
                        String aux="";  //La ultima frase
                        for(int j=0; j<s.length()-1; j++){
                            aux += s.charAt(j);
                        }
                        querys[pos]=consulta+aux;   //Guardamos
                        pos++;                      //Incrementamos posicion
                        consulta="";                //Reseteamos
                        i=-1;                       //mas el 1 seria igual a 0
                    }else{
                        consulta += s + " ";        //Añadimos
                    }
                }else{//Aqui estamos leyendo el numero, que no nos intersa
                }
                i++;                                //Incrementamos
            }
            //System.out.println("Consulta: "+consulta); //Aqui no me muestra nada...
            System.out.println("Query: "+this.querys[0]);
        } catch (FileNotFoundException ex) {
            System.out.println("Error leyendo Querys: "+ex);
        }
    }
    
    /**
     * Devolvemos las consultas
     * @return
     */
    public String[] getQuerys(){
        
        String[] aux = new String[this.cantidad];
        for (int i = 0; i < this.cantidad; i++) {
            aux[i]=this.querys[i];
        }
        return aux;
    }
    
    /**
     * Modificamos la cantidad de consultas
     * @param cant
     */
    public void setCant(int cant){
        this.cantidad=cant;
        this.querys = new String[cant];
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
