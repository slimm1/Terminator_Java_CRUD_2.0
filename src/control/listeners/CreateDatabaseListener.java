package control.listeners;

import control.MainController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * @author Martin Ramonda
 */
public class CreateDatabaseListener implements ActionListener{
   
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser f = new JFileChooser();
        f.showSaveDialog(((JButton)e.getSource()).getParent().getParent());
        File dir = f.getSelectedFile();
        MainController._instance.buttonLogic(MainController._instance.getDbLoader().bdConnect(dir.getAbsolutePath()+".db"));
    }
    
}
