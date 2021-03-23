package persistence;

import model.Message;
import model.SMS;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JsonWriterSMS class
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
class JsonWriterSMSTest extends JsonSMSTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            SMS sms = new SMS();
            JsonWriterSMS writer = new JsonWriterSMS("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySMS() {
        try {
            SMS sms = new SMS();
            JsonWriterSMS writer = new JsonWriterSMS("./data/testWriterEmptySMS.json");
            writer.open();
            writer.write(sms);
            writer.close();

            JsonReaderSMS reader = new JsonReaderSMS("./data/testWriterEmptySMS.json");
            sms = reader.read();
            assertEquals(0, sms.numMessages());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSMS() {
        try {
            SMS sms = new SMS();
            sms.sendMessage(new Message("I have to go."));
            sms.sendMessage(new Message("See you next time!"));
            JsonWriterSMS writer = new JsonWriterSMS("./data/testWriterGeneralSMS.json");
            writer.open();
            writer.write(sms);
            writer.close();

            JsonReaderSMS reader = new JsonReaderSMS("./data/testWriterGeneralSMS.json");
            sms = reader.read();
            List<Message> messages = sms.getMessages();
            assertEquals(2, messages.size());
            checkMessage("I have to go.", messages.get(0));
            checkMessage("See you next time!", messages.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}