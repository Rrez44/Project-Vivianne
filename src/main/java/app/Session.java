package app;
import model.User;

public class
Session {
    private static User user;


    public static void setUser(User newUser){
        user = newUser;
    }
    public static User getUser(){
        return user;
    }
}