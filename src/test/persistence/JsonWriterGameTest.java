package persistence;

import model.CrewMate;
import model.Game;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JsonWriterGame class
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonWriterGameTest extends JsonGameTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game();
            JsonWriterSMS writer = new JsonWriterSMS("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGameEmptyGame() {
        try {
            Game game = new Game();

            JsonWriterGame writer = new JsonWriterGame("./data/testWriterGameEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReaderGame reader = new JsonReaderGame("./data/testWriterGameEmptyGame.json");
            game = reader.read();
            assertEquals(0, game.getNumberOfCrewMates());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGameGeneralGame() {
        try {
            Game game = new Game();
            // game.buyPirate();
            // game.buyBuccaneer();

            CrewMate pirate = new CrewMate("pirate");
            CrewMate buccaneer = new CrewMate("buccaneer");
            game.getCrewMates().add(pirate);
            game.getCrewMates().add(buccaneer);
            game.setAutoCoinSpeed(0.1);

            JsonWriterGame writer = new JsonWriterGame("./data/testWriterGameGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReaderGame reader = new JsonReaderGame("./data/testWriterGameGeneralGame.json");
            game = reader.read();
            List<CrewMate> crewMates = game.getCrewMates();
            assertEquals(2, game.getNumberOfCrewMates());
            checkCrewMate("pirate", crewMates.get(0));
            checkCrewMate("buccaneer", crewMates.get(1));
            checkAutoSpeed(0.1, game.getAutoCoinSpeed());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
