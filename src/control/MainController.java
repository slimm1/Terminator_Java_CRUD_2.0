/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dbManager.SqliteLoader;
import javax.swing.table.DefaultTableModel;
import model.User;
import model.UserList;
import view.MainForm;

/**
 * @author Martin Ramonda
 */
public class MainController {
    
    private MainForm m;
    private SqliteLoader loader;
    private UserList listManager;
    
    public static MainController _instance = new MainController();
    
    private MainController(){
        loader = new SqliteLoader();
    }
    
    public MainForm getForm(){
        return m;
    }
    
    public SqliteLoader getDbLoader(){
        return loader;
    }
    
    public UserList getListManager(){
        return listManager;
    }
    
    public void loadView(){
        m = new MainForm();
        listManager = new UserList(loader.loadListFromDataBase());
        buttonLogic(loader.bdConnect());
    }
    
    public void buttonLogic(boolean loaded){
        if(loaded){
            m.getCreateButton().setEnabled(false);
            m.getAddButton().setEnabled(true);
            m.getDeleteButton().setEnabled(true);
            m.getEditButton().setEnabled(true);
            loadTable();
        }
        else{
            m.getCreateButton().setEnabled(true);
            m.getAddButton().setEnabled(false);
            m.getDeleteButton().setEnabled(false);
            m.getEditButton().setEnabled(false);
        }
    }
    
    public void loadTable(){  
        DefaultTableModel model = (DefaultTableModel) m.getMainTable().getModel();
        model.setRowCount(0);
        for(User u : listManager.getList()){
            model.addRow(new String[]{String.valueOf(u.getId()),u.getUsername(),u.getPassword(),u.getEmail(),u.getBirth()});
        }
    }
    
    public String getNameValueInSelectedRow(){
        if(m.getMainTable().getSelectedRow()==-1){
            return null;
        }
        return (String)m.getMainTable().getValueAt(m.getMainTable().getSelectedRow(), 1); 
    }
}
