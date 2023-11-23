package control.listeners;

import control.DataController;
import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import view.DataForm;

/**
 * @author Martin Ramonda
 * Clase Listener para boton de añadir y editar.
 */
public class AddEditListener implements ActionListener{

    /*
    * INSTANCIA LA VISTA DE DATOS SEGÚN SEA la de añadir o la editar.
      desactiva la ventana principal mientras la de datos esté activa. 
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton)e.getSource();
        if(clickedButton.getText().equalsIgnoreCase("add user")){
            DataController._instance.loadView(true);
            MainController._instance.getForm().setEnabled(false);
        }
        else{
            if(MainController._instance.getNameValueInSelectedRow()==null){
                JOptionPane.showMessageDialog(null, "You must select an user to edit", "NO field SELECTED", JOptionPane.ERROR_MESSAGE);
            }
            else{
                DataController._instance.loadView(false);
                MainController._instance.getForm().setEnabled(false);
            }
        }
    }
    
}
