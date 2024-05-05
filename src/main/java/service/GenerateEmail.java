package service;

public class GenerateEmail {


    private static final String domainName = "@vivianne.com";

    public static String email(String firstName, String lastName){
        return generateEmail(firstName,lastName);
    }


    private static String generateEmail(String firstName, String lastName) {
        StringBuilder email = new StringBuilder();
        email.append(firstName);
        email.append(lastName);
        email.append(domainName);
        return email.toString();

    }




}
