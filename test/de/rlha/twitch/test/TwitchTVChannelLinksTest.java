package de.rlha.twitch.test;

import de.rlha.twitch.TwitchTVChannelLinks;
import de.rlha.twitch.exceptions.FetchLinksException;

/**
 *
 * @author Roland
 */
public class TwitchTVChannelLinksTest {
    public static void main(String[] args) {
        try {
            TwitchTVChannelLinks links = new TwitchTVChannelLinks("wdyftw");
            
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
}
