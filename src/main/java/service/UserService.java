package service;

import model.User;
import model.dto.LoginUserDto;
import repository.UserRepository;

public class UserService {


    public static void insertSuperAdmin(){

        if(UserRepository.insertSuperAdmin()){
            System.out.println("Super admin added");
        }else{
            System.out.println("Super admin not added");
        }
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
}
