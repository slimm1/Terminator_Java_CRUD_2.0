package control;

import model.User;
import view.DataForm;

/**
 * @author Martin Ramonda
 * Clase diseñada con el patrón singleton para trabajar con una sola instancia y tener acceso global.
 * Controladora para la vista de editar o añadir usuario.
 */
public class DataController {
    
    public static DataController _instance = new DataController();
    
    private DataForm form;
    
    private User selectedUser;
    
    // Constructor privado. Se inicia la variable usuario seleccionado en null.
    private DataController(){
        this.selectedUser = null;
    }
    // setea el valor del usuario seleccionado en un determinado momento
    public void setSelectedUser(User u){
        this.selectedUser = u;
    }
    // devuelve el valor asignado a la variable selected user
    public User getSelectedUser(){
        return selectedUser;
    }
    // carga la vista de añadir o editar usuario en funcion de una variable booleana.
    // Si esta ventana se abre con false, se llama al metodo de carga de datos de usuario. ver textFieldLogic() 
    public void loadView(boolean add){
        form = new DataForm();
        if(add){
            form.getTitleLabel().setText("ADD USER");
        }
        else{
            form.getTitleLabel().setText("EDIT USER");
            setSelectedUser(textFieldLogic());
        }
    }
    
    /* Carga los textfields de la ventana con los datos de el usuario seleccionado. Estos datos se obtienen
    * desde la controladora del formulario padre.
    */
    public User textFieldLogic(){
        User selectedUser = MainController._instance.getListManager().getUserByName(MainController._instance.getNameValueInSelectedRow());
        form.getNameTextField().setText(selectedUser.getUsername());
        form.getPassTextField().setText(selectedUser.getPassword());
        form.getEmailTextField().setText(selectedUser.getEmail());
        form.getDateTextField().setText(selectedUser.getBirth());
        return selectedUser;
    }
}
