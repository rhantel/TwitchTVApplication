package de.rlha.twitch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rlha.twitch.util.HttpRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private final String STREAM_TITLE_PROPERTY = "status";
    
    private final String STREAM_GAME_PROPERTY = "game";
    
    private final String STREAM_VIEWERS_PROPERTY = "viewers";
    
    private final String STREAM_CHANNEL_PROPERTY = "channel";
    
    private final String STREAM_URL_PROPERTY = "url";
    
    private String STREAM_TITLE_VALUE;
    
    private String STREAM_GAME_VALUE;
    
    private String STREAM_VIEWERS_VALUE;
     
    private String STREAM_URL_VALUE;
    
    public TwitchTVStream(final String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        
        this.TWITCH_USERNAME = username;
    }
    
    public boolean isLive() throws IOException, MimeTypeParseException {
        String response = HttpRequest.performRequest(this.TWITCH_API_STREAM_BASE_URL + this.TWITCH_USERNAME, this.MIME_TYPE);            
        JsonParser parser = new JsonParser();

        JsonObject streamJson = (JsonObject) parser.parse(response);
        
        boolean isLive = streamJson.get(this.STREAM_PROPERTY).isJsonNull() == false;
        
        if (isLive) {
            this.STREAM_TITLE_VALUE = ((JsonObject)((JsonObject) streamJson.get(this.STREAM_PROPERTY)).get(this.STREAM_CHANNEL_PROPERTY)).get(STREAM_TITLE_PROPERTY).getAsString();
            JsonObject channelJson = (JsonObject) ((JsonObject) streamJson.get(this.STREAM_PROPERTY)).get(this.STREAM_CHANNEL_PROPERTY);            
            JsonElement gameJson = channelJson.get(this.STREAM_GAME_PROPERTY);
            this.STREAM_GAME_VALUE = gameJson.isJsonNull()?"":gameJson.getAsString();
            this.STREAM_VIEWERS_VALUE = ((JsonObject) streamJson.get(this.STREAM_PROPERTY)).get(STREAM_VIEWERS_PROPERTY).getAsString();
            this.STREAM_URL_VALUE = ((JsonObject)((JsonObject) streamJson.get(this.STREAM_PROPERTY)).get(this.STREAM_CHANNEL_PROPERTY)).get(STREAM_URL_PROPERTY).getAsString();
        }
        else {
            this.STREAM_TITLE_VALUE = "";
            this.STREAM_GAME_VALUE = "";
            this.STREAM_VIEWERS_VALUE = "";
            this.STREAM_URL_VALUE = "";
        }

        return isLive;
    }
    
    public String getViewers() {     
        if (this.STREAM_VIEWERS_VALUE == null) {
            throw new IllegalStateException("isLive() method has to be called first");
        }
        return this.STREAM_VIEWERS_VALUE;
    }
    
    public String getTitle() {   
        if (this.STREAM_VIEWERS_VALUE == null) {
            throw new IllegalStateException("isLive() method has to be called first");
        }
        return this.STREAM_TITLE_VALUE;
    }
        
    public String getGame() {   
        if (this.STREAM_VIEWERS_VALUE == null) {
            throw new IllegalStateException("isLive() method has to be called first");
        }
        return this.STREAM_GAME_VALUE;
    }
    
    public String getUrl() {         
        if (this.STREAM_VIEWERS_VALUE == null) {
            throw new IllegalStateException("isLive() method has to be called first");
        }
        return this.STREAM_URL_VALUE;
    }
}
