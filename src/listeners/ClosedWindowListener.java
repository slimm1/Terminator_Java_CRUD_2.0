/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import control.MainController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Vespertino
 */
public class ClosedWindowListener extends WindowAdapter{

    @Override
    public void windowClosing(WindowEvent e) {
        MainController._instance.getForm().setEnabled(true);
    }
    
}
