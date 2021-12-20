import network.Server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        try{
            Server serverSocket = new Server(1234);
            ExecutorService pool = Executors.newFixedThreadPool(4);

            while(true) {
                serverSocket.initConnection();

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
