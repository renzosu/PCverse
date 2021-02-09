package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Message class
 */
public class MessageTest {
    private Message m1;
    private Message m2;

    @BeforeEach
    void runBefore() {
        m1 = new Message("Hello");
        m2 = new Message("Goodbye");
    }

    @Test
    void testConstructor() {
        assertEquals("Hello", m1.getMessage());
        assertEquals("Goodbye", m2.getMessage());
    }
}
