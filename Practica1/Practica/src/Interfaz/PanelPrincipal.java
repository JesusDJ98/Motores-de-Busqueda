/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Jesus Delgado
 */
public class PanelPrincipal extends JPanel{
    
    private boolean permitido;
    private JList<String> SalidaPanel;
    
    public PanelPrincipal(boolean p){
        //super();
        setLayout(null);
        setBounds(150, 10, 430, 400);
        //setBackground(Color.green);
        
        permitido=p;
        Inicio();
    }
    
    private void Inicio(){
        
        Image img = new ImageIcon("src/Imagenes/Lupa.png").getImage();
        ImageIcon imagenBusc=new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        
        JTextField buscador = new JTextField();           
        buscador.setBounds(25, 0, 220, 30);
        buscador.setBorder(null);
        
        JLabel label = new JLabel();
        label.setIcon(imagenBusc); //NO hace nada
        label.setBounds(30, 100, 250, 30);
        label.add(buscador);
        
        
        
        
        
        SalidaPanel = new JList();
        SalidaPanel.setLayout(null);
        SalidaPanel.setBounds(5, 5, 370, 140);
        
        SalidaPanel.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        JScrollPane scroll = new JScrollPane(SalidaPanel);
        scroll.setBounds(20, 200, 380, 180);
        
        
        add(scroll);
        //add(buscador);
        add(label);
        //add(imagenBusc);
        
    }
    
    
    /**
     * Cuand cambiamos el core este se debe modificar tambien
     * @param core
     * @param cantidad
     */
    public void ActualizarMiniInfo(String core, String cantidad){
        
        
    }
    
    
}
