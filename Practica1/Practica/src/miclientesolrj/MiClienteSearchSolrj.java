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
            //System.out.println(consultas[j]);
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
            
            salid.add("Consulta: "+query.getQuery());
            salid.add("Documentos: "+docs.getNumFound());
            salid.add(" ");
            //Mostramos el resultado
            for (int i = 0; i < docs.size(); ++i) {
                salid.add(docs.get(i).toString());
                //System.out.println(docs.get(i));
            }
            //Separacion
            salid.add(" ");
            salid.add("-----------------------------------");
            /*System.out.println(" ");
            System.out.println("-----------------------------------");*/
        }
        /*System.out.println("Cantidad de consultas: "+salid.size());
        System.out.println(" ");*/
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
        //System.out.println("He creado el cliente");
        SolrQuery query = new SolrQuery();
        query.setQuery(campo+":"+texto);
        //System.out.println("Numero de querys: "+query.getRows()); //Esto da null
        //System.out.println("Numero de querys: "+query.getTermsLimit());
        query.setRows(100000); //Un numero muy grande
        //System.out.println("Numero de querys modificado: "+query.getTermsLimit());
        //System.out.println("Numero de querys: "+query.getRows());
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        //System.out.println("Salida: "+docs.getNumFound());
        documentos=(int)docs.getNumFound();
        //documentos =docs.size();
        //Mostramos los documentos
        /*for (int i = 0; i < docs.size(); ++i) { //Lo utilizo ara contabilizar los archivos
            //System.out.println(docs.get(i));
        }*/
        
    }
    
    public int getNumero(){
        return documentos;
    }
    
    public String[] getSalida(){
        return salida;
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
