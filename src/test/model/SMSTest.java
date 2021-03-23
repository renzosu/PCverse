package model;

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
    private Message m1;
    private Message m2;

    @BeforeEach
    public void runBefore() {
        sms = new SMS();
        m1 = new Message("Hello");
        m2 = new Message("Goodbye");
    }

    @Test
    public void testGetMessages() {
        sms.sendMessage(m1);
        assertEquals(1, sms.getMessages().size());
        assertEquals("Hello", sms.getMessages().get(0).getMessage());
    }

    @Test
    public void testSendMessage() {
        sms.sendMessage(m1);
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
        sms.sendMessage(m1);
        sms.sendMessage(m2);
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
        sms.sendMessage(m1);
        sms.sendMessage(m2);
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
        sms.sendMessage(m1);
        assertEquals(1, sms.numMessages());
        sms.deleteMessage();
        assertEquals(0, sms.numMessages());
    }

    @Test
    void testMessagesToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(m1.toJson());
        sms.sendMessage(m1);
        assertEquals(jsonArray.length(), sms.messagesToJson().length());
    }

    @Test
    void testToJson() {
        JSONObject json = new JSONObject();
        json.put("messages", sms.messagesToJson());
        assertEquals(json.length(), sms.toJson().length());
    }

}