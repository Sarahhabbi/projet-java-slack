package network;

<<<<<<< HEAD

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
=======
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader; //read message
    private PrintWriter writer; //broadcast(write) messages to other clients
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);

            this.clientUsername = bufferedReader.readLine();  // username is sent in sendMessage() method in Client class
            this.clientHandlers.add(this);
            this.broadcastMessage("SERVER: " + clientUsername + " has entered the chat !");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, writer);
>>>>>>> main
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

<<<<<<< HEAD
    //Join Channel
    /*public void JoinChannel(String name) throws Exception{
        if(this.currentUser==null){
            throw new Exception("Please sign in before create a channel");
        }
        userService.joinChannel
    }*/

=======
//    everything in this method will be run in a separate thread, we will listen to new messages (blocking operation)
>>>>>>> main
    @Override
    public void run() {
        String messageFromClient = null;

        do{
            try{
                messageFromClient = bufferedReader.readLine(); // blocking operation but here it's in another thread so no problem
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }while(messageFromClient!=null);

        closeEverything(socket, bufferedReader, writer);
    }

    public void broadcastMessage(String messageToSend) {
        System.out.println(">>> broadcastMessage() message =  "+ messageToSend);

        /* iterate over each client and write in every output stream the message except to the client who sent */
        for(ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(this.clientUsername)){
                    clientHandler.writer.println(messageToSend);
                }
            } catch (Exception e) {
                closeEverything(socket, bufferedReader, writer);
                e.printStackTrace();
            }
        }

    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+ clientUsername+ " has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, PrintWriter writer) {
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(writer != null){
                writer.close();
            }
            if(socket != null){
                socket.close();
            }
            removeClientHandler();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
