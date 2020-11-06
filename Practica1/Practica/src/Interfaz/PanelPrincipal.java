/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Jesus Delgado
 */
public class PanelPrincipal extends JPanel{
    
    private boolean permitido;
    
    public PanelPrincipal(boolean p){
        //super();
        setLayout(null);
        setBounds(150, 10, 430, 400);
        setBackground(Color.green);
        
        permitido=p;
    }
    
    
}
