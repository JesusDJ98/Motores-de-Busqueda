package Interfaz;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import Extra.LeerQuerys;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import miclientesolrj.MiClienteAddSolrj;
import miclientesolrj.MiClienteSearchSolrj;

/**
 *
 * @author Jesus Delgado
 */
public class MYFrame extends JFrame{
    
    //Material de la Interfaz
    private  JComboBox box;
    //private  JPanel mainPanel;
    private  PanelPrincipal mainP;
    private  PanelPractica practP;
    private  PanelConsultas consulP;
    private  int posBox;
    
    //Objetos de clases
    private LeerCorpus Corpus;
    private LeerQuerys LISAQUE;
    private ConexionSolr SOLR;
    private MiClienteAddSolrj AgregarSolrj;
    private MiClienteSearchSolrj ConsultasSolrj;
    //private BufferedImage image;
    
    boolean conectado;
    
    
    public MYFrame(){
        //super();
        this.setLayout(null);//Give us control
        setTitle("SRI Motores_Busqueda");
        this.setSize(600, 500);
        this.setPreferredSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Init();
    }
    
    private void Init(){
        //Añadimos la barra del menu de SOLR 
        this.add(MenuSolr());
        //Añadimos menu de Opciones 
        add(PanelOpciones());
        //Panel de info propio
        add(PanelDatos());
        
        //Inicializamos los objetos
        InicializarObj();
        
        //Paneles que se iran modificando
        mainP = new PanelPrincipal(conectado);
        //mainP.setEnabled(false);
        mainP.setVisible(false);
        practP = new PanelPractica(conectado, SOLR);
        //practP.setEnabled(false);
        practP.setVisible(true);
        consulP = new PanelConsultas(conectado, SOLR);
        //consulP.setEnabled(false);
        consulP.setVisible(false);
        
        add(mainP);
        add(practP);
        add(consulP);
    }
    
    /**
     * Generamos el menu de opciones 
     * para conectarse o desconctarse 
     * de SOLR
     * @return
     */
    private JMenuBar MenuSolr(){
        
        JMenuBar options = new JMenuBar();
        options.setBackground(Color.blue);
        options.setSize(this.getWidth(), 30);//size
        options.setLocation(0, 0);//position
        options.setLayout(null);
        
        JMenu Solr = new JMenu();
        Solr.setBounds(10, 5, 90, 20);
        Solr.setText("SOLR");
        Solr.setLayout(null);

        JMenuItem Conectar = new JMenuItem();
        Conectar.setText("Conectar");
        Conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConectarActionPerformed(evt);
                practP.Actualizar(conectado);
            }
        });
        
        JMenuItem Desconectar = new JMenuItem();
        Desconectar.setText("Desconectar");
        Desconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesconectarActionPerformed(evt);
                practP.Actualizar(conectado);
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
        SOLR.CerrarConexion();
        conectado = false;
        JOptionPane.showMessageDialog(null, "Conexion cerrada");
    }   
    /**
     * Nos conectamos de SOLR
     * @param evt
     */
    private void ConectarActionPerformed(java.awt.event.ActionEvent evt) {
        SOLR.Conexion();
        conectado = true;
    }
    
    /**
     * Creamos el panel con la lista de opciones
     * @return
     */
    private JPanel PanelOpciones(){
        JPanel opciones = new JPanel();
        opciones.setBounds(15, 100, 125, 250);
        opciones.setBackground(Color.red);
        opciones.setLayout(null);
        
        //ComboBox del Core
        String[] OpcionesCore = new String[3];
        OpcionesCore[0] ="Core";
        OpcionesCore[1] ="Eliminar";
        OpcionesCore[2] ="Nuevo";
        
        box = new JComboBox(OpcionesCore);
        box.setBounds(10, 200, 100, 30);
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pos = box.getSelectedIndex();
                switch(pos){
                    case 0:
                    {
                        JOptionPane.showMessageDialog(null, "Aun no he implementado el tener los cores disponibles");
                        setBox("micoleccion");
                        consulP.Actualizar(conectado, "micoleccion");
                        //practP.Actualizar(conectado);//Esta siempre es "micoleccion"
                    }
                        break;
                    case 1:
                    {
                        JOptionPane.showMessageDialog(null, "Aun no he implementado el eliminar el core seleccionado");
                    }
                        break;
                    case 2:
                    {
                        JOptionPane.showMessageDialog(null, "Aun no he implementado el añadir nuevo core");
                    }    
                        break;
                    default: 
                }
            }
        });
        
        //Lista de Acciones
        String[] op = { "Buscador", "Practica", "Modificar_Solr" };
        JList<String> Lista = new JList<>(op);
        Lista.setBounds(0, 40, 130, 150);
        Lista.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                OpcionesValueChanged(evt);
            }
        });
        
        opciones.add(box);
        opciones.add(Lista);
        
        return opciones;
    }
    
    /**
     * Funcion de la lista 
     * @param evt
     */
    public void OpcionesValueChanged(javax.swing.event.ListSelectionEvent evt) {                                      
        // TODO add your handling code here:
        if(evt.getValueIsAdjusting()){
            Object Lista = evt.getSource();
            JList<String> L = (JList<String>)Lista;
            /*System.out.println(L.getSelectedValue());
            System.out.println(L.getSelectedIndex());*/
            int op = L.getSelectedIndex();
            posBox=op;
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
                    mainP.setVisible(false);
                    practP.setVisible(true);
                    consulP.setVisible(false);
                }
            }
            
        }
        
    }    
    
    /**
     * Modificamos el nombre del core
     * @param s
     */
    private void setBox(String s){
        box.removeAllItems();
        box.addItem(s);
        box.addItem("Eliminar");
        box.addItem("Nuevo");
    }
    
    /**
     * Panel con los datos de la practica
     * @return
     */
    private JPanel PanelDatos(){
        JPanel datos = new JPanel();
        datos.setBounds(20, 10, 120, 80);
        //datos.setBackground(Color.yellow);
        datos.setLayout(null);
        
        JLabel name = new JLabel("Jesús Delgado");
        name.setBounds(10, 30, 110, 20);
        JLabel pract = new JLabel("Practica 1");
        pract.setBounds(25, 45, 80, 20);
        JLabel asig = new JLabel("Motores_Busqueda");
        asig.setBounds(0, 60, 120, 20);
        
        datos.add(name);
        datos.add(pract);
        datos.add(asig);
        
        return datos;
    }
    
    
    
    /**
     * Iicializamos los objetos de las clases que utilizaremos
     */
    private void InicializarObj(){
        Corpus = new LeerCorpus();
        LISAQUE = new LeerQuerys(35);
        SOLR = new ConexionSolr();
        AgregarSolrj = new MiClienteAddSolrj();
        ConsultasSolrj = new MiClienteSearchSolrj();
        
        conectado = false;
        //image = ImagePanel();
    }
    
}
