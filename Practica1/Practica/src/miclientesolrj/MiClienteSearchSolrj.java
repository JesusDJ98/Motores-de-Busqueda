package miclientesolrj;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


import org.apache.solr.common.SolrInputDocument;


/**
 *
 * @author Jesus Delgado
 */
public class MiClienteSearchSolrj {

    public static void main(String[] args) throws IOException,
			SolrServerException {

        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/gettingstarted").build();
		
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
    }
}
