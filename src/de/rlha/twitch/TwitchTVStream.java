package de.rlha.twitch;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rlha.twitch.util.HttpRequest;
import java.io.IOException;
import javax.activation.MimeTypeParseException;

/**
 *
 * @author Roland
 */
public class TwitchTVStream {
    /** MIME-Type for a GET-Request to Version 3 of the TwitchTV REST API */
    private final String MIME_TYPE = "application/vnd.twitch.v3+json";
    /** TwitchTV REST API's base URL for a specific channel */
    private final String TWITCH_API_STREAM_BASE_URL = "https://api.twitch.tv/kraken/streams/";
    /** Name of the TwitchTV channel */
    private final String TWITCH_USERNAME;
    
    private final String STREAM_PROPERTY = "stream";
    
    public TwitchTVStream(final String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        
        this.TWITCH_USERNAME = username;
    }
    
    public boolean isLive() throws IOException, MimeTypeParseException {
        try {
            String response = HttpRequest.performRequest(this.TWITCH_API_STREAM_BASE_URL + this.TWITCH_USERNAME, this.MIME_TYPE);            
            JsonParser parser = new JsonParser();
            
            JsonObject streamJson = (JsonObject) parser.parse(response);
            
            return streamJson.get(this.STREAM_PROPERTY).isJsonNull() == false;
                    
        } catch (IOException | MimeTypeParseException ex) {
            throw ex;
        }
    }
}
