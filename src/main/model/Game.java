package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Game app
 */
public class Game implements Writable {
    protected int coins;
    private final int piratePrice;
    private int buccaneerPrice;

    private List<CrewMate> crewMates;
    private CrewMate pirate;
    private CrewMate buccaneer;

    protected Timer timer;
    protected int timerSpeed;

    protected AutoSpeed autoSpeed;
    //protected double autoPerSec;

    private boolean timerOn;

    // EFFECTS: initializes a Game app
    public Game() {
        autoSpeed = new AutoSpeed(0.0);
        coins = 0;
        crewMates = new ArrayList<>();

        piratePrice = 10;
        pirate = new CrewMate("pirate");
        buccaneerPrice = 50;
        buccaneer = new CrewMate("buccaneer");

        timerOn = false;
    }

    // EFFECTS: returns number of coins
    public int getCoins() {
        return coins;
    }

    // EFFECTS: adds number of coins
    public void addCoins(int coins) {
        this.coins += coins;
    }

    // Rounding modelled from
    // https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    // EFFECTS: returns auto coin speed
    public double getAutoCoinSpeed() {
        return (double) Math.round(autoSpeed.getAutoSpeed() * 10d) / 10d;
        //return autoPerSec;
    }

    // REQUIRES: speed > 0;
    // MODIFIES: this
    // EFFECTS: sets auto coin speed
    public void setAutoCoinSpeed(double speed) {
//        this.autoPerSec = speed;
        autoSpeed.setAutoSpeed(speed);
    }

    // EFFECTS: returns number of crewMates
    public int getNumberOfCrewMates() {
        return crewMates.size();
    }

    // EFFECTS: returns crewMates
    public List<CrewMate> getCrewMates() {
        return crewMates;
    }

    // EFFECTS: returns number of pirates
    public int getNumberOfPirates() {
        int numPirates = 0;
        for (CrewMate c: crewMates) {
            if (c.equals(pirate)) {
                numPirates++;
            }
        }
        return numPirates;
    }

    // EFFECTS: returns number of buccaneers
    public int getNumberOfBuccaneers() {
        int numBuccaneers = 0;
        for (CrewMate c: crewMates) {
            if (c.equals(buccaneer)) {
                numBuccaneers++;
            }
        }
        return numBuccaneers;
    }

    // EFFECTS: returns timerSpeed
    public int getTimerSpeed() {
        return timerSpeed;
    }

    // MODIFIES: this
    // EFFECTS: taps treasure to increase coins by one
    public void tapTreasure() {
        coins++;
    }

    // MODIFIES: this
    // EFFECTS: returns true if able to successfully buy a pirate
    // and adds pirate to list of crewMate, false otherwise.
    public boolean buyPirate() {
        if (coins >= piratePrice) {
            crewMates.add(pirate);
            coins -= piratePrice;

            //autoPerSec += 0.1;
            double newSpeed = getAutoCoinSpeed() + 0.1;
            setAutoCoinSpeed(newSpeed);

            updateTimerSettings();

            //setTimer();
            //timer.start();
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if able to successfully buy a buccaneer,
    // and adds buccaneer to list of crewMate, false otherwise.
    public boolean buyBuccaneer() {
        if (coins >= buccaneerPrice) {
            crewMates.add(buccaneer);
            coins -= buccaneerPrice;

            //autoPerSec += 0.5;
            double newSpeed = getAutoCoinSpeed() + 0.5;
            setAutoCoinSpeed(newSpeed);

            updateTimerSettings();

            //setTimer();
            //timer.start();
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new tapping timer with given timerSpeed
    public void setTimer(int ts) {
        timer = new Timer(ts, e -> coins++);
    }

    // Timer speed and update modelled from
    // https://www.youtube.com/watch?v=gU8CUB-13ak&ab_channel=RyiSnow
    // MODIFIES: this
    // EFFECTS: updates the timerSpeed used for the tapping timer
    public void updateTimerSettings() {
        if (!timerOn) {
            timerOn = true;
        } else {
            timer.stop();
        }

        double speed = 1 / autoSpeed.getAutoSpeed() * 1000;
        timerSpeed = (int)Math.round(speed);

        setTimer(timerSpeed);
        timer.start();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("crewMates", crewMatesToJson());
        json.put("auto", autoSpeed.getAutoSpeed());
        return json;
    }

    // EFFECTS: returns things in this game as a JSON array
    public JSONArray crewMatesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CrewMate c : crewMates) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
