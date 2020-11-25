package Interfaz;

import Extra.ConexionSolr;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import miclientesolrj.MiClienteSearchSolrj;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author Jesus Delgado
 */
public class PanelPrincipal extends JPanel{
    
    private JList<String> SalidaPanel;
    private JTextField buscador;
    
    private MiClienteSearchSolrj search;
    private ConexionSolr conexion;
    
    private MiniInfoCore info;
    
    public PanelPrincipal(MiClienteSearchSolrj s, ConexionSolr c, MiniInfoCore f){
        //super();
        setLayout(null);
        setBounds(150, 100, 430, 350);
        setBackground(Color.white);
        
        Inicio(s, c, f);
    }
    
    private void Inicio(MiClienteSearchSolrj s, ConexionSolr c, MiniInfoCore f){
        search=s;
        conexion=c;
        info = f;
        
        Image img = new ImageIcon("src/Imagenes/Lupa.png").getImage();
        ImageIcon imagenBusc=new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        
        buscador = new JTextField();           
        buscador.setBounds(25, 5, 260, 32);
        buscador.setBorder(null);
        buscador.setBackground(Color.lightGray);
        buscador.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(conexion.estado()){
                        RealizarBusqueda();
                        buscador.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null, "No estas conectado a SOLR");
                        buscador.setText("");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        
        JLabel label = new JLabel();
        label.setIcon(imagenBusc); 
        label.setBounds(5, 0, 270, 40);
        label.add(buscador);
        
        JPanel aux = new JPanel();
        aux.setLayout(null);
        aux.setBounds(60, 10, 300, 40);
        aux.setBackground(Color.lightGray);
        aux.setBorder(BorderFactory.createLineBorder(Color.black));
        aux.add(label); //Para darle el color blanco abajo
        
        SalidaPanel = new JList();
        SalidaPanel.setLayout(null);
        SalidaPanel.setBounds(5, 5, 370, 140);
        SalidaPanel.setBackground(Color.lightGray);
        
        SalidaPanel.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        JScrollPane scroll = new JScrollPane(SalidaPanel);
        scroll.setBounds(20, 100, 380, 220);
        
        
        add(scroll);
        add(aux);
    }
    
    /**
     * Realizamos las busqueda
     */
    public void RealizarBusqueda(){
        try {
            search.RealizarQuery(buscador.getText(), info.getCore());
            String[] s = search.getSalidaB();
            
            if(s!=null){
                SalidaPanel.setListData(s);
            }else{
                System.out.println("NULL");
            }
        } catch (SolrServerException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta");
        }
    }
    
    
}
