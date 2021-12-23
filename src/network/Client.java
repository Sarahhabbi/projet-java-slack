package network;
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Client {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String pseudo;
    private Scanner scn;


    public Client(Socket socket, String pseudo) throws IOException {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.scn = new Scanner(System.in);
        this.pseudo = pseudo;
    }

    public void close() throws IOException {
        // closing resources
        try {
            if (this.dos != null) {
                this.dos.close();
            }
            if (this.dis != null) {
                this.dis.close();
            }
            if(this.scn != null){
                this.scn.close();
            }
            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            // establish the connection with server port 5056
            Socket s = new Socket("localhost", 1234);

            //create client
            Client client = new Client(s, "sarah");

            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                System.out.println(client.dis.readUTF());
                String tosend = client.scn.nextLine();
                client.dos.writeUTF(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Quit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

//                printing date or time as requested by client
//                String received = dis.readUTF();
//                System.out.println(received);
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
    }
}