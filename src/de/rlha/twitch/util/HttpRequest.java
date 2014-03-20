package de.rlha.twitch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

/**
 *
 * @author Roland Hantel
 * @version 1.0
 * @since   1.0
 */
public class HttpRequest {
    public static String performRequest(URL url, MimeType mime) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", mime.toString());
            
            if (conn.getResponseCode() != 200)
                throw new IOException("Failed : HTTP error code : " + conn.getResponseCode());
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            return br.readLine();
        } catch (IOException ex) {
            throw ex;
        }
        finally {
            if (conn != null)
                conn.disconnect();
            if (br != null)
                br.close();
        }             
    }
    
    public static String performRequest(String url, String mime) throws IOException, MimeTypeParseException {
        return performRequest(new URL(url), new MimeType(mime));
    }
    
    public static String performRequest(URL url, String mime) throws MimeTypeParseException, IOException {
        return performRequest(url, new MimeType(mime));
    }
        
    public static String performRequest(String url, MimeType mime) throws IOException  {
        return performRequest(new URL(url), mime);
    }
}
