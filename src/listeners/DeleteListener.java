/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import control.DataController;
import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author Vespertino
 */
public class DeleteListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainController._instance.getNameValueInSelectedRow()==null){
            JOptionPane.showMessageDialog(null, "You must select an user to edit", "NO field SELECTED", JOptionPane.ERROR_MESSAGE);
        }
        else{
            deleteUser();
        }
    }
    
    public void deleteUser(){
        User selectedUser = MainController._instance.getListManager().getUserByName(MainController._instance.getNameValueInSelectedRow());
        MainController._instance.getDbLoader().removeUserStatment(selectedUser);
        MainController._instance.getListManager().setList(MainController._instance.getDbLoader().loadListFromDataBase());
        MainController._instance.loadTable();
        JOptionPane.showMessageDialog(null, "User deleted succesfully", "USER DELETED on DB", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
