package persistence;

import org.json.JSONObject;

// Represent a interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
