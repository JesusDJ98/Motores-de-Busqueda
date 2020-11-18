/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Extra.ConexionSolr;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import miclientesolrj.MiClienteAddSolrj;
import miclientesolrj.MiClienteSearchSolrj;

/**
 *
 * @author Jesus Delgado
 */
public class PanelConsultas extends JPanel{
    
    private ConexionSolr solr;
    private MiClienteSearchSolrj consultas;
    private MiClienteAddSolrj clienteAdd;
    private String coleccion; //Debo hacer que esto lo coja del core
    
    private JButton Añadir;
    private JButton Eliminar;
    //JScrollPane SalidaPanel;
    private JPanel ConsultaPanel;
    private JTextField id;
    private JTextField title;
    private JTextField text;
    
    MiniInfoCore info;
    
    public PanelConsultas(MiClienteAddSolrj add, MiClienteSearchSolrj search, ConexionSolr s, MiniInfoCore f){
        //super();
        setLayout(null);
        setBounds(150, 100, 430, 350);
        setBackground(Color.white);
        
        Init(add, search, s, f);
    }
    
    /**
     * Añadimos todos los elementos del panel
     * @param p
     * @param s
     */
    private void Init(MiClienteAddSolrj add, MiClienteSearchSolrj search, ConexionSolr s, MiniInfoCore f){
        
        solr = s;
        clienteAdd = add;
        consultas = search;
        info=f;
        
        
        Añadir = new JButton();
        Añadir.setBounds(100, 200, 200, 30);
        //Corpus.setEnabled(false);
        Añadir.setText("Añadir");
        Añadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean permitido = solr.estado();
                if(permitido){
                    System.out.println("Hola boton añadir, estas conectado");
                    //Añadir();
                    //ModificacionJLabel(); //Actualizamos la cantidad de documentos
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        Eliminar = new JButton();
        Eliminar.setBounds(100, 250, 200, 30);
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean permitido = solr.estado();
                if(permitido){
                    System.out.println("Hola boton eliminar, estas conectado");
                    //Eliminar();
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        //^Panel de  cambios
        ConsultaPanel = new JPanel();
        ConsultaPanel.setLayout(null);
        ConsultaPanel.setBounds(20, 20, 380, 150);
        ConsultaPanel.setBackground(Color.white);
        
        JLabel namid = new JLabel("Id: ");
        namid.setBounds(20, 20, 60, 30);
        id=new JTextField();
        id.setBounds(80, 20, 200, 30);
        //id.setBackground(Color.lightGray);
        
        JLabel namtitle = new JLabel("Tilte: ");
        namtitle.setBounds(20, 60, 60, 30);
        title=new JTextField();
        title.setBounds(80, 60, 200, 30);
        //title.setBackground(Color.lightGray);
        
        JLabel namtext = new JLabel("Text: ");
        namtext.setBounds(20, 100, 60, 30);
        text=new JTextField();
        text.setBounds(80, 100, 200, 30);
        //text.setBackground(Color.lightGray);
       
        ConsultaPanel.add(namid);
        ConsultaPanel.add(id);
        ConsultaPanel.add(namtitle);
        ConsultaPanel.add(title);
        ConsultaPanel.add(namtext);
        ConsultaPanel.add(text);
        
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
    }
    
    /**
     * Añado los datos
     */
    private void Añadir(){
        try{
            if((id.getText()!="") && (title.getText()!="") && (text.getText()!="")){
                clienteAdd.Añadir(id.getText(), title.getText(), text.getText(), coleccion);
                LimpiarField();
                //JOptionPane.showMessageDialog(null, "Añadido correctamente");
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
        
        consultas.ActualizarMiniInfo(info.getCore());
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
                //JOptionPane.showMessageDialog(null, "Eliminado correctamente");
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
        
        consultas.ActualizarMiniInfo(info.getCore());
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
