package control.listeners;

import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * @author Martin Ramonda
 * Clase listener para accion click en el boton de crear base de datos en la ventana principal.
 */
public class CreateDatabaseListener implements ActionListener{
   
    // inicia un filechooser para elegir la localización en la que se va a guardar la nueva base de datos. 
    // recarga la lógica de botones en la ventana principal después de conectar con la base de datos creada. ver método
    // bdConnect(String) en la clase SqliteLoader.
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser f = new JFileChooser();
        f.showSaveDialog(((JButton)e.getSource()).getParent().getParent());
        File dir = f.getSelectedFile();
        MainController._instance.buttonLogic(MainController._instance.getDbLoader().bdConnect(dir.getAbsolutePath()+".db"));
    }
    
}
