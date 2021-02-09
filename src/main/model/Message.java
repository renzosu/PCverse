package model;

/**
 * Represents a message;
 */
public class Message {
    private String message;

    // EFFECTS: initializes a message and its identifier
    public Message(String message) {
        this.message = message;
    }

    // EFFECTS: returns the identifier for message
    public String getMessage() {
        return message;
    }

}
