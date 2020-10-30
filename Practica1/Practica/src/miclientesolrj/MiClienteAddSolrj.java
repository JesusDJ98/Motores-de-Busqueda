
package miclientesolrj;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
/**
 *
 * @author Jesus Delgado
 */
public class MiClienteAddSolrj {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SolrServerException{
        //Objetos que usare
        /*int[] IdDoc;
        String[] Titulo;
        String[] Texto;
        int tama;
        
        //Abrimos la conexion con SOLR
        try{
            ConexionSolr conex = new ConexionSolr();
            //conex.Conexion();
            conex.CerrarConexion();
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
        //*/
    }
    
}
