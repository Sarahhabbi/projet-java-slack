package network;


import models.*;
import service.UserService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    public static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    private UserService userService;

    private User currentUser;
    private Channel currentChannel;

    // Constructor
    public ClientHandler(Socket s) throws IOException {
        this.s = s;
        this.dis = new DataInputStream(this.s.getInputStream());
        this.dos = new DataOutputStream(this.s.getOutputStream());

        this.userService = new UserService();

        this.currentUser=null;
        this.currentChannel=null;
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
        this.currentUser=user;
    }

    //Create Channel
    public void createChannel(String name)throws Exception{
        if(this.currentUser==null){
            throw new Exception("Please sign in before create a channel");
        }
        userService.createChannel(name,this.currentUser.getPseudo());
    }

    //Join Channel
    /*public void JoinChannel(String name) throws Exception{
        if(this.currentUser==null){
            throw new Exception("Please sign in before create a channel");
        }
        userService.joinChannel
    }*/

    @Override
    public void run()
    {
        String received;
        while (true) {
            try {

                // Ask user what he wants
                dos.writeUTF("Choose from these choices\n----------------------------------" +
                        "\n1 - Sign In" +
                        "\n2 - Log In" +
                        "\n Quit");

                // receive the answer from client
                received = dis.readUTF();

                if(received.equals("Quit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "1" :
                        this.signIn();
                        break;

                    case "2" :
                        this.logIn();
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
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
}