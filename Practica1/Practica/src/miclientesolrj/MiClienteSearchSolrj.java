package miclientesolrj;

import Extra.LeerQuerys;
import Interfaz.MiniInfoCore;
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
    
    private int documentos;
    private String[] salida;
    private String[] salidaBuscador;
    private MiniInfoCore PanelInfo;
    
    public MiClienteSearchSolrj(MiniInfoCore infoC){
        PanelInfo = infoC;
        documentos=0;
        salida = new String[documentos];
    }
    
    /**
     * Hacemos todas las consultas del archivo LISA.QUE
     * @param QUE
     * @param core
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    public void BusquedaQuery(LeerQuerys QUE, String core) throws SolrServerException, IOException{
        
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
            query.setQuery("id:"+s+" title:"+s+" text:"+s);
            query.setRows(100000);//Numero grande 10k
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            
            salid.add("Consulta: "+(j+1)+" || '*:"+s+"'");
            salid.add("Documentos: "+docs.getNumFound());
            salid.add(" ");
            for (int i = 0; i < docs.getNumFound(); i++) {
                salid.add(" ");
                salid.add("id: "+docs.get(i).getFieldValue("id"));
                salid.add("title: "+docs.get(i).getFieldValue("title"));
                salid.add("text: "+docs.get(i).getFieldValue("text"));
            }
            //Separacion de consultas
            salid.add("------------------------");
        }
        salida = new String[salid.size()];
        for (int i = 0; i < salid.size(); i++) {
            salida[i]=salid.get(i);
        }
            
    }
    
    /**
     * Realizamos el *:* para ver el numero de documentos
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void Busqueda(String core) throws SolrServerException, IOException{
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setRows(100000); //Un numero muy grande
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        documentos=(int)docs.getNumFound();
    }
    
    /**
     * Realizamos las QUERY
     * @param texto
     * @param core
     * @throws IOException
     * @throws SolrServerException
     */
    public void RealizarQuery(String texto, String core) throws IOException, SolrServerException{
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        SolrQuery query = new SolrQuery();
        query.setQuery("id:"+texto+" title:"+texto+" text:"+texto);
        query.setRows(100000); //Un numero muy grande
        QueryResponse rsp = solr.query(query);
        SolrDocumentList docs = rsp.getResults();
        
        ArrayList<String> salid = new ArrayList<>();
        salid.add("Consulta: '*:"+texto+"'");
        salid.add("Documentos: "+docs.getNumFound());
        salid.add(" ");
        salid.add(" ");
        for (int i = 0; i < docs.getNumFound(); i++) {
            salid.add("id: "+docs.get(i).getFieldValue("id"));
            salid.add("title: "+docs.get(i).getFieldValue("title"));
            salid.add("text: "+docs.get(i).getFieldValue("text"));
            salid.add(" ");
            salid.add("------------------------");
        }
        
        documentos=(int)docs.getNumFound();
        
        salidaBuscador = new String[salid.size()];
        for (int i = 0; i < salid.size(); i++) {
            salidaBuscador[i]=salid.get(i);
        }
    }
    
    
    /**
     * Devolvemos la salida obtenida del CORPUS
     * @return
     */
    public String[] getSalida(){
        return salida;
    }
    
    /**
     * Devolvemos la salida obtenida del buscador
     * @return
     */
    public String[] getSalidaB(){
        return salidaBuscador;
    }
    
    /**
     * Metodo que utilizaremos para tener actualizado el panel
     * de informacion sobre el core.Debe coger el core del otro menu del nombre del core.
     *
     * @param core
     */
    public void ActualizarMiniInfo(String core){
        try {
            Busqueda(core);
            Thread.sleep(3000);
        } catch (SolrServerException | IOException | InterruptedException ex) {
        }
        String s = ""+documentos;
        PanelInfo.ActualizarMinInf("micoleccion", s);
    }

}
