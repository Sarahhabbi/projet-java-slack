package network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) throws IOException {
        System.out.println("Creating a server on port "+ serverSocket.getInetAddress().getHostAddress());
        this.serverSocket = serverSocket;
    }

    public void closeServerSocket() {
        try{
            ClientHandler.closeEverySocket();
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        while(!serverSocket.isClosed()) {
            try{
                    System.out.println("Waiting a connection ...");
                    Socket socket = serverSocket.accept();  //blocking operation

                    System.out.println("A new client is connected from "+ socket.getInetAddress().getHostAddress());

                    ClientHandler clientHandler = new ClientHandler(socket);

                    // create a new thread object
                    Thread thread = new Thread(clientHandler);
                    System.out.println("A new thread was assigned to this client");

                    thread.start();
            }catch(IOException e){
                this.closeServerSocket();
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = null;

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            server.closeServerSocket();
            e.printStackTrace();
        }
    }
// TODO débuger la boucle infinie quand on déconnecte le server, les clients recoivent 'null' infinement
}
