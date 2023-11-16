package model;

import java.util.ArrayList;

/**
 *
 * @author Martin Ramonda
 */
public class UserList {
    
    private ArrayList<User> userList;
    
    public UserList(ArrayList<User> userList){
        this.userList=userList;
        if(userList==null){
            this.userList = new ArrayList<>();
        }
    }
    
    public ArrayList<User> getList(){
        return userList;
    }
    
    public void setList(ArrayList<User> newList){
        this.userList = newList;
    }
    
    public User getUserByName(String name){
        for(User u: userList){
            if(u.getUsername().equalsIgnoreCase(name)){
                return u;
            }
        }
        return null;
    }
}
