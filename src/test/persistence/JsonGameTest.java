package persistence;

import model.CrewMate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Json game reading and writing
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonGameTest {
    protected void checkCrewMate(String item, CrewMate crewMate) {
        assertEquals(item, crewMate.getCrewMate());
    }

    protected void checkAutoSpeed(double item, double autoSpeed) {
        assertEquals(item, autoSpeed);
    }
}
