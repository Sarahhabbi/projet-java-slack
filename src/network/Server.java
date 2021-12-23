package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public ServerSocket serverSocket;
    public PrintWriter out;
    public BufferedReader in;
    public Socket socket;
    public static String file = "/Users/habbi/Documents/L3-DANT/PROJETS_GROUPE/ProjetSlack/projet-java-slack/src/data/messages";

    public Server(int port) throws IOException {
        System.out.println("Creating a server on port " + port);
        this.serverSocket = new ServerSocket(port);
    }

    public void initConnection() throws IOException {
        System.out.println("Waiting a connection ...");
        this.socket = serverSocket.accept();

        System.out.println("A client is connected from "+ socket.getInetAddress().getHostAddress());

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void close(){
        try {
            this.socket.close();
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}