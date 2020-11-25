
package miclientesolrj;


import Extra.LeerCorpus;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
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
        
        JOptionPane.showMessageDialog(null, "Añadido correctamente");
        
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
        
        solr.deleteByQuery("id:"+id);
        UpdateResponse commit = solr.commit();
        
        if(commit.getQTime() > 20){ //Si no existe suele ser 3, si existe supera los 100
            JOptionPane.showMessageDialog(null, "Eliminado correctamente");
        }else{
            JOptionPane.showMessageDialog(null, "No existe ningun documente con dicho id: "+id);
        }
        
    }
}
