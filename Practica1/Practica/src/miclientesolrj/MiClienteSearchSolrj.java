package miclientesolrj;

import Extra.LeerQuerys;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;



/**
 *
 * @author Jesus Delgado
 */
public class MiClienteSearchSolrj {
    
    /**
     * Hacemos todas las consultas del archivo LISA.QUE
     * @param QUE
     * @param core
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    public void BusquedaQuery(LeerQuerys QUE, String core) throws SolrServerException, IOException{
        //QUE.Leer();
        String[] consultas = QUE.getQuerys();
        
        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        //Recorremos la consulta
        for(int j=0; j< consultas.length; j++){
            System.out.println(consultas[j]);
            SolrQuery query = new SolrQuery();
            query.setQuery("*:"+consultas[j]);
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            //Mostramos el resultado
            for (int i = 0; i < docs.size(); ++i) {
                System.out.println(docs.get(i));
            }
            //Separacion
            System.out.println(" ");
            System.out.println("-----------------------------------");
        }
            
    }
    
    /**
     * Buscamos una busqueda especifica
     * @param campo
     * @param texto
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void Busqueda(String campo, String texto, String core) throws SolrServerException, IOException{
        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        
        SolrQuery query = new SolrQuery();
        query.setQuery(campo+":"+texto);
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        //Mostramos los documentos
        for (int i = 0; i < docs.size(); ++i) {
            System.out.println(docs.get(i));
        }
    }

    /*public static void main(String[] args) throws IOException,
			SolrServerException {
        
        /*LeerQuerys query = new LeerQuerys(35);
        query.Leer();
        String[] consultas = query.getQuerys();
        
        System.out.println(" ");
        System.out.println(" ");
        for(int i =0; i< consultas.length; i++){
            System.out.println(consultas[i]);
            System.out.println(" ");
        }*/
        
        /*
        Me muestra solo 0 pues es muy largo
        Para ello vamos a reducirla un poco, eliminando en primera instancia las palabras vacias(STOP WORDS)
        Desde SOLR se puede hacer
        */
        
        
        /*HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/gettingstarted").build();
		
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //query.setQuery("Apple");
        query.addFilterQuery("cat:electronics");
        //query.setFields("id","price","merchant","cat","store");
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        for (int i = 0; i < docs.size(); ++i) {
            System.out.println(docs.get(i));
        }
    }*/
}
