
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    public ServerSocket serverSocket;
    public int port;
    public PrintWriter out;
    public BufferedReader in;
    public Socket socket;
    public static String file = "/Users/habbi/Documents/L3-DANT/PROJETS_GROUPE/ProjetSlack/src/network/messages";

    public Server(int port) throws IOException {
        this.port = port;
        System.out.println("Creating a server on port "+port);
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

    public void sendOldMessages(){

        try {
            System.out.println("SERVER__________DEBUT ENVOI ARCHIVE__________");

            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            String data = null;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                out.println(data);
            }
            myReader.close();
            System.out.println("SERVER__________ARCHIVE FINI__________");
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    /*public void archiveMessage(String line, String file){
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw, true);){
            pw.println(line);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }*/

    public static void main(String[] args) throws IOException {
        try{
            Server serverSocket = new Server(1234);
            ExecutorService pool = Executors.newFixedThreadPool(4);

            while(true) {
                serverSocket.initConnection();



                /*pool.submit(() -> {
                    System.out.println("A client is connected from " + serverSocket.socket.getInetAddress().getHostAddress());

                    String line;
                    while ((line = serverSocket.in.readLine()) != null) {

                        // writing the received message from client
                        System.out.printf(
                                " Sent from the client: %s\n",
                                line);

                        //send back
                        serverSocket.out.println(line);
                    }

                    return true;
                });*/
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}