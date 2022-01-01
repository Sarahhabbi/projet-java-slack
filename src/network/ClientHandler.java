package network;

import front.IHM;
import models.ChannelUser;
import models.User;
import models.Message;
import service.ChannelService;
import service.UserService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientHandler implements Runnable {

    private static List<ClientHandler> clientHandlers = Collections.synchronizedList(new ArrayList<>());
    private Socket socket;


    private BufferedReader bufferedReader; //read message
    private PrintWriter writer; //broadcast(write) messages to other clients
    private String clientUsername;
    private String joinedChannel;

    private static UserService userService = new UserService();
    private static ChannelService channelService= new ChannelService();

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);

            this.clientUsername = bufferedReader.readLine();  // username is sent in sendMessage() method in Client class
            this.clientHandlers.add(this);
            this.joinedChannel = null;

            System.out.println("SERVER: " + clientUsername + " is connected to the server !");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, writer);
            e.printStackTrace();
        }
    }
    public void setJoinedChannel(String joinedChannel) {
        this.joinedChannel = joinedChannel;
    }

//    everything in this method will be run in a separate thread, we will listen to new messages (blocking operation)
    @Override
    public void run() {
        /* handle client command & send messages*/
        try {
            sendMessage();
        } catch (Exception e) {
            closeEverything(this.socket, this.bufferedReader, this.writer);
            e.printStackTrace();
        }
    }

    public void handleClientChoice(String feature, String arg) throws Exception {
        switch(feature){

            case "signUp":
                System.out.println(clientUsername + " want to signUp");
                this.signUp(arg);
                break;

            case "logIn":
                System.out.println(clientUsername + " want to logIn");
                this.logIn(arg);
                break;

            case "create":
                System.out.println(clientUsername + " want to create");
                this.createChannel(arg);
                break;

            case "join":
                System.out.println(clientUsername + " want to join");
                this.joinChannel(arg);
                break;
            case "delete":
                System.out.println(clientUsername + " want to delete");
                channelService.deleteChannel(arg,clientUsername);
                break;
            case "exit":
                System.out.println(clientUsername + " want to exit");
//                TODO: exeption quand on veut exit -> le client utilise ses stream dans listenMessage() mais ils ont été fermé donc il faut fermer les thread aussi ?
                closeEverything(this.socket, this.bufferedReader, this.writer);
                break;
            default:
                break;
        }
    }

    public void joinChannel(String arg) {
        List<Message> messages = null;
        try {
            messages = channelService.joinChannel(arg,clientUsername);
            for (Message m : messages){
                writer.println(m.getCreator() + ": "+ m.getText());
            }
            setJoinedChannel(arg);
            broadcastMessage( clientUsername+ " has entered "+ joinedChannel, joinedChannel);
        } catch (Exception e) {
            System.out.println("joinChannel debugging ClientHandler");
            writer.println(e.getMessage()); // renvoie la réponse au client
            e.printStackTrace();
        }
    }

    public synchronized void createChannel(String arg) {
        try {
            channelService.createChannel(arg, clientUsername);
            setJoinedChannel(arg);
            broadcastMessage(clientUsername+ " has entered "+ joinedChannel, joinedChannel);
            this.writer.println("Congrats you are now admin of "+ arg+ ", share the name to your friends to join !");
        }catch(Exception e){
            System.out.println("createChannel debugging");
            this.writer.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void signUp(String password) {
        try {
            userService.signUp(clientUsername, password);
            this.writer.println("Congrats you are now members of Slack, try create a channel for more fun");
        } catch (Exception e) {
            System.out.println("signUp debugging ClientHandler");
            writer.println(e.getMessage()); // renvoie la réponse au client
            e.printStackTrace();
        }
    }
    public void logIn(String password) {
        try {
            userService.connect(clientUsername, password);
            this.writer.println("welcome back to Slack "+ clientUsername+", good to see you ! ");

        } catch (Exception e) {
            System.out.println("logIn debugging ClientHandler");
            writer.println(e.getMessage());  //pour renvoyer la réponse au client
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        String messageFromClient = null;

        while(this.socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine(); // blocking operation but here it's in another thread so no problem
                String[] words = messageFromClient.split(" ");

                if(words[0].equals("/")){
                    handleClientChoice(words[1],words[2] );
                    System.out.println("handling client choice");
                }else if(joinedChannel!=null){
                    broadcastMessage(clientUsername+": " +messageFromClient, joinedChannel);

                    //create message m
                    Message message = new Message(messageFromClient,clientUsername, joinedChannel);
                    // add to database and memory cache
                    channelService.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }  // ICI
        closeEverything(this.socket, this.bufferedReader, this.writer);
    }

    public void broadcastMessage(String messageToSend, String channel) {
        System.out.println(">>> broadcastMessage() "+ messageToSend);

        synchronized(clientHandlers){
            /* iterate over each client and write in every output stream the message except to the client who sent */
            for(ClientHandler clientHandler : clientHandlers){
                try{
                    boolean sameChannel = clientHandler.joinedChannel.equals(this.joinedChannel);
                    boolean sameUser = clientHandler.clientUsername.equals(this.clientUsername);
                    if(!sameUser && sameChannel){
                        clientHandler.writer.println(messageToSend);
                    }
                } catch (Exception e) {
                    closeEverything(this.socket, this.bufferedReader, this.writer);
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+ clientUsername+ " has left "+ joinedChannel, joinedChannel);
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

    public static void closeEverySocket(){
        for(ClientHandler clientHandler : clientHandlers){
            clientHandler.closeEverything(clientHandler.socket, clientHandler.bufferedReader, clientHandler.writer);
        }
    }
}
