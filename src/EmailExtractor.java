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

    public String extractEmail(String input) throws IOException {
        String outPut = "";
        URL url = new URL(input.trim());

        //Check if website exists
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //setRequestMethod takes several inputs:GET POST HEAD OPTIONS PUT DELETE TRACE
        //HEAD requests the HTTP headers from server and don't return a message body.
        httpURLConnection.setRequestMethod("HEAD");

        //A page not found will result in a int with value 404
        int res = httpURLConnection.getResponseCode();
        if (res == 404){
            return "Code 2: print ‘!!!Server couldn’t find the web page!!!’";
        }

        //Several regex attempts
        //String regex ="[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        //String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        //String regex = "\\b[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+[.]+[A-Za-z]{2,4}$";
        //String regex = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        String regex = "\\b[A-Za-z0-9+._-]+@[A-Za-z0-9+._-]+.[A-Za-z0-9]\\b"; // \b = a word boundry, takes lower and upper case letters, numbers from 0-9 and ._-

        //Creates a compiled representation of a regular expression
        //As per doc: A regular expression, specified as a string, must first be compiled into an instance of this class.
        Pattern pattern = Pattern.compile(regex);

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String urlLine = "";
        while ((urlLine = in.readLine()) != null){
            //pattern can now be used to create a Matcher object that matches arbitrary charachter
            //sequences against the regular expression
            Matcher matcher = pattern.matcher(urlLine);

            if (matcher.find()){
                //adds matching results to output string
                outPut += matcher.group();
            }
        }

        //If no matches are founds code 1 is returned
        if (outPut.length() == 0 ){
            return "Code 1: print ‘!!!No email address found on the page!!!’.";
        }

        //Returns results
        return outPut + " lengde " + outPut.length();
    }
}
