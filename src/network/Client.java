package network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class Client {

    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public Scanner scanner;

    public String pseudo;
    public ArrayList<String> joinedChannel;

    public Client(String host, int port) throws IOException {;

        this.socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);

        this.joinedChannel = new ArrayList<String>();
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void addJoinedChannel(String joinedChannel) {
        this.joinedChannel.add(joinedChannel);
    }


    public void close(){
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
