import com.sun.source.tree.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KnockKnockServer {
    public static void main(String[] args) {
        int portNumber = 5555;

        try(
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));

                ) {
            String inputLine, outputLine;

            //KnockKnockProtocol kkp = new KnockKnockProtocol();
            //outputLine = kkp.processInput(null);
            //outputLine = emailExtractor.test(null);
            //out.println(outputLine);

            EmailExtractor emailExtractor = new EmailExtractor();

            while((inputLine = in.readLine()) != null){
                //outputLine = kkp.processInput(inputLine);
                outputLine = emailExtractor.test(inputLine);
                out.println(outputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
