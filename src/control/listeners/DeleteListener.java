package control.listeners;

import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User;

/**
 * @author Martin Ramonda
 * Clase listener para boton de borrar usuario en ventana principal (MainForm)
 */
public class DeleteListener implements ActionListener{

    // Accion de borrado en click, si hay objeto seleccionado borra, si no lanza mensaje.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainController._instance.getNameValueInSelectedRow()==null){
            JOptionPane.showMessageDialog(null, "You must select an user to edit", "NO field SELECTED", JOptionPane.ERROR_MESSAGE);
        }
        else{
            deleteUser();
        }
    }
    
    // recupera el usuario cargado en memoria a través del nombre. Elimina el usuario de la base datos y recarga la lista y la tabla.
    public void deleteUser(){
        User selectedUser = MainController._instance.getListManager().getUserByName(MainController._instance.getNameValueInSelectedRow());
        MainController._instance.getDbLoader().removeUserStatment(selectedUser);
        MainController._instance.getListManager().setList(MainController._instance.getDbLoader().loadListFromDataBase());
        MainController._instance.loadTable();
        JOptionPane.showMessageDialog(null, "User deleted succesfully", "USER DELETED on DB", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
