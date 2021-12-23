package handlers;


import models.User;
import service.UserService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    public static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private String clientPseudo = null;
    private UserService userService;


    // Constructor
    public ClientHandler(Socket s) throws IOException {
        this.s = s;
        this.dis = new DataInputStream(this.s.getInputStream());
        this.dos = new DataOutputStream(this.s.getOutputStream());

        this.userService = new UserService();
    }


    //Sign In
    public void signIn() throws IOException {

        this.dos.writeUTF("Enter your pseudo");
        String pseudo = dis.readUTF();
        dos.writeUTF("Enter your password");
        String password = dis.readUTF();

        User user = new User(pseudo, password);
        try {
            userService.signUp(user.getPseudo(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.clientPseudo = pseudo;
        this.start();

    }

    // Log In
    public void logIn() throws IOException {
        dos.writeUTF("Enter your pseudo");
        String pseudo = dis.readUTF();
        dos.writeUTF("Enter your password");
        String password = dis.readUTF();

        User user = new User(pseudo, password);
        try {
            userService.connect(user.getPseudo(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.clientPseudo = pseudo;
        this.start();
    }

    public void broadcast(String message){
        try{
//          iterate over each client and send everyone the message except to the client who sent it
            for(ClientHandler client : clients){
                if(!client.getClientPseudo().equals(this.clientPseudo)){
                    client.dos.writeUTF(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClientPseudo() {
        return this.clientPseudo;
    }

    @Override
    public void run()
    {
        String messageFromClient;

        while (true) {
            try {
                messageFromClient = dis.readUTF();
                broadcast(messageFromClient);

            } catch (IOException e) {
                //e.printStackTrace(); //Ca fait une boucle infini avec "ctrl+C"
                try {
                    this.s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void writeMenuToClient() throws IOException {
        // Ask user what he wants
        this.dos.writeUTF("Choose from these choices\n----------------------------------" +
                "\n1 - Sign In" +
                "\n2 - Log In" +
                "\n Quit");
    }

    public void quit() throws IOException {
        System.out.println("Client " + this.s + " sends exit...");
        System.out.println("Closing this connection.");
        this.s.close();
        System.out.println("Connection closed");
    }

    public void handleClientChoice() {
        try{
            String choice = dis.readUTF();

            if(choice.equals("Quit")){
                this.quit();
            }

            // write on output stream based on the
            // answer from the client
            switch(choice){

                case "1":
                    this.signIn();
                    break;

                case "2":
                    this.logIn();
                    break;

                default:
                    dos.writeUTF("Invalid input");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}