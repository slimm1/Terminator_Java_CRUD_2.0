package control;

import model.User;
import view.DataForm;

/**
 *
 * @author Martin Ramonda
 */
public class DataController {
    
    public static DataController _instance = new DataController();
    
    private DataForm form;
    
    private User selectedUser;
    
    private DataController(){
        this.selectedUser = null;
    }
    
    public void setSelectedUser(User u){
        this.selectedUser = u;
    }
    
    public User getSelectedUser(){
        return selectedUser;
    }
    
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
    
    public User textFieldLogic(){
        User selectedUser = MainController._instance.getListManager().getUserByName(MainController._instance.getNameValueInSelectedRow());
        if(form.getTitleLabel().getText().equalsIgnoreCase("edit user")){
            form.getNameTextField().setText(selectedUser.getUsername());
            form.getPassTextField().setText(selectedUser.getPassword());
            form.getEmailTextField().setText(selectedUser.getEmail());
            form.getDateTextField().setText(selectedUser.getBirth());
        }
        return selectedUser;
    }
}
