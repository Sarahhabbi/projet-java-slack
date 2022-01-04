package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader; //read message
    private PrintWriter writer; //broadcast(write) messages to other clients
    private String username;

    public Client(Socket socket, String username) {

        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader,  writer);
            e.printStackTrace();
        }
    }


    public void sendMessage(){
        try{
            writer.println(username); // le user écrit d'abord son nom dans son output stream avant d'envoyer des messages
            // send messages
            String messageToSend;
            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                messageToSend = scanner.nextLine();   // message from client
                writer.println(messageToSend);  // handled in ClientHandler
            } //ICI
        }catch(Exception e){
            closeEverything(socket, bufferedReader, writer);
            e.printStackTrace();
        }
    }

    //  blocking operation so need a thread
    public void listenForMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String messageFromChat;
                while(socket.isConnected()){
                    try{
                        messageFromChat = bufferedReader.readLine(); // read message broadcasted by CientHandler
                        System.out.println(messageFromChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, writer);
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Client client = null;
        try {
            // ask username
            Scanner scanner = new Scanner(System.in);
            System.out.println("------------ Welcome to Slack ! ------------");
            System.out.println("Enter your username:");
            String username = scanner.nextLine();

            // establish the connection with server port 1234
            Socket socket = new Socket("localhost", 8080);
            // create Client
            client = new Client(socket, username);

            System.out.println("--------- Enter a command with this format for any action: ---------");
            System.out.println("/ signUp password");
            System.out.println("/ logIn password");
            System.out.println("/ create #myNewChannel ");
            System.out.println("/ join #myNewChannel ");
            System.out.println("/ delete #myNewChannel");
            System.out.println("/ exit slack");

            client.listenForMessage();
            client.sendMessage();

        } catch (Exception e) {
            e.printStackTrace();
            client.closeEverything(client.socket, client.bufferedReader, client.writer);
        }
    }
}
