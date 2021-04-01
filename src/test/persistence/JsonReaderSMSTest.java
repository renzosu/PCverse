package persistence;

import model.SMS;
import model.Message;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JsonReaderSMS class
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
class JsonReaderSMSTest extends JsonSMSTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderSMS reader = new JsonReaderSMS("./data/noSuchFile.json");
        try {
            SMS sms = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySMS() {
        JsonReaderSMS reader = new JsonReaderSMS("./data/testReaderEmptySMS.json");
        try {
            SMS sms = reader.read();
            assertEquals(0, sms.numMessages());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSMS() {
        JsonReaderSMS reader = new JsonReaderSMS("./data/testReaderGeneralSMS.json");
        try {
            SMS sms = reader.read();
            List<Message> messages = sms.getMessages();
            assertEquals(2, messages.size());
            checkMessage("Hello!", messages.get(0));
            checkMessage("How are you Joe?", messages.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralExceptionSMS() {
        JsonReaderSMS reader = new JsonReaderSMS("./data/testReaderGeneralExceptionSMS.json");
        try {
            SMS sms = reader.read();
            List<Message> messages = sms.getMessages();
            assertEquals(0, messages.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}