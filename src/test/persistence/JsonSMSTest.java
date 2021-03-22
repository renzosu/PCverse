package persistence;

import model.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Json reading and writing
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonSMSTest {
    protected void checkMessage(String item, Message message) {
        assertEquals(item, message.getMessage());
    }
}
