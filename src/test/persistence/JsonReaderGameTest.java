package persistence;

import model.Game;
import model.CrewMate;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JsonReaderGame class
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
class JsonReaderGameTest extends JsonGameTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderGame reader = new JsonReaderGame("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReaderGame reader = new JsonReaderGame("./data/testReaderGameEmptyGame.json");
        try {
            Game game = reader.read();
            assertEquals(0, game.getNumberOfCrewMates());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReaderGame reader = new JsonReaderGame("./data/testReaderGameGeneralGame.json");
        try {
            Game game = reader.read();
            List<CrewMate> crewMates = game.getCrewMates();

            assertEquals(2, crewMates.size());
            checkCrewMate("pirate", crewMates.get(0));
            checkCrewMate("buccaneer", crewMates.get(1));

            checkAutoSpeed(0.1, game.getAutoCoinSpeed());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}