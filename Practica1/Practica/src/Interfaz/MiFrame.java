package Interfaz;

import Extra.ConexionSolr;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import miclientesolrj.MiClienteSearchSolrj;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

/**
 *
 * @author Jesus Delgado
 */
public class MiFrame extends JFrame{
    
    class Indice{
        Indice(int i){
            id = i;
        }
        public int id;
    }
    
    //Material de la Interfaz
    private  JComboBox box;
    private  PanelPrincipal mainP;
    private  PanelPractica practP;
    private  PanelConsultas consulP;
    private MiniInfoCore info;
    
    private ConexionSolr solr;
    private MiClienteSearchSolrj search;
   
    private String[] OpcionesCore;
    private JButton boton;
    
    
    public MiFrame(MiniInfoCore p0, PanelPrincipal p1, PanelConsultas p2, PanelPractica p3, ConexionSolr s, MiClienteSearchSolrj s2){
        //super();
        this.setLayout(null);//Give us control
        setTitle("SRI Motores_Busqueda");
        this.setSize(600, 480);
        this.setPreferredSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Init(p0, p1, p2, p3, s, s2);
    }
    
    private void Init(MiniInfoCore p0, PanelPrincipal p1, PanelConsultas p2, PanelPractica p3, ConexionSolr s, MiClienteSearchSolrj s2){
        solr = s;
        search = s2;
        OpcionesCore = new String[1];
        OpcionesCore[0]= "Core";
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 480);
        panel.setBackground(Color.white);
        
        
        
        //Añadimos la barra del menu de SOLR 
        panel.add(MenuSolr());
        //Añadimos menu de Opciones 
        panel.add(PanelOpciones());
        //Panel de info propio
        panel.add(PanelDatos());
        
        info = p0;
        
        //Paneles que se iran modificando
        mainP = p1;
        mainP.setVisible(true);
        practP = p3;
        practP.setVisible(false);
        consulP = p2;
        consulP.setVisible(false);
        
        panel.add(info);
        panel.add(mainP);
        panel.add(practP);
        panel.add(consulP);
        
