package de.rlha.twitch.exceptions;

/**
 * The Exception that is being thrown if an error occurs when getting TwitchTV REST API links
 * 
 * @author  Roland Hantel
 * @version 1.0
 * @since   1.0
 */
public class FetchLinksException extends Throwable {
    
    /**
     * Getter for message
     * 
     * @return always returns "Could not fetch links."
     */
    @Override
    public String getMessage() {
        return "Could not fetch links.";
    }
    
    /**
     * Getter for localized message
     * 
     * @return always returns "Could not fetch links."
     */
    @Override
    public String getLocalizedMessage() {
        return "Could not fetch links.";
    }
}
