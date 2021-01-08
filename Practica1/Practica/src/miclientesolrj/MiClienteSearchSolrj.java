package miclientesolrj;

import Extra.LeerQuerys;
import Extra.Tratamiento_Query;
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
    private Tratamiento_Query tratar;
    
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
        String titulo = "text_Titulo";
        String texto  = "text_Text";
        for(int j=0; j< consultas.length; j++){
            //Primero tratamos las consultas
            String s = "";
            s= consultas[j];
            tratar = new Tratamiento_Query(s);
            tratar.Limpiar();
            String words = tratar.PalabrasString();
            s = words;
            //System.out.println("MiniTrabajo: "+s);
            
            //Realizamos las consultas tratadas
            /*
            Quiero solo mirar el texto, pero que este contenga TODAS las palabras
            de la consulta
            */
            String Filtro ="id:"+s+" OR ("+texto+":"+s+" AND "+titulo+":"+s+")";
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");  //Todo
            query.setFilterQueries(Filtro); //El filtro
            SolrQuery.SortClause orden = new SolrQuery.SortClause("score", SolrQuery.ORDER.asc);  //De mayor a menor id
            query.setSort(orden);   //Orden de aparicion
            query.setRows(100000); //Cantidad que devuelve
            query.setFields("id", texto, titulo, "score");  //Los campos que queremos
            
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            
            salid.add("Consulta: "+(j+1)+" || '*:"+s+"'");
            salid.add("Documentos: "+docs.getNumFound());
            salid.add(" ");
            for (int i = 0; i < docs.getNumFound(); i++) {
                salid.add("Id: "+docs.get(i).getFieldValue("id"));
                salid.add("Title: "+docs.get(i).getFieldValue(titulo));
                salid.add("Text: "+docs.get(i).getFieldValue(texto));
                salid.add("Score: "+docs.get(i).getFieldValue("score"));
                salid.add(" ");
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
     * @param consulta
     * @param core
     * @throws IOException
     * @throws SolrServerException
     */
    public void RealizarQuery(String consulta, String core) throws IOException, SolrServerException{
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/"+core).build();
        
        String titulo = "text_Titulo";
        String texto  = "text_Text"; 
        //Tratamos la consulta
        tratar = new Tratamiento_Query(consulta);
        tratar.Limpiar();
        String words = tratar.PalabrasString();
        String s = words;
        //System.out.println("MiniTrabajo: "+s);
        
        String Filtro ="id:"+s+" OR ("+texto+":"+s+" AND "+titulo+":"+s+")";
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");  //Todo
        query.setFilterQueries(Filtro); //El filtro
        SolrQuery.SortClause orden = new SolrQuery.SortClause("id", SolrQuery.ORDER.desc);  //De mayor a menor id
        query.setSort(orden);   //Orden de aparicion
        query.setRows(100000); //Cantidad que devuelve
        query.setFields("id", texto, titulo, "score");  //Los campos que queremos

        QueryResponse rsp = solr.query(query);  //Realizar consulta
        SolrDocumentList docs = rsp.getResults();
        
        ArrayList<String> salid = new ArrayList<>();
        salid.add("Consulta: '*:"+consulta+"'");
        salid.add("Documentos: "+docs.getNumFound());
        salid.add(" ");
        salid.add(" ");
        for (int i = 0; i < docs.getNumFound(); i++) {
            salid.add("Id: "+docs.get(i).getFieldValue("id"));
            salid.add("Title: "+docs.get(i).getFieldValue(texto));
            salid.add("Text: "+docs.get(i).getFieldValue(titulo));
            salid.add("Score: "+docs.get(i).getFieldValue("score"));
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
        PanelInfo.ActualizarMinInf(core, s);
    }

}
