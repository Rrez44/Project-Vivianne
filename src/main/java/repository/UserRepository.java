package repository;

import databaseConnection.DatabaseUtil;
import model.User;
import service.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {


    public static boolean insertSuperAdmin(){

        String firstName = "superAdmin";
        String lastName = "superAdmin";
        String username = "superAdmin";
        String email = "superAdmin@gmail.com";
        String salt = PasswordHasher.generateSalt();
        String password = "superAdmin";
        String hashPassword = PasswordHasher.generateSaltedHash(password,salt);
        String role = "superAdmin";

        String query = "insert into users(firstName,lastName,username,email,salt,passwordHash,role) values(?,?,?,?,?,?,?)";
        Connection connection =DatabaseUtil.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, salt);
            preparedStatement.setString(6, hashPassword);
            preparedStatement.setString(7, role);
            preparedStatement.execute();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }






    public static User getByUsername(String username){
        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";
        Connection connection = DatabaseUtil.getConnection();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                return getFromResultSet(result);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    private static User getFromResultSet(ResultSet result){
        try{
            int id = result.getInt("id");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String username = result.getString("userName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            String role = result.getString("role");

            return new User(
                    id, firstName, lastName,username, email, salt, passwordHash,role
            );
        }catch (Exception e){
            return null;
        }
    }







}