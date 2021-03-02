package persistence;

import model.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMessage(String item, Message message) {
        assertEquals(item, message.getMessage());
    }
}
