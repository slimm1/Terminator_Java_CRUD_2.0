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
 * Clase enlace entre la base de datos y la aplicación. Incluye herramientas para lanzar consultas, recuperar, insertar y actualizar
 * datos en la bd. 
 */
public class SqliteLoader {
    
    private String connectPath;
    
    private FileManager fileManager;
    
    private Connection c;
    
    // constructor instancia el fileManager e inicia la ruta de acceso a la base de datos
    public SqliteLoader(){
        fileManager = new FileManager();
        connectPath = Constants.jdbc+fileManager.readConfigPath();
    }
    
    // setea el path para esta instancia del programa. se usa cuando la ruta del fichero config no existe o no es correcta.
    public void setPath(String path){
        this.connectPath=path;
    }
    
    // true si se conectó o false si no se conectó.
    // si el fichero no existe o está vacío lanza excepción sql, es decir devuelve false.
    public boolean bdConnect(){
        try{
            if(fileManager.readConfigPath()==null || !new File(fileManager.readConfigPath()).exists()){
                throw new SQLException();
            }
            c = DriverManager.getConnection(connectPath);
            JOptionPane.showMessageDialog(null, "BD CONECTADA", "OK", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL CONECTAR BD", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // sobreescritura del método bdConnect para conectar la bd con una ruta diferente a la que proporciona esta clase. Realiza varias acciones:
    // setea el String de conexion para esta instancia e inicia la conexión a la base de datos.
    // Escribe la ruta de la base de datos en el fichero de configuración como ruta por defecto.
    // Inicia la lista de usuarios desde una instancia del controlador con una lista vacía.
    // Crea una nueva tabla en la base de datos si ésta no existe.
    // (Este método sólo se ejecuta cuando la primera conexión falla, es decir cuando se está creando una nueva base de datos)
    public boolean bdConnect(String path){
        try{
            this.setPath(Constants.jdbc+path);
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
    
    // Ejecuta una query para crear una nueva tabla users en la base de datos.
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
    
    
    // Carga un arrayList de users con los datos existentes en la base de datos. Cualquier error lanza excepción y devuelve null.
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
            return null;
        }
    }
    
    // Inserta los datos pasados por parámetros en la base de datos como un nuevo usuario. El id es autoincrementable en la bd
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
    
    // Update statment para un determinado usuario (u). Los datos recogidos en parámetros son los datos a actualizar en la bd. 
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
    
    // Elimina de la base de datos el usuario que coincida con el usuario pasado por params
    public void removeUserStatment(User u){
        String delete = "DELETE FROM users WHERE userId = "+u.getId()+";";
        try {
            c = DriverManager.getConnection(connectPath);
            Statement s = c.createStatement();
            s.execute(delete);
        } catch (SQLException ex) {
            System.out.println("Error sql al eliminar");
        } 
    }
}
