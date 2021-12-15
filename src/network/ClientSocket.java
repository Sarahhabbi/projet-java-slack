
import java.io.*;
import java.net.Socket;
import java.util.Scanner;



public class ClientSocket {

    public static void main(String[] args) throws IOException {
        String file = "/Users/habbi/Documents/L3-DANT/JAVA/TP-Network-JAVA/src/messages";

        try(Socket socket = new Socket("localhost", 1236);
            PrintWriter out =	new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            //read archived messages
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            System.out.println("MESSAGE ARCHIVEE FINI");
            myReader.close();
            
            
            Scanner scanner = new Scanner(System.in);
            String line;
            while (scanner.hasNextLine()) {
                // Read the line from stdin
                line = scanner.nextLine();
                // Send the line to the server
                out.println(line);
                // Print what the server is sending
                System.out.println("SERVER SEND "+ in.readLine());
            }
        }
    }

}