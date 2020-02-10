import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        int portNumber = 5555;

        try(
                // Create server socket with the given port number
                ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
            // continuously listening for clients
            while(true){
                // create and start a new ClientServer thread for each connected client
                MultiClient multiClient = new MultiClient(serverSocket.accept());
                multiClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//without thread
/*
public class EchoServer {
    public static void main(String[] args) {
        int portNumber = 5555;

        try(
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));

        ) {
            String inputLine, outputLine;

            EmailExtractor emailExtractor = new EmailExtractor();

            while((inputLine = in.readLine()) != null){
                outputLine = emailExtractor.test(inputLine);
                out.println(outputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
