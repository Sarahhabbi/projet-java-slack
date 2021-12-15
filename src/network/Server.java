import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("Creating a server on port 1236");

        String file = "/Users/habbi/Documents/L3-DANT/JAVA/TP-Network-JAVA/src/messages";

        try(ServerSocket serverSocket = new ServerSocket(1236)) {
            System.out.println("Waiting a connection...");
            Socket socket = serverSocket.accept();
            System.out.println("A client is connected from " + socket.getInetAddress().getHostAddress());
            PrintWriter out =	new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // ENVOI MESSAGE ARCHIVEE AU CLIENT
            try {
                File myObj = new File(file);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    out.println(data);
                }
                System.out.println("OK1 - ARCHIVE FINI");
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            //LECTURE CLIENT MESSAGES AND ARCHIVE MESSAGES
            String line = null;
            // Read infinitely what the client sends
            while ((line = in.readLine()) != null) {
                // Send back the line and print it
                System.out.println(line);
                out.println(line);

                try (FileWriter fw = new FileWriter(file, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter pw = new PrintWriter(bw, true);){
                     pw.println(line);
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}