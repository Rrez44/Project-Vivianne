package service;

import model.CreateUser;
import model.User;
import model.dto.LoginUserDto;
import model.dto.UserDto;
import repository.UserRepository;

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

    public static void insertSuperAdmin(){

        if(UserRepository.insertSuperAdmin()){
            System.out.println("Super admin added");
        }else{
            System.out.println("Super admin not added");
        }
    }






    public static boolean login(LoginUserDto loginData) {
        User user = UserRepository.getByUsername(loginData.getUsername());
        System.out.println(loginData.getUsername());
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
}
