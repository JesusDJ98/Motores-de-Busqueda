package Extra;

import java.io.IOException;


/**
 *
 * @author Jesus Delgado
 */
public class ConexionSolr {
    
    private boolean conectado;
    
    public ConexionSolr(){
        conectado=false;
    }
    
    public void Conexion(){
        
        //Abro conexion con SOLR
        try {
            Runtime r = Runtime.getRuntime();
            Process exec = r.exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr.cmd start");
        } catch (IOException ex) {
            System.out.println("Error abriendo conexion con Solr: "+ex);
        }
        conectado=true;
        
    }
    
    public void NewColeccion(String name){
        //Creo el nuevo core
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr.cmd create -c "+name);
        } catch (IOException ex) {
            System.out.println("Error creando CORE: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(5000);//5 segundos
        }catch(InterruptedException ex){
        }
        //Comando hay campos nulos (text_Text suele añadirse mal)
        //-fieldName:['' TO *]
        
        String url = "http://localhost:8983/solr/#/"+name+"/core-overview";
        
        //Abro la url en el navegador
        //goUrl(url);
    }
    
    /**
     * Método para eliminar una coleccion de SOLR
     * @param name
     */
    public void DeleteColeccion(String name){
        //Creo el nuevo core
        try {
            Runtime r = Runtime.getRuntime();
            r.exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr.cmd delete -c "+name);
        } catch (IOException ex) {
            System.out.println("Error eliminando CORE: "+ex);
        }
        //Damos un margen
        try{
            Thread.sleep(5000);//5 segundos
        }catch(InterruptedException ex){
        }
    }
    
    /**
     * Cerramos la conexion con SOLR
     */
    public void CerrarConexion(){
        //Abro conexion con SOLR
        try {
            Runtime.getRuntime().exec("cmd /C cd "+DirAct()+"\\solr-8.6.3"+ " && bin\\solr stop -all");
        } catch (IOException ex) {
            System.out.println("Error cerrando conexion con Solr: "+ex);
        }
        conectado=false;
        //Damos un margen
        try{
            Thread.sleep(5000);
        }catch(InterruptedException ex){
        }
        
    }
    
    /**
     * Coge el directorio actual de trabajo
     * @return
     */
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
    
    /**
     * Funcion que abre el navegador y va a la url
     * pasada por parametro
     * @param url
     */
    private void goUrl(String url){
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
     * Devoolvemos el estado de Solr
     * @return
     */
    public boolean estado(){
        return conectado;
    }
}
