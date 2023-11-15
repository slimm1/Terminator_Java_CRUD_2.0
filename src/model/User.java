/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;



/**
 *
 * @author Martin Ramonda
 */
public class User {
    
    private int id;
    private String username;
    private String password;
    private String birth;
    private String email;
    
    public User(int id, String username, String password, String email, String birth){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birth = birth;
    }
    
    public int getId(){
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
         if(obj == null){return false;}
         if(!(obj instanceof User)){return false;}
         User u = (User)obj;
         if(u.getId()==this.id || u.getUsername().equalsIgnoreCase(this.username)){return true;}
         return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }   
    
}
