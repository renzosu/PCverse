package persistence;

import model.CrewMate;
import model.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/**
 * Represents a reader that reads game from JSON data stored in file
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReaderGame {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderGame(String source) {
        this.source = source;
    }

    // EFFECTS: reads Game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        Game game = new Game();
        addCrewMates(game, jsonObject);
        double autoSpeed = jsonObject.getDouble("auto");
        game.setAutoCoinSpeed(autoSpeed);
        return game;
    }

    // MODIFIES: sms
    // EFFECTS: parses crewMates from JSON object and adds them to Game
    private void addCrewMates(Game game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("crewMates");
        for (Object json : jsonArray) {
            JSONObject nextCrewMate = (JSONObject) json;
            addCrewMate(game, nextCrewMate);
        }
    }

    // MODIFIES: sms
    // EFFECTS: parses crewMate from JSON object and adds it to Game
    private void addCrewMate(Game game, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        CrewMate crewMate = new CrewMate(name);
        if (name.equals("pirate")) {
            game.addCoins(10);
            game.buyPirate();
        }
        if (name.equals("buccaneer")) {
            game.addCoins(50);
            game.buyBuccaneer();
        }
    }
}

