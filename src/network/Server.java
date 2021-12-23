package network;

import handlers.ClientHandler;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket serverSocket;


    public Server(ServerSocket serverSocket) throws IOException {
        System.out.println("Creating a server on port "+ serverSocket.getInetAddress().getHostAddress());
        this.serverSocket = serverSocket;
    }

    public void startServer() throws IOException {
        try{
            while(true){
                System.out.println("Waiting a connection ...");
                Socket socket = serverSocket.accept();

                System.out.println("A client is connected from "+ socket.getInetAddress().getHostAddress());

                // create a new thread object
                Thread t = new ClientHandler(socket);
                System.out.println("A new thread was assigned  this client");

                ((ClientHandler) t).writeMenuToClient();
                // Invoking the start() method
                // t.start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(1234);
        Server server = new Server(ss);
        while(true) {
            try
            {
               server.startServer();
            }
            catch (Exception e){
                server.close();
                e.printStackTrace();
            }
        }
    }
}
