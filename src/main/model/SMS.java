package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an SMS app: a list of messages
 */
public class SMS implements Writable {
    private List<Message> messages;

    // EFFECTS: initializes an SMS app
    public SMS() {
        messages = new ArrayList<>();
    }

    // EFFECTS: returns list of messages
    public List<Message> getMessages() {
        return messages;
    }

    // MODIFIES: this
    // EFFECTS: adds a message to SMS's list of messages
    public void sendMessage(Message m) {
        messages.add(m);
    }

    // MODIFIES: this
    // EFFECTS: deletes the latest message from SMS's list of messages
    public boolean deleteMessage() {
        if (messages.size() > 0) {
            messages.remove(messages.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: displays all messages in SMS's list of messages
    public String displayMessages() {
        StringBuilder allMessages = new StringBuilder("\n");

        for (Message m: messages) {
            allMessages.append(m.getMessage()).append("\n");
        }

        return allMessages.toString();
    }

    // EFFECTS: displays number of messages in SMS's list of messages
    public int numMessages() {
        return messages.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("messages", messagesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    public JSONArray messagesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Message m : messages) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
