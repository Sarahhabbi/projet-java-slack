package network;


import models.Message;
import service.ChannelService;
import service.UserService;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
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
    private boolean isAuthenticated;

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
            this.isAuthenticated = false;

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

    public synchronized void createChannel(String arg,int isPrivate) {
        try {
            channelService.createChannel(arg, clientUsername,isPrivate);
            setJoinedChannel(arg);
            broadcastMessage(clientUsername+ " has entered "+ joinedChannel, joinedChannel);
            this.writer.println("Congrats you are now admin of "+ arg+ ", share the name to your friends to join !");
            if(isPrivate==1){
                this.writer.println("You need to add member then you can invite them by sharing the channel name!");
            }else{
                this.writer.println("You can invite them by sharing the channel name!");
            }
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
            this.isAuthenticated = true;
        } catch (Exception e) {
            System.out.println("signUp debugging ClientHandler");
            writer.println(e.getMessage()); // renvoie la réponse au client
            e.printStackTrace();
        }
    }

    public void addMember(String channel){
        this.writer.println("Please put a pseudo");
        try {
            String pseudo= bufferedReader.readLine();
            channelService.addMemberChannel(channel,clientUsername,pseudo);
            this.writer.println(pseudo+" ajouté");
        }catch (IOException ie) {
            ie.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(String channel){
        this.writer.println("Please put a pseudo");
        try {
            String pseudo= bufferedReader.readLine();
            channelService.deleteMemberChannel(channel,clientUsername,pseudo);
            this.writer.println(pseudo+" retiré");
        }catch (IOException ie) {
            ie.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logIn(String password) {
        try {
            userService.connect(clientUsername, password);
            this.writer.println("welcome back to Slack "+ clientUsername+", good to see you ! ");
            this.isAuthenticated = true;
        } catch (Exception e) {
            System.out.println("logIn debugging ClientHandler");
            writer.println(e.getMessage());  //pour renvoyer la réponse au client
            e.printStackTrace();
        }
    }

    public void displayConnectedMembers(String arg){
        synchronized(clientHandlers){
            /* iterate over each client and write in every output stream the message except to the client who sent */                    ArrayList<ClientHandler> members = new ArrayList<>();
            ArrayList<ClientHandler> member = new ArrayList<>();
            writer.println("Currently connected to " + arg);
            for(ClientHandler clientHandler : clientHandlers){
                try{
                    if(this.joinedChannel!=null && clientHandler.joinedChannel.equals(arg) && this.joinedChannel.equals(arg) && this.clientUsername!=null){
                        writer.println(clientHandler.clientUsername);
                    }
                } catch (Exception e) {
                    closeEverything(this.socket, this.bufferedReader, this.writer);
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(){
        String messageFromClient = null;

        while(this.socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine(); // blocking operation but here it's in another thread so no problem
                if(messageFromClient!=null){
                    String[] words = messageFromClient.split(" ");

                    if(words[0].equals("/")){
                        handleClientChoice(words[1],words[2] );
                        System.out.println("handling client choice");
                    }
                    else if(joinedChannel!=null){
                        broadcastMessage(clientUsername+": " +messageFromClient, joinedChannel);

                        //create message m
                        Message message = new Message(messageFromClient,clientUsername, joinedChannel);
                        // add to database and memory cache
                        channelService.sendMessage(message);
                    }
                }
            } catch (SocketException e) {
                System.out.println(clientUsername+" is gone");
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }  // ICI
    }

    public void broadcastMessage(String messageToSend, String channel) {
        System.out.println(">>> broadcastMessage() "+ messageToSend);

        synchronized(clientHandlers){
            /* iterate over each client and write in every output stream the message except to the client who sent */
            for(ClientHandler clientHandler : clientHandlers){
                try{
                    if(this.joinedChannel!=null && this.clientUsername!=null && clientHandler.joinedChannel != null && clientHandler.clientUsername!=null){
                        boolean sameChannel = clientHandler.joinedChannel.equals(this.joinedChannel);
                        boolean sameUser = clientHandler.clientUsername.equals(this.clientUsername);
                        if(!sameUser && sameChannel){
                            clientHandler.writer.println(messageToSend);
                        }
                    }
                } catch (Exception e) {
                    closeEverything(this.socket, this.bufferedReader, this.writer);
                    e.printStackTrace();
                }
            }
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
                if(this.isAuthenticated == true){
                    System.out.println(clientUsername + " want to create");
                    this.createChannel(arg,0);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;
            case "createPrivate":
                if(this.isAuthenticated == true){
                    System.out.println(clientUsername + " want to create private channel");
                    this.createChannel(arg,1);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;
            case "addMember":
                if(this.isAuthenticated == true){
                    System.out.println(clientUsername + " want to add member");
                    this.addMember(arg);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;
            case "deleteMember":
                if(this.isAuthenticated == true){
                    System.out.println(clientUsername + " want to delete member");
                    this.deleteMember(arg);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;
            case "join":
                if(this.isAuthenticated == true){
                    System.out.println(clientUsername + " want to join");
                    this.joinChannel(arg);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;

            case "delete":
                if(this.isAuthenticated==true){
                    System.out.println(clientUsername + " want to delete");
                    channelService.deleteChannel(arg,clientUsername);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;

            case "displayConnectedMembers":
                if(this.isAuthenticated==true){
                    System.out.println(clientUsername + " want to know members of "+ arg);
                    this.displayConnectedMembers(arg);
                }else{
                    writer.println("You must logIn or SignUp first !");
                }
                break;
            case "exit":
                System.out.println(clientUsername + " want to exit");
                closeEverything(this.socket, this.bufferedReader, this.writer);
                break;
            default:
                break;
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
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
