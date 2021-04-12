package model;

import exceptions.EmptyMessageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SMS class
 */
public class SMSTest {
    private SMS sms;
    private Message m0;
    private Message m1;
    private Message m2;

    @BeforeEach
    public void runBefore() {
        sms = new SMS();
        m0 = new Message("");
        m1 = new Message("Hello");
        m2 = new Message("Goodbye");
    }

    @Test
    public void testGetMessages() {
        try {
            sms.sendMessage(m1);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        assertEquals(1, sms.getMessages().size());
        assertEquals("Hello", sms.getMessages().get(0).getMessage());
    }

    @Test
    public void testSendMessageEmptyMessage() {
        try {
            sms.sendMessage(m0);
            fail("should have thrown EmptyMessageException");
        } catch (EmptyMessageException e) {
            System.err.println("Message contents cannot be empty!");
            // expected behaviour
        }
        assertEquals(0, sms.numMessages());
    }

    @Test
    public void testSendMessageNonEmptyMessage() {
        try {
            sms.sendMessage(m1);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        assertEquals(1, sms.numMessages());
        assertEquals("Hello", m1.getMessage());
    }


    @Test
    public void testDeleteMessageFailure() {
        sms.deleteMessage();
        assertEquals(0, sms.numMessages());
    }

    @Test
    public void testDeleteMessageSuccess() {
        try {
            sms.sendMessage(m1);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        try {
            sms.sendMessage(m2);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        assertEquals(2, sms.numMessages());
        sms.deleteMessage();
        assertEquals(1, sms.numMessages());
    }

    @Test
    public void testDisplayMessagesNone() {
        assertEquals("\n", sms.displayMessages());
    }

    @Test
    public void testDisplayMessagesSome() {
        try {
            sms.sendMessage(m1);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        try {
            sms.sendMessage(m2);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        assertEquals("\nHello\nGoodbye\n", sms.displayMessages());
        sms.deleteMessage();
        assertEquals("\nHello\n", sms.displayMessages());
    }

    @Test
    public void testNumMessagesNone() {
        assertEquals(0, sms.numMessages());
    }

    @Test
    public void testNumMessagesSome() {
        try {
            sms.sendMessage(m1);
            // expected behaviour
        } catch (EmptyMessageException e) {
            fail("should not have thrown");
        }
        assertEquals(1, sms.numMessages());
        sms.deleteMessage();
        assertEquals(0, sms.numMessages());
    }

    @Test
    void testMessagesToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(m1.toJson());
        try {
            sms.sendMessage(m1);
        } catch (EmptyMessageException e) {
            e.printStackTrace();
        }
        assertEquals(jsonArray.length(), sms.messagesToJson().length());
    }

    @Test
    void testToJson() {
        JSONObject json = new JSONObject();
        json.put("messages", sms.messagesToJson());
        assertEquals(json.length(), sms.toJson().length());
    }

}