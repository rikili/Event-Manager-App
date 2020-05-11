package network;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WebPageRead {

    //REQUIRES: a valid webpage link that is readable
    //EFFECTS: reads page of given api string
    public static String readJson(String api) throws IOException {
        String br;
        URL temp = new URL(api);
        br = readSource(temp.openStream());
        return br;
    }

    //EFFECTS: reads page and builds a string from what is read
    private static String readSource(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        br.close();

        return sb.toString();
    }

    //EFFECTS: reads given url and returns the lines that is read
    public static String read(String url) throws MalformedURLException, IOException {
        BufferedReader br = null;
        String line;
        try {
            URL temp = new URL(url);
            br = new BufferedReader(new InputStreamReader(temp.openStream()));
            line = br.readLine();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        System.out.println(line);
        return line;
    }

}
