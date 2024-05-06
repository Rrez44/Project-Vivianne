package service;

public class GenerateEmail {


    private static final String domainName = "@vivianne.com";

    public static String email(String firstName, String lastName,int numberOfUsers){
        return generateEmail(firstName,lastName,numberOfUsers);
    }


    private static String generateEmail(String firstName, String lastName,int numberOfUsers) {
        StringBuilder email = new StringBuilder();
        email.append(firstName);
        email.append(lastName);
        if(numberOfUsers ==0){
            email.append(domainName);

        }else{
            email.append(numberOfUsers).append(domainName);
        }

        return email.toString();

    }




}
