import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor {

    String url;

    public String test(String input) throws IOException {
        String outPut = "";
        URL url = new URL(input.trim());

        //Sjekker om nettsiden eksisterer
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("HEAD");
        int res = httpURLConnection.getResponseCode();

        if (res == 404){
            return "Code 2: print ‘!!!Server couldn’t find the web page!!!’";
        }
        int lengde = outPut.length();

        //String regex ="[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        //String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        //String regex = "\\b[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+[.]+[A-Za-z]{2,4}$";
        //String regex = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        String regex = "\\b[A-Za-z0-9+._-]+@[A-Za-z0-9+._-]+.[A-Za-z0-9]\\b";

        Pattern pattern = Pattern.compile(regex);

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String urlLine = "";
        int teller = 0;
        while ((urlLine = in.readLine()) != null){
            Matcher matcher = pattern.matcher(urlLine);
            teller++;

            if (matcher.find()){
                outPut += matcher.group();
            }
        }

        if (outPut.length() == 0 ){
            return "Code 1: print ‘!!!No email address found on the page!!!’.";
        }

        //return lengde + " <- Fra server: " + outPut + " teller: " + teller;
        return outPut + " lengde " + outPut.length();
    }
}
