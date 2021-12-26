package network;

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
            e.printStackTrace();
        }
    }

//    everything in this method will be run in a separate thread, we will listen to new messages (blocking operation)
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
