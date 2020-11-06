
package Interfaz;

import Extra.ConexionSolr;
import Extra.LeerCorpus;
import Extra.LeerQuerys;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import miclientesolrj.MiClienteAddSolrj;
import miclientesolrj.MiClienteSearchSolrj;

/**
 *
 * @author Jesus Delgado
 */
public class MiFrame extends javax.swing.JFrame {

    private LeerCorpus Corpus;
    private LeerQuerys LISAQUE;
    private ConexionSolr SOLR;
    private MiClienteAddSolrj AgregarSolrj;
    private MiClienteSearchSolrj ConsultasSolrj;
    private BufferedImage image;
    
    boolean conectado;
    
    /**
     * Creates new form MiFrame
     */
    public MiFrame() {
        initComponents();
        InicializarObj();
    }
    
    private void InicializarObj(){
        Corpus = new LeerCorpus();
        LISAQUE = new LeerQuerys(35);
        SOLR = new ConexionSolr();
        AgregarSolrj = new MiClienteAddSolrj();
        ConsultasSolrj = new MiClienteSearchSolrj();
        
        conectado = false;
        image = ImagePanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        MainPanel = new javax.swing.JPanel();
        Buscador = new javax.swing.JTextField();
        SalidaPanel = new javax.swing.JScrollPane();
        MiniInfoPanel = new javax.swing.JPanel();
        NameCore = new javax.swing.JLabel();
        NameArchivos = new javax.swing.JLabel();
        Core = new javax.swing.JLabel();
        Files = new javax.swing.JLabel();
        OpcionesPanel = new javax.swing.JPanel();
        ListaOpciones = new javax.swing.JScrollPane();
        Opciones = new javax.swing.JList<>();
        Autor = new javax.swing.JLabel();
        Practica = new javax.swing.JLabel();
        Asignatura = new javax.swing.JLabel();
        AccionesCoRe = new javax.swing.JComboBox<>();
        Menu = new javax.swing.JMenuBar();
        Solr = new javax.swing.JMenu();
        Conectar = new javax.swing.JMenuItem();
        Desconectar = new javax.swing.JMenuItem();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MiniInfoPanel.setBackground(new java.awt.Color(0, 204, 255));

        NameCore.setText("Core:");

        NameArchivos.setText("Archivos:");

        Core.setBackground(new java.awt.Color(204, 204, 0));

        javax.swing.GroupLayout MiniInfoPanelLayout = new javax.swing.GroupLayout(MiniInfoPanel);
        MiniInfoPanel.setLayout(MiniInfoPanelLayout);
        MiniInfoPanelLayout.setHorizontalGroup(
            MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiniInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameArchivos)
                    .addComponent(NameCore))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Files, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(Core, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        MiniInfoPanelLayout.setVerticalGroup(
            MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiniInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameCore)
                    .addComponent(Core, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MiniInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameArchivos)
                    .addComponent(Files, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        OpcionesPanel.setBackground(new java.awt.Color(255, 51, 51));

        Opciones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Buscador", "Practica", "Modificar_Solr"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Opciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OpcionesKeyPressed(evt);
            }
        });
        Opciones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                OpcionesValueChanged(evt);
            }
        });
        ListaOpciones.setViewportView(Opciones);

        Autor.setText("Jesús Delgado");

        Practica.setText("Practica 1");

        Asignatura.setText("Motores_Busqueda");

        AccionesCoRe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Core", "Añadir", "Eliminar"}));
        AccionesCoRe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccionesCoReActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OpcionesPanelLayout = new javax.swing.GroupLayout(OpcionesPanel);
        OpcionesPanel.setLayout(OpcionesPanelLayout);
        OpcionesPanelLayout.setHorizontalGroup(
            OpcionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ListaOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(Asignatura, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OpcionesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OpcionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AccionesCoRe, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(OpcionesPanelLayout.createSequentialGroup()
                        .addGroup(OpcionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Autor)
                            .addGroup(OpcionesPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(Practica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        OpcionesPanelLayout.setVerticalGroup(
            OpcionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpcionesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Autor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Practica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Asignatura)
                .addGap(14, 14, 14)
                .addComponent(ListaOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AccionesCoRe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(OpcionesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SalidaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MiniInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MainPanelLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                                .addComponent(MiniInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)))
                        .addComponent(SalidaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(OpcionesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        Solr.setText("SOLR");

        Conectar.setText("Conectar");
        Conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConectarActionPerformed(evt);
            }
        });
        Solr.add(Conectar);

        Desconectar.setText("Desconectar");
        Desconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesconectarActionPerformed(evt);
            }
        });
        Solr.add(Desconectar);

        Menu.add(Solr);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesconectarActionPerformed
        // TODO add your handling code here:
        SOLR.CerrarConexion();
        conectado = false;
        JOptionPane.showMessageDialog(null, "Conexion cerrada");
    }//GEN-LAST:event_DesconectarActionPerformed

    private void ConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConectarActionPerformed
        // TODO add your handling code here:
        SOLR.Conexion();
        conectado = true;
        //JOptionPane.showMessageDialog(null, "Conexion abierta");
        //No necesaria, pues ya abrimos el navegador
    }//GEN-LAST:event_ConectarActionPerformed

    private void OpcionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OpcionesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OpcionesKeyPressed

    private void OpcionesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_OpcionesValueChanged
        // TODO add your handling code here:
        
        if(!evt.getValueIsAdjusting()){
            System.out.println(Opciones.getSelectedValue().toString());
            MainPanel.setVisible(false);
            
        }/*else{//Se muestra al mismo time que lo otro
            System.out.println("Hola");
        }*/
        
    }//GEN-LAST:event_OpcionesValueChanged

    private void AccionesCoReActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccionesCoReActionPerformed
        // TODO add your handling code here:
        System.out.println("Click");
        
    }//GEN-LAST:event_AccionesCoReActionPerformed
    
    /**
     * Creamos la imagen de la lupa
     * @return
     */
    public BufferedImage ImagePanel() {
        BufferedImage imagen=null;
       try {                
           imagen = ImageIO.read(new File("Imagenes/Lupa.png"));
       } catch (IOException ex) {
            // handle exception...
       }
       return imagen;
    }
    
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    /*    java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AccionesCoRe;
    private javax.swing.JLabel Asignatura;
    private javax.swing.JLabel Autor;
    private javax.swing.JTextField Buscador;
    private javax.swing.JMenuItem Conectar;
    private javax.swing.JLabel Core;
    private javax.swing.JMenuItem Desconectar;
    private javax.swing.JLabel Files;
    private javax.swing.JScrollPane ListaOpciones;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JPanel MiniInfoPanel;
    private javax.swing.JLabel NameArchivos;
    private javax.swing.JLabel NameCore;
    private javax.swing.JList<String> Opciones;
    private javax.swing.JPanel OpcionesPanel;
    private javax.swing.JLabel Practica;
    private javax.swing.JScrollPane SalidaPanel;
    private javax.swing.JMenu Solr;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    // End of variables declaration//GEN-END:variables
}
