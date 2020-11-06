/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Extra.*;
import miclientesolrj.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author Jesus Delgado
 */
public class PanelPractica extends JPanel{
    
    ConexionSolr solr;
    LeerCorpus lisa;
    LeerQuerys query;
    MiClienteSearchSolrj consultas;
    MiClienteAddSolrj clientaAdd;
    boolean permitido;
    
    JLabel core;
    JLabel NDoc;
    JButton Corpus;
    JButton Query;
    //JScrollPane SalidaPanel;
    JList<String> SalidaPanel;
    
    JLabel accion;
    
    public PanelPractica(boolean p, ConexionSolr s){
        //super();
        setLayout(null);
        setBounds(150, 10, 430, 400);
        setBackground(Color.blue);
        
        solr = s;
        permitido=p;
        Init();
    }
    
    /**
     * Añadimos todos los elementos del panel
     * @param p
     * @param s
     */
    private void Init(){
        
        core = new JLabel("Core: ");
        NDoc = new JLabel("Nº Doc: ");
        core.setBounds(300, 30, 100, 20);
        NDoc.setBounds(300, 50, 100, 20);
            
        
        Corpus = new JButton();
        Corpus.setBounds(80, 100, 200, 30);
        //Corpus.setEnabled(false);
        Corpus.setText("Añadir Corpus");
        Corpus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Entramos: "+permitido);
                if(permitido){
                    //System.out.println("Hola");
                    Corpus_SOLR();
                    ModificacionJLabel(); //Actualizamos la cantidad de documentos
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        Query = new JButton();
        Query.setBounds(80, 150, 200, 30);
        //Query.setEnabled(false);
        Query.setText("Realizar Querys");
        Query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(permitido){
                    Query_SOLR();
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        /*SalidaPanel = new JScrollPane();
        SalidaPanel.setLayout(null);
        SalidaPanel.setBounds(20, 200, 380, 150);*/
        SalidaPanel = new JList<>();
        SalidaPanel.setLayout(null);
        SalidaPanel.setBounds(20, 200, 380, 150);
        
        
        accion = new JLabel("Accion: ");
        accion.setBounds(10, 370, 300, 30);
       
        add(core);
        add(NDoc);
        add(Corpus);
        add(Query);
        add(SalidaPanel);
        add(accion);
        
        Inicio();
    }
    
    /**
     * Nos movemos a dicha coleccion (micoleccion)
     * y vemos los documentos que hay ya introducidos
     */
    private void Inicio(){
        ModificacionJLabel();
    }
    
    /**
     * Modificamos los Label de Info
     */
    private void ModificacionJLabel(){
        if(permitido){
            //Nos aseguramos de que existe dicho core (micoleccion)
            solr.NewColeccion("micoleccion");

            //Vemos cuantos documentos tiene
            consultas = new MiClienteSearchSolrj();
            int numero =0;
            try{
                consultas.Busqueda("*", "*", "micoleccion");
                numero = consultas.getNumero();
            }catch(Exception ex){
                System.out.println("Error en JLabel");
                System.out.println("Error: "+ex);
            }

            setInfo("micoleccion", numero);
        }
    }
    
    /**
     * Leemos el corpus y lo añadimos a SOLR
     */
    private void Corpus_SOLR(){
        accion.setText("Accion: Leyendo el CORPUS");
        //Leo el Corpus
        lisa = new LeerCorpus();
        String[] listado = lisa.Listado();
        clientaAdd = new MiClienteAddSolrj();
        for(int i = 0; i<listado.length; i++){
            System.out.println("File: "+listado[i]);
            accion.setText("Accion: Leyendo "+listado[i]);
            try {
                lisa.LeerFicheros(listado[i]);
            } catch (FileNotFoundException ex) {
                accion.setText("Accion: Error leyendo "+listado[i]);
            }
        }
        accion.setText("Accion: Añadiendo el Corpus a SOLR");
        try {
            //espero
            Thread.sleep(5000);
            //Lo añado a SOLR
            clientaAdd.AñadirCorpus(lisa, "micoleccion");
        } catch (SolrServerException | IOException | InterruptedException ex) {
            accion.setText("Accion: Error añadiendo el Corpus a SOLR");
        }
        
        //Actualizamos y esperamos
        try{
            Thread.sleep(2000);
        }catch(Exception ex){
        }
        Actualizar(permitido);
    }
    
    /**
     * Leemos las Querys predeterminadas Y las buscamos en SOLR
     */
    private void Query_SOLR(){
        accion.setText("Accion: Leyendo Fichero de Querys");
        boolean correcto = false;
        int a = 0;
        while(!correcto){
            String s = JOptionPane.showInputDialog("Cuantas consultas desea hacer? (max 35)");
            try{
                a = Integer.parseInt(s);
                if(a>0){
                    if(a>35){
                        a = 35;
                    }
                    correcto = true;
                }else{
                    JOptionPane.showMessageDialog(null, "Cantidad no aceptada");
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Deben ser numeros");
            }
        }
        
        query = new LeerQuerys(a);
        query.Leer();
        //String[] ListaQue = query.getQuerys();
        
        accion.setText("Accion: Realizando Querys");
        try {
            consultas.BusquedaQuery(query, "micoleccion");
            String[] s = consultas.getSalida();
            if(s!=null){
                SalidaPanel.setListData(s);
            }else{
                System.out.println("NULL");
            }
        } catch (SolrServerException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta");
        }
    }
    
    /**
     * Cambiamos los nombres de los JLabel
     * @param c
     * @param num
     */
    private void setInfo(String c, int num){
        core.setText("Core: "+c);
        NDoc.setText("Nº Doc: "+num);
    }
    
     /**
     * Actualizamos segun este conectado o no
     * @param p
     */
    public void Actualizar(boolean p){
        permitido=p;
        try{
            Thread.sleep(5000);//5 segundos
        }catch(Exception ex){
        }
        Inicio();
    }
    
}
