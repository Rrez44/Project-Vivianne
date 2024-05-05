package service;

import java.sql.Struct;

public class Session {

    private static Session session =null;
    private  String username;
    private  String role;


    private Session(){
    }


    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }
        return session;
    }



    public void logOut(){
        session = null;
        username = null;
        role = null;
    }




    public String getUserName() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
