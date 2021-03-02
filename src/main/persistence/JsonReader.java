package persistence;

import model.CrewMate;
import model.Game;
import model.Message;
import model.SMS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SMS from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SMS read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSMS(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses SMS from JSON object and returns it
    private SMS parseSMS(JSONObject jsonObject) {
        SMS sms = new SMS();
        addMessages(sms, jsonObject);
        return sms;
    }

    // MODIFIES: sms
    // EFFECTS: parses messages from JSON object and adds them to SMS
    private void addMessages(SMS sms, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("messages");
        for (Object json : jsonArray) {
            JSONObject nextMessage = (JSONObject) json;
            addMessage(sms, nextMessage);
        }
    }

    // MODIFIES: sms
    // EFFECTS: parses message from JSON object and adds it to SMS
    private void addMessage(SMS sms, JSONObject jsonObject) {
        String name = jsonObject.getString("message");
        Message message = new Message(name);
        sms.sendMessage(message);
    }
}
