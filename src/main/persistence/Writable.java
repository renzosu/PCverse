package persistence;

import org.json.JSONObject;

/**
 * Represents a Writable interface
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
