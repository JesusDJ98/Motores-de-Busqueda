
package miclientesolrj;


import Extra.LeerCorpus;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
/**
 *
 * @author Jesus Delgado
 */
public class MiClienteAddSolrj {
    
    /**
     * Añadimos todos los documentos de LISA a SOLR
     * @param corpus
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void AñadirCorpus(LeerCorpus corpus, String core) throws SolrServerException, IOException{
        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        //Objetos que usare
        int[] IdDoc;
        String[] Titulo;
        String[] Texto;
        int tama;
        
        //Leo los archivos
        tama = corpus.getTama();
        IdDoc = corpus.getId();
        Titulo = corpus.getTitle();
        Texto = corpus.getText();
        
        //Creo los documentos SOLR
        for(int i=0; i<tama; i++){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", IdDoc[i]);
            doc.addField("title", Titulo[i]);
            doc.addField("text", Texto[i]);
            solr.add(doc);
            solr.commit();
        }
    }
    
    /**
     * Añadimos las clausulas nuevas
     * @param id
     * @param title
     * @param text
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void Añadir(String id, String title, String text, String core) throws SolrServerException, IOException{
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        
        //Creo los documentos SOLR
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField("title", title);
        doc.addField("text", text);
        solr.add(doc);
        solr.commit();
        
    }
    
    /**
     * Eliminamos la clausula solicitada
     * @param id
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void Eliminar(String id, String core) throws SolrServerException, IOException{
        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        
        solr.deleteById(id);
        solr.commit();
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) throws IOException, SolrServerException{
        //Objetos que usare
        /*int[] IdDoc;
        String[] Titulo;
        String[] Texto;
        int tama;
        
        //Abrimos la conexion con SOLR
        try{
            ConexionSolr conex = new ConexionSolr();
            conex.Conexion();
            //conex.CerrarConexion();
        }catch(Exception ex){
            System.out.println("Error: "+ex);
        }
        
        //Leo los archivos
        LeerCorpus corpus = new LeerCorpus();
        corpus.LeerFicheros();
        
        
        //Creo el cliente
        //HttpSolrClient solr = new HttpSolrClient.Builder(url+"/gettingstarted").build();
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/micoleccion").build();
        
        
        //Leo los archivos
        tama = corpus.getTama();
        IdDoc = corpus.getId();
        Titulo = corpus.getTitle();
        Texto = corpus.getText();
        
        //Creo los documentos SOLR
        for(int i=0; i<tama; i++){
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", IdDoc[i]);
            doc.addField("title", Titulo[i]);
            doc.addField("text", Texto[i]);
            solr.add(doc);
            solr.commit();
        }
        //
    }*/
    
}
