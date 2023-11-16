/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.listeners;

import control.DataController;
import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import view.DataForm;

/**
 *
 * @author Martin Ramonda
 */
public class AddEditListener implements ActionListener{

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
