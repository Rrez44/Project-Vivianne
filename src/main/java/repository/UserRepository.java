package repository;

import ENUMS.Role;
import INTERFACES.Identifiable;
import databaseConnection.DatabaseUtil;
import model.CreateUser;
import model.User;
import model.dto.ChangePassword;
import model.dto.UpdateUser;
import service.PasswordHasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserRepository  {


    public static boolean insertUser(CreateUser user)  {

        String query = "INSERT INTO users(user_id,first_name,last_name,username,email,salt,hashed_password,role) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseUtil.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getId());
            preparedStatement.setString(2,user.getFirst_name());
            preparedStatement.setString(3,user.getLast_name());
            preparedStatement.setString(4,user.getUsername());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getSalt());
            preparedStatement.setString(7,user.getPasswordHash());
            preparedStatement.setString(8,user.getRole().name());
            preparedStatement.execute();
            preparedStatement.close();

            return true;

        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }




    public static boolean insertSuperAdmin(){

        String firstName = "superAdmin";
        String lastName = "superAdmin";
        String username = "superAdmin";
        String email = "superAdmin@gmail.com";
        String salt = PasswordHasher.generateSalt();
        String password = "superAdmin";
        String hashPassword = PasswordHasher.generateSaltedHash(password,salt);
        String role = "SUPER_ADMIN";

        String finalId =UUID.randomUUID().toString().replace("-", "");


        String query = "insert into users(user_id,first_name,last_name,username,email,salt,hashed_password,role) values(?,?,?,?,?,?,?,?)";
        Connection connection =DatabaseUtil.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,finalId);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,username);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,salt);
            preparedStatement.setString(7,hashPassword);
            preparedStatement.setString(8,role);
            preparedStatement.execute();
//            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }






    public static User getByUsername(String username){
        String query = "SELECT * FROM users WHERE username = ?";
        Connection connection = DatabaseUtil.getConnection();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                return getFromResultSet(result);
            }


            return null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static User getById(String Id){
    String query = "SELECT * FROM users WHERE user_id = ?";
    Connection connection = DatabaseUtil.getConnection();
    try{
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, Id);
        ResultSet result = pst.executeQuery();
        if(result.next()){
            return getFromResultSet(result);
        }
        return null;
    }catch (SQLException e){
        System.out.println(e.getMessage());
        return null;
        }
    }
    public static String getRole(String username){
        String query = "SELECT role FROM users WHERE username = ?";
        Connection connection = DatabaseUtil.getConnection();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                return result.getString(1);
            }


            return null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int checkDuplicate(String firstName,String lastName){
        String query = "SELECT count(user_id) as user_counted FROM users WHERE first_name = ? AND last_name = ?";
        Connection connection = DatabaseUtil.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                int hasDuplicate = result.getInt("user_counted");

                return  hasDuplicate;

           }

            return 0;
        }catch (SQLException e){
            return 0;
        }
    }

    public static void deleteAdmin(String username){

        String query = "DELETE FROM users WHERE username = ?";

        Connection connection = DatabaseUtil.getConnection();

        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.execute();
            System.out.println("Deleted admin");
        }catch (SQLException e){
            System.out.println(e);
        }

    }




    private static User getFromResultSet(ResultSet result){
        try{
            String user_id = result.getString("user_id");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String username = result.getString("username");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("hashed_password");
            Role role = Role.valueOf(result.getString("role"));

            return new User(
                    user_id, firstName, lastName,username, email, salt, passwordHash,role
            );
        }catch (Exception e){
            System.out.println(e.getMessage())  ;
            return null;
        }
    }

    public static List<User> getAllAdminUsers(String loggedUser){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users where  username !=?";
        Connection connection = DatabaseUtil.getConnection();

        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, loggedUser);
            ResultSet result = pst.executeQuery();

            while(result.next()){
                users.add(getFromResultSet(result));
            }
            return users;
        }catch (SQLException e){
            System.out.println(e);
        }

        return null;

    }


    public static void updatePassword(UpdateUser updateUser){

        String query = "UPDATE users SET hashed_password = ?,salt=? WHERE user_id = ?";
        Connection connection = DatabaseUtil.getConnection();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, updateUser.getPasswordHash());
            pst.setString(2,updateUser.getSalt());
            pst.setString(3,updateUser.getId());
            pst.execute();
            System.out.println("Password updated");

        }catch (SQLException e){
            System.out.println(e);
        }





    }






}
