package persistence;

import model.SMS;
import model.Message;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JsonReader class
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SMS sms = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySMS.json");
        try {
            SMS sms = reader.read();
            assertEquals(0, sms.numMessages());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSMS.json");
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
}