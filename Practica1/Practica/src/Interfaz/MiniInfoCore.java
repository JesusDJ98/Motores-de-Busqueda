/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jesus Delgado
 */
public class MiniInfoCore extends JPanel{
    
    private JLabel core;
    private JLabel NDoc;
    
    public MiniInfoCore(){
        
        setLayout(null);
        setBounds(450, 30, 200, 60);
        
        Inicio();
    }
    
    private void Inicio(){
        core = new JLabel("Core: ");
        NDoc = new JLabel("Nº Doc: ");
        core.setBounds(0, 0, 150, 20);
        NDoc.setBounds(0, 30, 150, 20);
        
        add(core);
        add(NDoc);
    }
    
    /**
     * Cambiamos los nombres de los JLabel
     * @param c
     * @param num
     */
    public void ActualizarMinInf(String c, String num){
        core.setText("Core: "+c);
        NDoc.setText("Nº Doc: "+num);
    }
        
}
