package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Game app;
 */
public class Game {
    protected int coins;
    private final int piratePrice;
    private int buccaneerPrice;

    private List<CrewMate> crewMates;
    private CrewMate pirate;
    private CrewMate buccaneer;

    protected Timer timer;
    protected int timerSpeed;
    protected double autoPerSec;
    private boolean timerOn;

    // EFFECTS: initializes a Game app
    public Game() {
        coins = 0;
        crewMates = new ArrayList<>();

        piratePrice = 10;
        pirate = new CrewMate("pirate");
        buccaneerPrice = 30;
        buccaneer = new CrewMate("buccaneer");

        timerOn = false;
    }

    // EFFECTS: returns number of coins
    public int getCoins() {
        return coins;
    }

    // Rounding modelled from
    // https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    // EFFECTS: returns auto coin speed
    public double getAutoCoinSpeed() {
        return (double) Math.round(autoPerSec * 10d) / 10d;
        //return autoPerSec;
    }

    // EFFECTS: returns number of crewMates
    public int getNumberOfCrewMates() {
        return crewMates.size();
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

            autoPerSec += 0.1;
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

            autoPerSec += 0.5;
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

        double speed = 1 / autoPerSec * 1000;
        timerSpeed = (int)Math.round(speed);

        setTimer(timerSpeed);
        timer.start();
    }
}
