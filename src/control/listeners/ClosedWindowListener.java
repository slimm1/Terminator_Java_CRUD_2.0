package control.listeners;

import control.MainController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Martin Ramonda
 * Clase para recoger el evento close del formulario de datos. 
 */
public class ClosedWindowListener extends WindowAdapter{

    @Override
    public void windowClosing(WindowEvent e) {
        MainController._instance.getForm().setEnabled(true);
    }
    
}