        add(panel);
    }
    
    /**
     * Generamos el menu de opciones 
     * para conectarse o desconctarse 
     * de SOLR
     * @return
     */
    private JMenuBar MenuSolr(){
        
        JMenuBar options = new JMenuBar();
        options.setBackground(Color.WHITE);
        options.setSize(this.getWidth(), 30);//size
        options.setLocation(0, 0);//position
        options.setLayout(null);
        
        JMenu Solr = new JMenu("SOLR");
        Solr.setBounds(10, 5, 200, 20);
        Solr.setLayout(null);

        JMenuItem Conectar = new JMenuItem();
        Conectar.setText("Conectar");
        Conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConectarActionPerformed(evt);
                Solr.setText("Conectado a SOLR");
                try{
                    Thread.sleep(2000);
                }catch(Exception ex){
                }
                //System.out.println("COre: "+(String)box.getSelectedItem());
                
                search.ActualizarMiniInfo((String)box.getSelectedItem());
            }
        });
        
        JMenuItem Desconectar = new JMenuItem();
        Desconectar.setText("Desconectar");
        Desconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesconectarActionPerformed(evt);
                Solr.setText("Desconectado de SOLR");
            }
        });
        
        Solr.add(Conectar);
        Solr.add(Desconectar);

        options.add(Solr);
        return options;
    }
    /**
     * Nos desconectamos de SOLR
     * @param evt
     */
    private void DesconectarActionPerformed(java.awt.event.ActionEvent evt) {
        if(!solr.estado()){
            Salir(0);
        }else{
            Salir(1);
        }
        solr.CerrarConexion();
        JOptionPane.showMessageDialog(null, "Conexion cerrada");
    }   
    /**
     * Nos conectamos de SOLR
     * @param evt
     */
    private void ConectarActionPerformed(java.awt.event.ActionEvent evt) {
        solr.Conexion();
        
        try{
            Thread.sleep(2000);
        }catch(Exception ex){
        }
        //OpcionesCore = Cores();
        setBox(Cores(), 1);
    }
    
    /**
     * Creamos el panel con la lista de opciones
     * @return
     */
    private JPanel PanelOpciones(){
        JPanel opciones = new JPanel();
        opciones.setBounds(15, 110, 125, 310);
        opciones.setBackground(Color.white);
        opciones.setLayout(null);
        opciones.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        box = new JComboBox(OpcionesCore);
        //box.setBackground(Color.LIGHT_GRAY);
        //box.setName("Core");
        box.setBounds(0, 0, 100, 30);
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean permitido=solr.estado();
                if(permitido){
                    String namecore = (String) box.getSelectedItem();
                    box.setName(namecore);//Cambio el nombre
                    
                    if(box.getItemCount()>1){ //Ni null ni Core
                        search.ActualizarMiniInfo((String)box.getSelectedItem());
                    }
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "No estas conectado");
                }
                
            }
        });
        
        boton =  Botones();
        
        JLabel aux = new JLabel();
        aux.setBounds(5, 200, 120, 30);
        //aux.setText("HOLAA");
        aux.add(box);
        aux.add(boton);
        
        
        aux.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("AhoraClick");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("AhoraPressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("AhoraRelease");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setVisible(false);
            }
        });
        
        
        //Lista de Acciones
        String[] op = { "Buscador", "Practica", "Modificar_Solr" };
        JList<String> Lista = new JList<>(op);
        Lista.setBounds(5, 40, 100, 150);
        Lista.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                OpcionesValueChanged(evt);
            }
        });
        //Lista.setBackground(Color.LIGHT_GRAY);
        
        //opciones.add(box);
        opciones.add(Lista);
        opciones.add(aux);
        
        //opciones.add(boton);
        
        return opciones;
    }
    
    /**
     * Funcion de la lista 
     * @param evt
     */
    private void OpcionesValueChanged(javax.swing.event.ListSelectionEvent evt) {                                      
        // TODO add your handling code here:
        if(evt.getValueIsAdjusting()){
            Object Lista = evt.getSource();
            JList<String> L = (JList<String>)Lista;
            int op = L.getSelectedIndex();
            switch(op){
                case 0:
                {
                    mainP.setVisible(true);
                    practP.setVisible(false);
                    consulP.setVisible(false);
                }
                    break;
                case 1: //Caso de Practica
                {
                    mainP.setVisible(false);
                    practP.setVisible(true);
                    consulP.setVisible(false);
                }
                    break;
                case 2: //Caso de Modificar SOLR
                {
                    mainP.setVisible(false);
                    practP.setVisible(false);
                    consulP.setVisible(true);
                }
                    break;
                default: //Tanto el caso 0 como cualquier otro caso, quiero que muestre la pantalla de inicio
                {
                    mainP.setVisible(true);
                    practP.setVisible(false);
                    consulP.setVisible(false);
                }
            }
            
        }
        
    }    
    
    /**
     * Modificamos el nombre del core
     * @param s
     * @param op
     */
    public void setBox(String[] s, int op){
        if(op==1){
            box.removeAllItems();

            if(s.length == 0){
                box.removeAllItems();
                box.addItem("Core");
            }else{
                for (int i = 0; i < s.length; i++) {
                    box.addItem(s[i]);
                }
            }
        }
        
        
    }
    
    public JButton Botones(){
        JButton mas = new JButton();
        mas.setLayout(null);
        mas.setBounds(100, 5, 18, 20);
        Image img = new ImageIcon("src/Imagenes/Signos2.png").getImage();
        ImageIcon imagenBusc=new ImageIcon(img.getScaledInstance(10, 15, Image.SCALE_SMOOTH));
        mas.setIcon(imagenBusc);
        
        mas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newCore = JOptionPane.showInputDialog(null, "Nombre repetido elimina, nombre nuevo inserta");
                //System.out.println("Nuevo: "+newCore);
                if(newCore!=null){
                    boolean permitido=solr.estado();
                    if(permitido){
                        String actual = (String)box.getSelectedItem();
                        Indice indice = new Indice(0);
                        boolean nuevoC = AñadirCore(newCore, indice);
                        if(nuevoC){//Añadimos
                            solr.NewColeccion(newCore);
                        }else{//Eliminamos
                            solr.DeleteColeccion(newCore);
                        }
                       try{//Doy margen
                           Thread.sleep(1000);
                       }catch(Exception ex){
                           
                       }
                        //Actualiza
                        setBox(Cores(), 1);
                        if(!actual.equals(newCore)){
                            box.setSelectedItem(actual);
                        }
                        search.ActualizarMiniInfo((String)box.getSelectedItem());
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "No estas conectado");
                    } 
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("AhoraPressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("AhoraRelease");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setVisible(false);
            }
        });
        
        
        mas.setVisible(false);
        return mas;
    }
    
    /**
     * Panel con los datos de la practica
     * @return
     */
    private JPanel PanelDatos(){
        JPanel datos = new JPanel();
        datos.setLayout(null);
        datos.setBounds(20, 40, 120, 60);
        datos.setBackground(Color.white);
        datos.setBorder(BorderFactory.createBevelBorder(0));
        
        JLabel name = new JLabel("Jesús Delgado");
        name.setBounds(10, 5, 110, 20);
        JLabel pract = new JLabel("Practica 1");
        pract.setBounds(25, 20, 80, 20);
        JLabel asig = new JLabel("Motores_Busqueda");
        asig.setBounds(5, 35, 120, 20);
        
        datos.add(name);
        datos.add(pract);
        datos.add(asig);
        
        return datos;
    }
    
    private void Salir(int op){
        //Muestra dos vece NO estas CONECTADO
        setBox(new String[0], op);
        //MiniInfo vacio
        info.ActualizarMinInf("", "");
    }
    
    private String[] Cores(){
        
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/").build();
        CoreAdminRequest request = new CoreAdminRequest();
        request.setAction(CoreAdminAction.STATUS);
        CoreAdminResponse cores=null;
        boolean encontrado=false;
        while(!encontrado){
            try {
                cores = request.process(solr);
                encontrado=true;
            } catch (SolrServerException | IOException ex) {
                //JOptionPane.showMessageDialog(null, "Error recuperando cores: "+ex);
            }
        }
        try{    //Margen para que coja bien todos los cores
            Thread.sleep(1200);
        }catch(Exception ex){
        }
        
        //Los guardamos
        List<String> coreList = new ArrayList<String>();
        for (int i = 0; i < cores.getCoreStatus().size(); i++) {
            coreList.add(cores.getCoreStatus().getName(i));
            //System.out.println(cores.getCoreStatus().getName(i));
        }
        
        String[] coresDisponibles = new String[coreList.size()];
        for (int i = 0; i < coreList.size(); i++) {
            coresDisponibles[i]=coreList.get(i);
        }
        
        return coresDisponibles;
    }
    
    /**
     * Vemos si es nuevo o no el core introducido
     * @param NuevoCore
     * @param indice
     * @return
     */
    private boolean AñadirCore(String NuevoCore, Indice indice){
        
        boolean nuevo = true;
        int i = 0;
        //System.out.println("Opciones core: "+OpcionesCore.length);
        while(i<box.getItemCount() & nuevo){
            //System.out.println("Comparo con: "+box.getItemAt(i));
            if(box.getItemAt(i).equals(NuevoCore)){
                nuevo = false;
                indice.id = i;
            }
            i++;
        }
        return nuevo;
    }
}
