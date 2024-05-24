package service;

import model.CreateUser;
import model.User;
import model.dto.ChangePassword;
import model.dto.LoginUserDto;
import model.dto.UpdateUser;
import model.dto.UserDto;
import repository.UserRepository;

import java.util.List;

public class UserService {

    public static boolean signUP(UserDto user) {

        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        if(!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return false;
        }

        String salt = PasswordHasher.generateSalt();
        String hashedPassword= PasswordHasher.generateSaltedHash(user.getPassword(),salt);


        CreateUser createUser = new CreateUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                salt,
                hashedPassword,
                user.getRole()
        );

        return UserRepository.insertUser(createUser);

    }

    public static int checkDuplicate(String firstName,String lastName) {
        return UserRepository.checkDuplicate(firstName, lastName);

    }

    public static void insertSuperAdmin(){

        if(UserRepository.insertSuperAdmin()){
            System.out.println("Super admin added");
        }else{
            System.out.println("Super admin not added");
        }
    }

    public static User getUser(String username){
        return UserRepository.getByUsername(username);

    }



    public static boolean login(LoginUserDto loginData) {
        User user = UserRepository.getByUsername(loginData.getUsername());
        if (user == null) {
            return false;
        }

        String password = loginData.getPassword();
        String salt = user.getSalt();
        String passwordHash = user.getHashedPassword();
        return PasswordHasher.compareSaltedHash(
                password, salt, passwordHash
        );
    }


    public static void deleteUser(String username){
        UserRepository.deleteAdmin(username);
    }


    public static List<User> getAllUsers(String loggedUser){
        return  UserRepository.getAllAdminUsers(loggedUser);
    }

    public static void updateUser(ChangePassword changePassword) {
        try {
            User user = UserRepository.getById(changePassword.getId());
            if (user == null) {
                System.out.println("User not found");
                return;
            }

            String currentPassword = changePassword.getCurrentPassword();
            String storedSalt = user.getSalt();
            String storedHashedPassword = user.getHashedPassword();

            if (!PasswordHasher.compareSaltedHash(currentPassword, storedSalt, storedHashedPassword)) {
                System.out.println("Current password is incorrect");
                return;
            }

            if (!changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
                return;
            }

            String newSalt = PasswordHasher.generateSalt();
            String newHashedPassword = PasswordHasher.generateSaltedHash( changePassword.getNewPassword(),newSalt);


            UserRepository.updatePassword(new UpdateUser(changePassword.getId(), newSalt, newHashedPassword));
            System.out.println("Password updated successfully");

        } catch (Exception e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

}
