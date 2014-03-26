package de.rlha.twitch;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import de.rlha.twitch.exceptions.FetchLinksException;
import de.rlha.twitch.util.HttpRequest;
import java.io.IOException;
import javax.activation.MimeTypeParseException;

/**
 * Representation of channel specific links for performing calls to the TwitchTV REST API
 * <p>
 * The links are fetched by using the TwitchTV REST API's selfdescribing functionality
 * 
 * @author  Roland Hantel
 * @version 1.0
 * @since   1.0
 */
public class TwitchTVChannelLinks {
    /** MIME-Type for a GET-Request to Version 3 of the TwitchTV REST API */
    private final String MIME_TYPE = "application/vnd.twitch.v3+json";
    /** TwitchTV REST API's base URL for a specific channel */
    private final String TWITCH_API_CHANNELS_BASE_URL = "https://api.twitch.tv/kraken/channels/";
    /** Name of the TwitchTV channel */
    private final String TWITCH_USERNAME;
    
    /// Property names
    /** Property name TwitchTV REST API uses for selfdescription */
    private final String SELFDESCRIBING_PROPERTY = "_links";
    /** Property name for the link, under which TwitchTV REST API returns the channel followers */
    private final String FOLLOWS_PROPERTY = "follows";
    /** Property name for the link, under which TwitchTV REST API returns the channel teams */
    private final String TEAMS_PROPERTY = "teams";
    /** Property name for the link, under which TwitchTV REST API returns the channel information */
    private final String CHANNEL_PROPERTY = "self";
    /** Property name for the link, which can be used to put commercials on the channel using PUT-Request */
    private final String COMMERCIAL_PROPERTY = "commercial";
    /** Property name for the link, which can be used to reset the channel's stream key using DELETE-Request */
    private final String STREAM_KEY_PROPERTY = "stream_key";
    /** Property name for the link, under which TwitchTV REST API returns the channel's links to chat endpoints */
    private final String CHAT_PROPERTY = "chat";
    /** Property name for the link, under which TwitchTV REST API returns the channel's features */
    private final String FEATURES_PROPERTY = "features";
    /** Property name for the link, under which TwitchTV REST API returns the channel's subcriptions */
    private final String SUBSCRIPTIONS_PROPERTY = "subscriptions";
    /** Property name for the link, under which TwitchTV REST API returns the channel editors */
    private final String EDITORS_PROPERTY = "editors";
    /** Property name for the link, under which TwitchTV REST API returns the channel videos */
    private final String VIDEOS_PROPERTY = "videos";
    
    /// URLs | property values
    /** Value of FOLLOWS_PROPERTY */
    private final String FOLLOWS_URL;
    /** Value of TEAMS_PROPERTY */
    private final String TEAMS_URL;
    /** Value of CHANNEL_PROPERTY */
    private final String CHANNEL_URL;
    /** Value of COMMERCIAL_PROPERTY */
    private final String COMMERCIAL_URL;
    /** Value of STREAM_KEY_PROPERTY */
    private final String STREAM_KEY_URL;
    /** Value of CHAT_PROPERTY */
    private final String CHAT_URL;
    /** Value of FEATURES_PROPERTY */
    private final String FEATURES_URL;
    /** Value of SUBSCRIPTIONS_PROPERTY */
    private final String SUBSCRIPTIONS_URL;
    /** Value of EDITORS_PROPERTY */
    private final String EDITORS_URL;
    /** Value of VIDEOS_PROPERTY */
    private final String VIDEOS_URL;
    
    /** The response of TwitchTV REST API's when getting the channel information */
    private final String RESPONSE;

