package network;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(1234);
        while(true) {
            Socket s = null;
            try
            {
                s = ss.accept(); //accepte les clients qui veulent se connecter
                System.out.println("Creating a server on port 5056");

                System.out.println("A new client is connected : " + s);

                // connecter les input/output Stream
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("A new thread was assigned  this client");

                // create a new thread object
                Thread t = new ClientHandler(s);
                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
