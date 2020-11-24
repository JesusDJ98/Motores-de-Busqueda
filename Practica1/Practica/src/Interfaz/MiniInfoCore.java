package Interfaz;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jesus Delgado
 */
public class MiniInfoCore extends JPanel{
    
    private JLabel core;
    private JLabel NDoc;
    private String coleccion;
    private int datos;
    
    public MiniInfoCore(){
        
        setLayout(null);
        setBounds(450, 35, 130, 60);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        Inicio();
    }
    
    private void Inicio(){
        datos=0;
        coleccion = "";
        core = new JLabel("Core: ");
        NDoc = new JLabel("Nº Doc: ");
        core.setBounds(5, 5, 150, 20);
        NDoc.setBounds(5, 30, 150, 20);
        
        add(core);
        add(NDoc);
    }
    
    /**
     * Cambiamos los nombres de los JLabel
     * @param c
     * @param num
     */
    public void ActualizarMinInf(String c, String num){
        coleccion = c;
        datos=0;
        try{
            datos = Integer.parseInt(num);
        }catch(Exception ex){
        }
        
        core.setText("Core: "+c);
        NDoc.setText("Nº Doc: "+num);
    }
    
    /**
     * Devuelve la coleccion
     * @return
     */
    public String getCore(){
        return coleccion;
    }
    
    /**
     * Devuelve el numero de documentos que hay
     * @return
     */
    public int getNum(){
        return datos;
    }
        
}
