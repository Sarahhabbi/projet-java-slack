package front;

import models.User;
import network.Server;
import service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class IHM {
    private static Server server;
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


    public static String menu() {
        String selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Sign Up");
        System.out.println("2 - Log In");
        System.out.println("3 - Quit");

        selection = input.nextLine();
        return selection;
    }

    public static void matchChoice(String selection){
        switch(selection){
            case "1":
                signIn();
                break;
            case "2":
                logIn();
                break;
            case "3":
                break;
            default:
                System.out.println("Enter a correct choice");
                matchChoice(IHM.menu());
        }
    }

    public static void main(String[] args){
/*

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        String selection;
        Scanner input = new Scanner(System.in);

        System.out.println("Choose your command");
        System.out.println("1 - / signUp password");
        System.out.println("2 - / logIn password");
        System.out.println("3 - / create #myNewChannel ");
        System.out.println("4 - / join #myNewChannel ");
        System.out.println("5 - / delete #myNewChannel");
        System.out.println("6 - / exit slack");

        selection = input.nextLine();
        String[] words = selection.split(" ");

    }
}
