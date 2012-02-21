/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher.transport;

import java.util.logging.Level;
import java.util.logging.Logger;

import ch.mbae.pusher.PusherChannel;
import ch.mbae.pusher.PusherCredentials;
import ch.mbae.pusher.PusherResponse;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pushing a an event to pusher by using transport based on jakarta HttpClient.
 *
 * @author marcbaechinger
 */
public class HttpClientPusherTransportTest implements PusherCredentials {

    private static final Logger LOGGER = Logger.getLogger(HttpClientPusherTransportTest.class.getName());

    private static final String JSON_STRING = "{ originator: " + HttpClientPusherTransportTest.class.getName() + "}";
    private static final String SOCKET_ID = "12345678";
    private HttpClientPusherTransport tranportUnderTest;
    private PusherChannel channel;


    @Before
    public void setUp() {
        // create the transport under test
        this.tranportUnderTest = new HttpClientPusherTransport();
        // the channel to inject the transport into
        this.channel = new PusherChannel(CHANNEL, APPLICATION_ID, APPLICATION_KEY, APPLICATION_SECRET,  this.tranportUnderTest);
    }

    /**
     * Test of fetch method, of class HttpClientPusherTransport.
     */
    @Test
    public void testFetch() throws Exception {
        // push the event
        PusherResponse response = this.channel.pushEvent(EVENT, JSON_STRING);

        LOGGER.log(Level.INFO, Integer.toString(response.getResponseCode()));
        LOGGER.log(Level.INFO, new String(response.getContent()));
        LOGGER.log(Level.INFO, response.getHeaders().get("Content-Length"));

        // http status 202
        Assert.assertEquals(202, response.getResponseCode());
        // content as expected ? (hard test; might fail once)
        //Assert.assertEquals("202 ACCEPTED\n", new String(response.getContent()));
        // correct content length in http header?
        Assert.assertEquals(Integer.valueOf(response.getHeaders().get("Content-Length")).intValue(), response.getContent().length);
    }

    /**
     * Test of fetch method, of class HttpClientPusherTransport.
     */
    @Test
    public void testFetchWidthSessionId() throws Exception {
        // push the event
        PusherResponse response = this.channel.pushEvent(EVENT, JSON_STRING, SOCKET_ID);

        LOGGER.log(Level.INFO, Integer.toString(response.getResponseCode()));
        LOGGER.log(Level.INFO, new String(response.getContent()));
        LOGGER.log(Level.INFO, response.getHeaders().get("Content-Length"));

        // http status 202
        Assert.assertEquals(202, response.getResponseCode());
        // content as expected ? (hard test; might fail once)
        //Assert.assertEquals("202 ACCEPTED\n", new String(response.getContent()));
        // correct content length in http header?
        Assert.assertEquals(Integer.valueOf(response.getHeaders().get("Content-Length")).intValue(), response.getContent().length);
    }
}
