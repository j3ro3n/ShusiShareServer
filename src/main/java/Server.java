import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(40000);
            System.out.println("Some one has started the FileSharing server! SushiShare Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }


        while (true) {
            try {
                Socket requestFromClient = serverSocket.accept();
                System.out.println("Accepted connection : " + requestFromClient);

                Thread t = new Thread(new ServerFileShareHandler(requestFromClient));

                t.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }
    }
}

