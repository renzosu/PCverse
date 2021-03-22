package model;

import org.json.JSONObject;
import persistence.Writable;

public class AutoSpeed {
    double autoSpeed;

    public AutoSpeed(double autoSpeed) {
        this.autoSpeed = autoSpeed;
    }

    public double getAutoSpeed() {
        return this.autoSpeed;
    }

    public void setAutoSpeed(double newSpeed) {
        this.autoSpeed = newSpeed;
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("auto", autoSpeed);
//        return json;
//    }
}
