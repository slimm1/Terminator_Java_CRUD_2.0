package control;

import control.dbManager.SqliteLoader;
import javax.swing.table.DefaultTableModel;
import model.User;
import model.UserList;
import view.MainForm;

/**
 * @author Martin Ramonda
 * Clase controladora para el formulario principal. Instanciada con el patrón singleton para acceso global y unica instancia.
 */
public class MainController {
    
    private MainForm m;
    private SqliteLoader loader;
    private UserList listManager;
    
    public static MainController _instance = new MainController();
    
    //Constructor privado, inicia una instancia SqliteLoader.
    private MainController(){
        loader = new SqliteLoader();
    }
    
    // Devuelve la instancia del formulario para acceso externo
    public MainForm getForm(){
        return m;
    }
    
    // Devuelve la instancia del loader
    public SqliteLoader getDbLoader(){
        return loader;
    }
    
    // devuelve la instancia del modelo de la lista
    public UserList getListManager(){
        return listManager;
    }
    
    // setea el modelo de la lista para un determinado momento
    public void setListManager(UserList u){
        this.listManager = u;
    }
    
    // carga la vista con una lógica de botones según se haya logrado una conexión exitosa o no.
    // si la conexión fue exitosa, se inicia la lista desde la base de datos y se cargan los datos en la tabla.
    public void loadView(){
        m = new MainForm();
        if(buttonLogic(loader.bdConnect())){
            listManager = new UserList(loader.loadListFromDataBase());
            loadTable();
        }
    }
    
    // Setea los botones según la conexion fue true o false. 
    public boolean buttonLogic(boolean loaded){
        if(loaded){
            m.getCreateButton().setEnabled(false);
            m.getAddButton().setEnabled(true);
            m.getDeleteButton().setEnabled(true);
            m.getEditButton().setEnabled(true);
            return true;
        }
        else{
            m.getCreateButton().setEnabled(true);
            m.getAddButton().setEnabled(false);
            m.getDeleteButton().setEnabled(false);
            m.getEditButton().setEnabled(false);
            return false;
        }
    }
    
    // Carga los datos de los usuarios en lista en la tabla del formulario
    public void loadTable(){  
        DefaultTableModel model = (DefaultTableModel) m.getMainTable().getModel();
        model.setRowCount(0);
        for(User u : listManager.getList()){
            model.addRow(new String[]{String.valueOf(u.getId()),u.getUsername(),u.getPassword(),u.getEmail(),u.getBirth()});
        }
    }
    
    //Devuelve el username que se encuentra en la posicion que el usuario ha seleccionado
    public String getNameValueInSelectedRow(){
        if(m.getMainTable().getSelectedRow()==-1){
            return null;
        }
        return (String)m.getMainTable().getValueAt(m.getMainTable().getSelectedRow(), 1); 
    }
}
