/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import Extra.LeerQuerys;
import Extra.Trec_Eval;
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
        
        PanelPrincipal principal = new PanelPrincipal(clienteSearch, conexion, infoCore);
        PanelConsultas cambios = new PanelConsultas(clienteAdd, clienteSearch, conexion, infoCore);
        PanelPractica practica = new PanelPractica(corpus, querys, clienteAdd, clienteSearch, conexion, infoCore);
        
        MiFrame frame = new MiFrame(infoCore, principal, cambios, practica, conexion, clienteSearch);
        frame.setVisible(true); // */
        
        
        //Porbar Trec eval
        /*Trec_Eval trec = new Trec_Eval();
        String s = trec.DirAct();
        trec.CreoFichero(s+"\\Trec_Eval");
        //*/
        
    }
}
