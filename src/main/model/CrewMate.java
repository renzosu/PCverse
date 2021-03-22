package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a CrewMate
 */
public class CrewMate implements Writable {
    private String crewMate;
    private Game game;

    // EFFECTS: initializes a crewMate and its identifier
    public CrewMate(String crewMate) {
        this.crewMate = crewMate;
    }

    // EFFECTS: returns the identifier for crewMate
    public String getCrewMate() {
        return crewMate;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", crewMate);
        return json;
    }
}
