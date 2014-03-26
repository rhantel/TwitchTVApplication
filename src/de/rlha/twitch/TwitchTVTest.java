package de.rlha.twitch;

import de.rlha.twitch.exceptions.FetchLinksException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimeTypeParseException;

/**
 *
 * @author Roland
 */
public class TwitchTVTest {
    public static void main(String[] args) {
        testTwitchTVLinks();
        System.out.println("");
        testTwitchTVStream();

        
    }
    
    private static void testTwitchTVLinks() {
        try {
            TwitchTVChannelLinks links = new TwitchTVChannelLinks("northwind_cooza");
            
            System.out.println("Username:      " + links.getTwitchUsername());
            System.out.println("Channel:       " + links.getChannelUrl());            
            System.out.println("Videos:        " + links.getVideosUrl());
            System.out.println("Chat:          " + links.getChatUrl());
            System.out.println("Commercial:    " + links.getCommercialUrl());
            System.out.println("Features:      " + links.getFeaturesUrl());
            System.out.println("Editors:       " + links.getEditorsUrl());
            System.out.println("Follows:       " + links.getFollowsUrl());
            System.out.println("Subscriptions: " + links.getSubscriptionsUrl());
            System.out.println("Stream Key:    " + links.getStreamKeyUrl());
            System.out.println("Teams:         " + links.getTeamsUrl());
        } catch (FetchLinksException ex) {
            System.err.println("Could not get links. Either cannot connect to Twitch API or user does not exist.");
        }  
    }
    
    private static void testTwitchTVStream() {
        try {
            TwitchTVStream coozaStream = new TwitchTVStream("northwind_cooza");
            TwitchTVStream kripparianStream = new TwitchTVStream("nl_kripp");
            TwitchTVStream kungenStream = new TwitchTVStream("kungentv");

            System.out.print("northwind_cooza: ");
            System.out.println(coozaStream.isLive()?"LIVE":"OFFLINE");

            System.out.print("nl_kripp:        ");
            System.out.println(kripparianStream.isLive()?"LIVE":"OFFLINE");

            System.out.print("kungentv:        ");
            System.out.println(kungenStream.isLive()?"LIVE":"OFFLINE");
        } catch (IOException | MimeTypeParseException ex) {
            Logger.getLogger(TwitchTVTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
