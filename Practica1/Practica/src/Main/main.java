/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import Extra.LeerQuerys;
import Interfaz.MiFrame;
import Interfaz.MiniInfoCore;
import Interfaz.PanelConsultas;
import Interfaz.PanelPractica;
import Interfaz.PanelPrincipal;
import miclientesolrj.MiClienteAddSolrj;
import miclientesolrj.MiClienteSearchSolrj;

/**
 *
 * @author Jesus Delgado
 */
public class main {
    public static void main(String args[]) {
        
        ConexionSolr conexion= new ConexionSolr();
        LeerCorpus corpus = new LeerCorpus();
        LeerQuerys querys = new LeerQuerys(0);
        
        MiniInfoCore infoCore = new MiniInfoCore();
        MiClienteAddSolrj clienteAdd = new MiClienteAddSolrj();
        MiClienteSearchSolrj clienteSearch = new MiClienteSearchSolrj(infoCore);
        
        PanelPrincipal principal = new PanelPrincipal(clienteSearch, conexion);
        PanelConsultas cambios = new PanelConsultas(clienteAdd, clienteSearch, conexion);
        PanelPractica practica = new PanelPractica(corpus, querys, clienteAdd, clienteSearch, conexion);
        
        MiFrame frame = new MiFrame(infoCore, principal, cambios, practica, conexion, clienteSearch);
        frame.setVisible(true); // */
        
        
        
        
        
        
        
        
        
        /*MYFrame frame = new MYFrame();
        frame.setVisible(true);//*/
        
        
        //Parte 1: Añadir al CORPUS
        /*
        LeerCorpus corpus = new LeerCorpus();
        String[] s = corpus.Listado();
        for(int i=0; i<s.length; i++){
            try {
                corpus.LeerFicheros(s[i]);
            } catch (FileNotFoundException ex) {
                System.out.println("No Correcto");
            }
        }
        miclientesolrj.MiClienteAddSolrj add = new MiClienteAddSolrj();
        add.AñadirCorpus(corpus, "micoleccion");
        //*/
        
        
        //Parte 2: Consultas
        /*
        miclientesolrj.MiClienteSearchSolrj a = new MiClienteSearchSolrj();
        System.out.println("UNO");
        LeerQuerys query = new LeerQuerys(1);
        query.Leer();
        String[] s = query.getQuerys();
        System.out.println("Salida:"+s.length);
        for(int i=0; i<s.length; i++){
            System.out.println(s[i]);
            try {
                Thread.sleep(2000);
                a.BusquedaQuery(query,"micoleccion");
                
                String[] sal=a.getSalida();
                if(sal!=null){
                    System.out.println("Salida: "+sal.length);
                    for(int j=0; j<sal.length; j++){
                        System.out.println(sal[j]);
                    }
                }else{
                    System.out.println("NADA");
                }
            } catch (SolrServerException | IOException ex) {
                System.out.println("Cagada");
            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //*/
        
        
        
        
        
    }
}
