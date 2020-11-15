package miclientesolrj;

import Extra.LeerQuerys;
import java.io.IOException;
import java.util.ArrayList;
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
    
    private int documentos=0;
    private String[] salida;
    
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
        //Vemos el tamaño de lineas que necesitammos mientras hacemos las consultas
        ArrayList<String> salid = new ArrayList<>();
        for(int j=0; j< consultas.length; j++){
            SolrQuery query = new SolrQuery();
            //Coger solo 5 pos
            String s="";
            String[] aux=consultas[j].split(" "); //Separamos
            for(int i=0; i<5; i++){//Cogemos 5 palabras
                s+=aux[i];
                if(i<4){
                    s+=" ";
                }
            }
            query.setQuery("text:"+s);
            //query.setQuery("*:"+consultas[j]);
            /*query.setQuery("id"+":"+s);
            query.setQuery("title"+":"+s);
            query.setQuery("text"+":"+s);*/
            //System.out.println("Consulta: "+query.getQuery());
            query.setRows(100000);//Numero grande 10k
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            
            salid.add("Consulta: "+(j+1)+" || "+query.getQuery());
            salid.add("Documentos: "+docs.getNumFound());
            salid.add(" ");
            //Mostramos el resultado
            for (int i = 0; i < docs.size(); ++i) {
                salid.add(docs.get(i).toString());
            }
            //Separacion
            salid.add(" ");
            salid.add("-----------------------------------");
        }
        salida = new String[salid.size()];
        for (int i = 0; i < salid.size(); i++) {
            salida[i]=salid.get(i);
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
        query.setRows(100000); //Un numero muy grande
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        documentos=(int)docs.getNumFound();
    }
    
    /**
     * Devolvemos el numero de documentos
     * @return
     */
    public int getNumero(){
        return documentos;
    }
    
    /**
     * Devolvemos la salida obtenida
     * @return
     */
    public String[] getSalida(){
        return salida;
    }

}
