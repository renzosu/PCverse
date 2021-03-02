package persistence;

import org.json.JSONObject;

// Modelled based on TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
