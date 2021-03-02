package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CrewMate class
 */
public class CrewMateTest {
    private CrewMate pirate;
    private CrewMate buccaneer;

    @BeforeEach
    void runBefore() {
        pirate = new CrewMate("pirate");
        buccaneer = new CrewMate("buccaneer");
    }

    @Test
    void testConstructor() {
        assertEquals("pirate", pirate.getCrewMate());
        assertEquals("buccaneer", buccaneer.getCrewMate());
    }
}