    /**
     * Requests the channel links from TwitchTV's REST API and stores the information
     * into the respective properties
     * 
     * @param  username             name of the TwitchTV channel
     * @throws FetchLinksException  if there the connection to TwitchTV's REST API could 
     *                              not be established or if the returned JSON object 
     *                              is not compatible or has format errors
     */
    public TwitchTVChannelLinks(final String username) throws FetchLinksException {
        this.TWITCH_USERNAME = username;
        
        try {
            this.RESPONSE = HttpRequest.performRequest(this.TWITCH_API_CHANNELS_BASE_URL + this.TWITCH_USERNAME, this.MIME_TYPE);
        } catch (IOException | MimeTypeParseException ex) {
            throw new FetchLinksException();
        }
        
        this.FOLLOWS_URL = this.extractProperty(this.FOLLOWS_PROPERTY);
        this.TEAMS_URL = this.extractProperty(this.TEAMS_PROPERTY);
        this.CHANNEL_URL = this.extractProperty(this.CHANNEL_PROPERTY);
        this.COMMERCIAL_URL = this.extractProperty(this.COMMERCIAL_PROPERTY);
        this.STREAM_KEY_URL = this.extractProperty(this.STREAM_KEY_PROPERTY);
        this.CHAT_URL = this.extractProperty(this.CHAT_PROPERTY);
        this.FEATURES_URL = this.extractProperty(this.FEATURES_PROPERTY);
        this.SUBSCRIPTIONS_URL = this.extractProperty(this.SUBSCRIPTIONS_PROPERTY);
        this.EDITORS_URL = this.extractProperty(this.EDITORS_PROPERTY);
        this.VIDEOS_URL = this.extractProperty(this.VIDEOS_PROPERTY);
    }
    
    /**
     * Parses the specified property from the selfdescribing property of the RESPONSE
     * <p>
     * If there is no slash ("/") at the end of the parsed value, the slash is being added
     * 
     * @param  propertyName         property to be parsed from RESPONSE
     * @return                      link specified in the property with the name propertyName
     * @throws FetchLinksException  if the JSON object of RESPONSE is not compatible or has
     *                              format errors
     */
    private String extractProperty(String propertyName) throws FetchLinksException {
        JsonParser parser = new JsonParser();
        
        try {
            JsonObject responseJson = (JsonObject) parser.parse(this.RESPONSE);
            
            if (!responseJson.isJsonNull()) {
                JsonObject linksJson = responseJson.getAsJsonObject(this.SELFDESCRIBING_PROPERTY);
                
                if (!linksJson.isJsonNull()) {
                    JsonPrimitive propertyJson = linksJson.getAsJsonPrimitive(propertyName);

                    if (!propertyJson.isJsonNull()) {
                        String returnValue = propertyJson.getAsString();
                        
                        if (!returnValue.endsWith("/"))
                            returnValue += "/";
                        
                        return returnValue;
                    }
                }
            }
        }
        catch (JsonParseException ex) {
            throw new FetchLinksException();
        }        
        throw new FetchLinksException();
    }

    /**
     * Getter for the specified TwitchTV Channel-/Username
     * 
     * @return specified Channel-/Username
     */
    public String getTwitchUsername() {
        return this.TWITCH_USERNAME;
    }

    /**
     * Getter for the Follows URL
     * 
     * @return Follows URL
     */
    public String getFollowsUrl() {
        return this.FOLLOWS_URL;
    }

    /**
     * Getter for the Teams URL
     * 
     * @return Teams URL
     */
    public String getTeamsUrl() {
        return this.TEAMS_URL;
    }
    
    /**
     * Getter for the Channel URL
     * 
     * @return Channel URL
     */
    public String getChannelUrl() {
        return this.CHANNEL_URL;
    }
    
    /**
     * Getter for the Commercial URL
     * 
     * @return Commercial URL
     */
    public String getCommercialUrl() {
        return this.COMMERCIAL_URL;
    }

    /**
     * Getter for the Stream Key URL
     * 
     * @return Stream Key URL
     */    
    public String getStreamKeyUrl() {
        return this.STREAM_KEY_URL;
    }

    /**
     * Getter for the Chat URL
     * 
     * @return Chat URL
     */    
    public String getChatUrl() {
        return this.CHAT_URL;
    }

    /**
     * Getter for the Features URL
     * 
     * @return Features URL
     */    
    public String getFeaturesUrl() {
        return this.FEATURES_URL;
    }

    /**
     * Getter for the Subscriptions URL
     * 
     * @return Subscriptions URL
     */    
    public String getSubscriptionsUrl() {
        return this.SUBSCRIPTIONS_URL;
    }

    /**
     * Getter for the Editors URL
     * 
     * @return Editors URL
     */    
    public String getEditorsUrl() {
        return this.EDITORS_URL;
    }

    /**
     * Getter for the Videos URL
     * 
     * @return Videos URL
     */    
    public String getVideosUrl() {
        return this.VIDEOS_URL;
    }    
}
