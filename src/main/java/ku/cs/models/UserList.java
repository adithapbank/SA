package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//user ทั้งหมด
public class UserList {
    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }

    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getAllUsers(){
        return users;
    }


    public String toCsv() {
        String result = "";
        for (User user : users) {
            result += user.toCsv() + "\n";
        }
        return result;
    }

    public User SearchByUsername(String username){
        for(User user: users){
            if (user.checkUser(username)){
            return user;
            }
        }
        return null;
    }
}
