package model;

/**
 * Represents a crewmate;
 */
public class CrewMate {
    private String crewMate;

    // EFFECTS: initializes a crewMate and its identifier
    public CrewMate(String crewMate) {
        this.crewMate = crewMate;
    }

    // EFFECTS: returns the identifier for crewMate
    public String getCrewMate() {
        return crewMate;
    }
}
