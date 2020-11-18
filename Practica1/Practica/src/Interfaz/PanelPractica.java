package Interfaz;

import Extra.*;
import java.awt.Color;
import miclientesolrj.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    
    MiniInfoCore info;
    
    JButton Corpus;
    JButton Query;
    JList<String> SalidaPanel;
    JLabel accion;
    private JLabel labelAcu;
    
    public PanelPractica(LeerCorpus corpus, LeerQuerys q, MiClienteAddSolrj add,
            MiClienteSearchSolrj search, ConexionSolr s, MiniInfoCore f){
        //super();
        setLayout(null);
        setBounds(150, 100, 430, 330);
        setBackground(Color.white);
        
        solr = s;
        lisa = corpus;
        query = q;
        consultas = search;
        clientaAdd = add;
        info = f;
        Init();
    }
    
    /**
     * Añadimos todos los elementos del panel
     * @param p
     * @param s
     */
    private void Init(){
        
        Corpus = new JButton();
        Corpus.setBounds(100, 10, 200, 30);
        //Corpus.setEnabled(false);
        Corpus.setText("Añadir Corpus");
        Corpus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean permitido = solr.estado();
                if(permitido){
                    Corpus_SOLR();
                    consultas.ActualizarMiniInfo(info.getCore());
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        Query = new JButton();
        Query.setBounds(100, 60, 200, 30);
        //Query.setEnabled(false);
        Query.setText("Realizar Querys");
        Query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean permitido = solr.estado();
                if(permitido){
                    Query_SOLR();
                    consultas.ActualizarMiniInfo(info.getCore());
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
            }
        });
        
        SalidaPanel = new JList();
        SalidaPanel.setLayout(null);
        SalidaPanel.setBounds(5, 5, 370, 170);
        SalidaPanel.setBackground(Color.lightGray);
        
        SalidaPanel.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        JScrollPane scroll = new JScrollPane(SalidaPanel);
        scroll.setBounds(20, 120, 380, 170);
        
        accion = new JLabel("Accion: ");
        accion.setBounds(20, 290, 400, 30);
        
        
        /*JPanel panelAcu = new JPanel();
        panelAcu.setLayout(null);
        panelAcu.setBackground(Color.red);
        panelAcu.setBounds(310, 30, 120, 40);*/
        
        labelAcu = new JLabel();
        labelAcu.setBounds(310, 30, 120, 40);
        labelAcu.setText("");
        
       
        add(Corpus);
        add(Query);
        add(scroll);
        add(accion);
        add(labelAcu);
        
    }
    
    
    /**
     * Leemos el corpus y lo añadimos a SOLR
     */
    private void Corpus_SOLR(){
        accion.setText("Accion: Leyendo el CORPUS");
        //Leo el Corpus
        String[] listado = lisa.Listado();
        for(int i = 0; i<listado.length; i++){
            System.out.println("File: "+listado[i]);
            accion.setText("Accion: Leyendo "+listado[i]);
            try {
                lisa.LeerFicheros(listado[i]);
                Thread.sleep(200); //Mini descanso para que de time a actualizar
            } catch (FileNotFoundException | InterruptedException ex) {
                accion.setText("Accion: Error leyendo "+listado[i]);
            }
        }
        accion.setText("Accion: Añadiendo el Corpus a SOLR");
        try {
            
            Thread.sleep(1000);//espero
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
        
        consultas.ActualizarMiniInfo(info.getCore());
        //Actualizamos y esperamos
        try{
            Thread.sleep(1000);
        }catch(Exception ex){
        }
        
        accion.setText("Accion: Todo el CORPUS Agregado");
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
        
        query.setCant(a);
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
        
        accion.setText("Accion: Generando Trec_Eval");
        //Aqui genero el traceval
        
        //Luego comparo
        
        //Lo escribo
        labelAcu.setText("Presición: ");
        
    }
    
}
