package repository;

import ENUMS.Role;
import INTERFACES.Identifiable;
import databaseConnection.DatabaseUtil;
import model.CreateUser;
import model.User;
import service.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            connection.close();

            return true;

        }catch (SQLException e){
            System.out.println(e.getMessage());
//            System.out.println("Registering User Failed");
            return false;
        }

//        return false;
    }




    public static boolean insertSuperAdmin(){

        String firstName = "superAdmin";
        String lastName = "superAdmin";
        String username = "superAdmin";
        String email = "superAdmin@gmail.com";
        String salt = PasswordHasher.generateSalt();
        String password = "superAdmin";
        String hashPassword = PasswordHasher.generateSaltedHash(password,salt);
        String role = "superAdmin";
//        UUID id = UUID.randomUUID();
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
        }catch (SQLException e){
            System.out.println(e.getMessage());
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
            Role role = Role.valueOf(result.getString("role"));

            return new User(
                    id, firstName, lastName,username, email, salt, passwordHash,role
            );
        }catch (Exception e){
            return null;
        }
    }







}