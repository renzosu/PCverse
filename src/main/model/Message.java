package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a message
 */
public class Message implements Writable {
    private String message;

    // EFFECTS: initializes a message and its identifier
    public Message(String message) {
        this.message = message;
    }

    // EFFECTS: returns the identifier for message
    public String getMessage() {
        return message;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("message", message);
        return json;
    }
}
