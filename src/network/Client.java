import java.io.*;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public Scanner scanner;

    public String pseudo;
    public String joinedChannel;

    public Client(String host, int port) throws IOException {;

        this.socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setJoinedChannel(String joinedChannel) {
        this.joinedChannel = joinedChannel;
    }

    /*public void sendMessages() throws IOException {
        Scanner scanner = new Scanner(System.in);
            String line;
            while (scanner.hasNextLine()) {
                System.out.println("CLIENT SENDS INFINITLY");

                // Read the line from stdin
                line = scanner.nextLine();

                // Send the line to the server
                this.out.println(line);

                // Print what the server is sending
                System.out.println("SERVER SENT" + this.in.readLine());
            }
    }

    public void readOldMessages() throws IOException {
        System.out.println("CLIENT___________DEBUT ARCHIVE___________");
        String line = null;
        while ((line = this.in.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("CLIENT___________FIN ARCHIVE___________");
    }*/

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

    /*public static void main(String[] args) throws IOException {

        try {
            Client client = new Client("localhost", 1234);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
