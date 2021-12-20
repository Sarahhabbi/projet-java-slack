package front;

import models.User;
import service.UserService;

import java.util.Scanner;

public class IHM {
    private static UserService userService = new UserService();

    private static User menuLoginAndSignup() {
        String pseudo;
        String password;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your pseudo");
        pseudo = input.nextLine();
        System.out.println("Enter your password");
        password = input.nextLine();

        User user = new User(pseudo, password);
        return user;
    }

    private static void logIn() {
        User user = menuLoginAndSignup();
        try {
            userService.connect(user.getPseudo(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            menu();
        }
    }
    private static void signIn() {
        User user = menuLoginAndSignup();
        try {
            userService.signUp(user.getPseudo(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            menu();
        }
    }


    public static int menu() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Sign In");
        System.out.println("2 - Log In");
        System.out.println("3 - Quit");

        selection = input.nextInt();
        return selection;
    }

    public static void matchChoice(int selection){
        switch(selection){
            case 1:
                signIn();
                break;
            case 2:
                logIn();
            default:
                System.out.println("Enter a correct choice");
        }
    }

    public static void main(String[] args){
        int userChoice;

        userChoice = menu();

        while(userChoice != 3){
            userChoice = menu();
            matchChoice(userChoice);
        }
        return;
    }
}
