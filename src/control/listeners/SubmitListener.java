package control.listeners;

import control.DataController;
import control.MainController;
import control.constants.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.User;
import view.DataForm;

/**
 * @author Martin Ramonda
 * Clase listener para el boton de aceptar en el formulario de datos (DataForm)
 */
public class SubmitListener implements ActionListener{
    
    
    // accede a la instancia de DataForm a través de la clase SwingUtilities. Carga los datos recogidos en los
    // text fields y realiza una acción u otra según sea el formulario de añadir o editar usuario.
    @Override
    public void actionPerformed(ActionEvent e) {
        DataForm d = (DataForm)SwingUtilities.getWindowAncestor((JButton)e.getSource());
        String username = d.getNameTextField().getText();
        String email = d.getEmailTextField().getText();
        String password = d.getPassTextField().getText();
        String birth = d.getDateTextField().getText();
        if(d.getTitleLabel().getText().equalsIgnoreCase("add user")){
            submitAddUser(d, username, password, email, birth);
        }
        else{
            submitEditUser(d, username, password, email, birth);
        }
    }
    
    /*
    * Comprueba que todo sea correcto en los textfields cuando se presione el boton de aceptar y sea la opción de editar usuario.
        - comprueba si hay cambios para guardar
        - comprueba si hay espacios en blanco
        - comprueba que el email coincide con el patron establecido en una regex
        - comprueba que el nombre actualizado no coincida con el de otro usuario existente en la lista
    Si pasa todas las comprobaciones: actualiza la base de datos, setea la lista con los nuevos datos, carga la tabla y cierra el formulario.
    */
    public void submitEditUser(DataForm d,String username, String password,String email, String birth){
        User selectedUser = DataController._instance.getSelectedUser();
        boolean changes = equalFields(username, password, email, birth, selectedUser);
        if(!changes){
           JOptionPane.showMessageDialog(null, "The programm did not detect changes in selected user", "NO CHANGES", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(checkEmptyFields(username, password, email, birth)){
           JOptionPane.showMessageDialog(null, "Some field is empty in the form", "Empty field error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!emailChecker(email)){
           JOptionPane.showMessageDialog(null, "invalid e-mail format", "e-mail error", JOptionPane.ERROR_MESSAGE);
        }
        else if(MainController._instance.getListManager().getUserByName(username)!=null && !username.equalsIgnoreCase(selectedUser.getUsername())){
           JOptionPane.showMessageDialog(null, "invalid name. This username already exists", "name error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            MainController._instance.getDbLoader().updateUserStatment(selectedUser, username, password, email, birth);
            MainController._instance.getListManager().setList(MainController._instance.getDbLoader().loadListFromDataBase());
            MainController._instance.loadTable();
            MainController._instance.getForm().setEnabled(true);
            JOptionPane.showMessageDialog(null, "User edited succesfully", "USER EDITED on DB", JOptionPane.INFORMATION_MESSAGE);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    // Método similar al de editar. 
    public void submitAddUser(DataForm d,String username, String password,String email, String birth){
        if(!emailChecker(email)){
           JOptionPane.showMessageDialog(null, "invalid e-mail format", "e-mail error", JOptionPane.ERROR_MESSAGE);
        }
        else if(MainController._instance.getListManager().getUserByName(username)!=null){
           JOptionPane.showMessageDialog(null, "invalid name. This username already exists", "name error", JOptionPane.ERROR_MESSAGE);
        }
        else if(checkEmptyFields(username, password, email, birth)){
           JOptionPane.showMessageDialog(null, "Some field is empty in the form", "Empty field error", JOptionPane.ERROR_MESSAGE);
        }
        else{
           MainController._instance.getDbLoader().insertUserStatment(username, password, email, birth);
           MainController._instance.getListManager().setList(MainController._instance.getDbLoader().loadListFromDataBase());
           MainController._instance.loadTable();
           MainController._instance.getForm().setEnabled(true);
           JOptionPane.showMessageDialog(null, "New user added succesfully", "USER ADDED TO DB", JOptionPane.INFORMATION_MESSAGE);
           d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    // Checkea si alguno de los parametros contiene una cadena vacía.
    public boolean checkEmptyFields(String username, String password,String email, String birth){
        if(email.isEmpty()){
           return true;
        }
        else if(username.isEmpty()){
            return true;
        }
        else if(birth.isEmpty()){
           return true;
        }
        else if(password.isEmpty()){
           return true;
        }
        return false;
    }
    
    // comprueba el email a través de una regex.
    public boolean emailChecker(String email){
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();    
    }
    
    // comprueba si alguno de los params coincide con los del usuario seleccionado.
    public boolean equalFields(String username, String password,String email, String birth, User selectedUser){
        boolean nameBool, emailBool, passwordBool, dateBool;
        nameBool = username.equalsIgnoreCase(selectedUser.getUsername());
        emailBool = email.equalsIgnoreCase(selectedUser.getEmail());
        passwordBool = password.equalsIgnoreCase(selectedUser.getPassword());
        dateBool = birth.equalsIgnoreCase(selectedUser.getBirth());
        if(nameBool && emailBool && passwordBool && dateBool){return false;}
        return true;
    }
}
