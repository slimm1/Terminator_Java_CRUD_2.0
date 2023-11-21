package control;

import control.constants.Constants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Ramonda
 */
public class FileManager {
    
    private File configFile;
    
    public FileManager(){
        configFile = new File(Constants.configFilePath);
        createDir();
    }
    
    public void createDir(){
        if(!configFile.exists()){
            try {
                new File("./config").mkdir();
                System.out.println(configFile.createNewFile());
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String readConfigPath(){
        Scanner in=null;
        if(configFile.length()==0){return null;}
        else{
            try {
                in = new Scanner(configFile);
                return in.nextLine();
            }
            catch (FileNotFoundException ex) {
                System.out.println("Error de lectura config.txt");
                return null;
            }
            finally{
                in.close();
            }
        }
    }
    
    public void writeDefaultPath(String toWrite){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(configFile,false));
            out.write(toWrite);
            out.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero");
        }
    }
}