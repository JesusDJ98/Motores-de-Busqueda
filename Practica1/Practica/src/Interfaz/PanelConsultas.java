/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import Extra.LeerQuerys;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import miclientesolrj.MiClienteAddSolrj;
import miclientesolrj.MiClienteSearchSolrj;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author Jesus Delgado
 */
public class PanelConsultas extends JPanel{
    
    ConexionSolr solr;
    LeerCorpus lisa;
    LeerQuerys query;
    MiClienteSearchSolrj consultas;
    MiClienteAddSolrj clienteAdd;
    boolean permitido;
    String coleccion;
    
    JLabel core;
    JLabel NDoc;
    JButton Añadir;
    JButton Eliminar;
    //JScrollPane SalidaPanel;
    JPanel ConsultaPanel;
    JTextField id;
    JTextField title;
    JTextField text;
    
    public PanelConsultas(boolean p, ConexionSolr s){
        //super();
        setLayout(null);
        setBounds(150, 10, 430, 400);
        setBackground(Color.red);
        
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
            
        
        Añadir = new JButton();
        Añadir.setBounds(80, 100, 200, 30);
        //Corpus.setEnabled(false);
        Añadir.setText("Añadir");
        Añadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(permitido){
                    //System.out.println("Hola");
                    Añadir();
                    ModificacionJLabel(); //Actualizamos la cantidad de documentos
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        Eliminar = new JButton();
        Eliminar.setBounds(80, 150, 200, 30);
        //Query.setEnabled(false);
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(permitido){
                    Eliminar();
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        ConsultaPanel = new JPanel();
        ConsultaPanel.setLayout(null);
        ConsultaPanel.setBounds(20, 200, 380, 150);
        
        
        JLabel namid = new JLabel("Id: ");
        namid.setBounds(20, 20, 60, 30);
        id=new JTextField();
        id.setBounds(100, 20, 200, 30);
        
        JLabel namtitle = new JLabel("Tilte: ");
        namtitle.setBounds(20, 60, 60, 30);
        title=new JTextField();
        title.setBounds(100, 60, 200, 30);
        
        JLabel namtext = new JLabel("Text: ");
        namtext.setBounds(20, 100, 60, 30);
        text=new JTextField();
        text.setBounds(100, 100, 200, 30);
       
        ConsultaPanel.add(namid);
        ConsultaPanel.add(id);
        ConsultaPanel.add(namtitle);
        ConsultaPanel.add(title);
        ConsultaPanel.add(namtext);
        ConsultaPanel.add(text);
        
        add(core);
        add(NDoc);
        add(Añadir);
        add(Eliminar);
        add(ConsultaPanel);
        
        Inicio();
    }
    
    /**
     * Nos movemos a dicha coleccion (micoleccion)
     * y vemos los documentos que hay ya introducidos
     */
    private void Inicio(){
        coleccion = "micoleccion"; //Predeterminado
        ModificacionJLabel();
    }
    
    /**
     * Añado los datos
     */
    private void Añadir(){
        clienteAdd = new MiClienteAddSolrj();
        try{
            if((id.getText()!="") && (title.getText()!="") && (text.getText()!="")){
                clienteAdd.Añadir(id.getText(), title.getText(), text.getText(), coleccion);
                LimpiarField();
                JOptionPane.showMessageDialog(null, "Añadido correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Rellena todos los datos");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error añadiendo: "+ex);
        }
        //Actualizo dsps de esperar 2 segundos
        try{
            Thread.sleep(2000);
        }catch(Exception ex){
        }
        Actualizar(permitido, coleccion);
    }
    
    /**
     * Elimino los datos
     */
    private void Eliminar(){
        clienteAdd = new MiClienteAddSolrj();
        try{
            if(id.getText()!=""){
                clienteAdd.Eliminar(id.getText(), coleccion);
                LimpiarField();
                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Rellena el id");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error eliminando: "+ex);
        }
        //Actualizo dsps de esperar 2 segundos
        try{
            Thread.sleep(2000);
        }catch(Exception ex){
        }
        Actualizar(permitido, coleccion);
    }
    
    /**
     * Modificamos los Label de Info
     */
    private void ModificacionJLabel(){
        if(permitido){
            //coleccion = "micoleccion";

            //Vemos cuantos documentos tiene
            consultas = new MiClienteSearchSolrj();
            int numero =0;
            try{
                consultas.Busqueda(".", ".", coleccion);
                numero = consultas.getNumero();
            }catch(Exception ex){
                System.out.println("Error: "+ex);
            }

            setInfo(coleccion, numero);
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
     * @param n
     */
    public void Actualizar(boolean p, String n){
        permitido=p;
        coleccion = n;
        try{
            Thread.sleep(5000);//5 segundos
        }catch(Exception ex){
        }
        Inicio();
    }
    
    /**
     * Limpio los fields
     */
    private void LimpiarField(){
        id.setText("");
        title.setText("");
        text.setText("");
    }
    
}
