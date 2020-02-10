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
        //super.run();

        try(
                // Create server socket with the given port number
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
                ){
            String inputLine, outputLine;

            EmailExtractor emailExtractor = new EmailExtractor();

            while((inputLine = in.readLine()) != null){
                outputLine = emailExtractor.test(inputLine);
                out.println(inetAddress.getHostAddress() + " " + clientPortNumber + " " + outputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
