/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.dbManager;

import control.FileManager;
import control.MainController;
import control.constants.Constants;
import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.User;
import model.UserList;

/**
 * @author Martin Ramonda
 */
public class SqliteLoader {
    
    private String connectPath;
    
    private FileManager fileManager;
    
    private Connection c;
    
    public SqliteLoader(){
        fileManager = new FileManager();
        connectPath = Constants.jdbc+fileManager.readConfigPath();
    }
    
    public void setPath(String path){
        this.connectPath=path;
    }
    
    public boolean bdConnect(){
        try{
            if(fileManager.readConfigPath()==null || !new File(fileManager.readConfigPath()).exists()){
                throw new SQLException();
            }
            System.out.println(new File(fileManager.readConfigPath()).exists());
            c = DriverManager.getConnection(connectPath);
            JOptionPane.showMessageDialog(null, "BD CONECTADA", "OK", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR BD", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public boolean bdConnect(String path){
        try{
            this.setPath("jdbc:sqlite:"+path);
            c = DriverManager.getConnection(connectPath);
            fileManager.writeDefaultPath(path);
            MainController._instance.setListManager(new UserList(loadListFromDataBase()));
            createTableStatement(c);
            JOptionPane.showMessageDialog(null, "BD CONECTADA", "OK", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR BD", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public void createTableStatement(Connection c) throws SQLException{
        Statement statement = c.createStatement();
        String createTable = "CREATE TABLE IF NOT EXISTS users ("
                + "userId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "email TEXT,"
                + "birthDate TEXT);";
        statement.execute(createTable);
    }
    
    public ArrayList<User> loadListFromDataBase(){
        String selectQuery = "SELECT * FROM users";
        ArrayList<User> userList = new ArrayList<>();
        try {
            c = DriverManager.getConnection(connectPath);
            Statement s = c.createStatement();
            s.execute(selectQuery);
            ResultSet result = s.getResultSet();
            while(result.next()){
                userList.add(new User(result.getInt("userId"),result.getString("username"),
                        result.getString("password"),result.getString("email"),result.getString("birthDate")));
            }
            return userList;
        } catch (SQLException ex) {
            System.out.println("Error sql al iniciar la lista");
            return null;
        }
    }
    
    public boolean insertUserStatment(String username, String password, String email, String birthDate){
        String insert = "INSERT INTO users(username,password,email,birthDate) "
                    + "VALUES(\""+username+"\", \""+password+"\", \""+email+"\", \""+birthDate+"\");";
        try {
            c = DriverManager.getConnection(connectPath);
            Statement s = c.createStatement();
            return s.execute(insert);
        } catch (SQLException ex) {
            System.out.println("Error sql al insertar datos");
            return false;
        } 
    }
    
    public void updateUserStatment(User u,String username, String password, String email, String birthDate){
        String update = "UPDATE users SET username = \""+username+"\", password = \""+password+"\","
                + "email = \""+email+"\", birthDate = \""+birthDate+"\" WHERE userId = "+u.getId();
        try {
            c = DriverManager.getConnection(connectPath);
            Statement s = c.createStatement();
            s.execute(update);
        } catch (SQLException ex) {
            System.out.println("Error sql al actualizar");
        } 
    }
    
    public void removeUserStatment(User u){
        String delete = "DELETE FROM users WHERE userId = "+u.getId()+";";
        try {
            c = DriverManager.getConnection(connectPath);
            Statement s = c.createStatement();
            s.execute(delete);
        } catch (SQLException ex) {
            System.out.println("Error sql al actualizar");
        } 
    }
}
