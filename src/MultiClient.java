import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MultiClient extends Thread {
    Socket socket;
    InetAddress inetAddress;
    int serverPortNumber;
    int clientPortNumber;

    public MultiClient(Socket socket){
        this.socket = socket;
        inetAddress = socket.getInetAddress();
        clientPortNumber = socket.getPort();
        serverPortNumber = socket.getLocalPort();
    }

    @Override
    public void run() {
        try(
                // Create server socket with the given port number
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
                ){
            String inputLine, outputLine;
            //Creates a new EmailExtractor object
            EmailExtractor emailExtractor = new EmailExtractor();

            // read from the connection socket
            while((inputLine = in.readLine()) != null){
                //Takes input from client and sends it to "extractEmail" method in class EmailExtractor
                outputLine = emailExtractor.extractEmail(inputLine);
                out.println(inetAddress.getHostAddress() + " " + clientPortNumber + " " + outputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
