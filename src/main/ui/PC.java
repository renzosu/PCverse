package ui;

import model.Game;
import model.Message;
import model.SMS;
import persistence.JsonReaderSMS;
import persistence.JsonReaderGame;
import persistence.JsonWriterSMS;
import persistence.JsonWriterGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * Represents a personal computer model
 * User input based on TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */
public class PC {
    private static final String JSON_STORE_SMS = "./data/SMS.json";
    private static final String JSON_STORE_GAME = "./data/Game.json";
    private SMS sms;
    private Game game;
    private Scanner input;
    private JsonWriterSMS jsonWriterSMS;
    private JsonReaderSMS jsonReaderSMS;
    private JsonWriterGame jsonWriterGame;
    private JsonReaderGame jsonReaderGame;


    // EFFECTS: runs the PC model
    public PC() {
        jsonWriterSMS = new JsonWriterSMS(JSON_STORE_SMS);
        jsonReaderSMS = new JsonReaderSMS(JSON_STORE_SMS);
        jsonWriterGame = new JsonWriterGame(JSON_STORE_GAME);
        jsonReaderGame = new JsonReaderGame(JSON_STORE_GAME);
        runPC();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for PC desktop
    private void runPC() {
        boolean keepGoing = true;
        String command;

        init();

        System.out.println("\nBooting up... \nWelcome to your PC experience!");

        while (keepGoing) {
            displayMenuPC();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommandPC(command);
            }
        }
        System.out.println("\nSystem shutting down... \nSee you next time!");
    }

    // MODIFIES: this
    // EFFECTS: initializes SMS app and Game app
    private void init() {
        sms = new SMS();
        game = new Game();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of PC desktop options to user
    private void displayMenuPC() {
        System.out.println("\nDisplaying desktop... \nPlease select from the following options:");
        System.out.println("\ts -> open SMS app");
        System.out.println("\tg -> open Game app");
        System.out.println("\tq -> power down PC");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for PC
    private void processCommandPC(String command) {
        switch (command) {
            case "s":
                doOpenSMS();
                break;
            case "g":
                doOpenGame();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: opens and processes user input for SMS app
    private void doOpenSMS() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenuSMS();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                doQuitSaveProcedure();
                keepGoing = false;
            } else {
                processCommandSMS(command);
            }
        }
        System.out.println("\nLeaving SMS app...");

    }

    private void doQuitSaveProcedure() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayQuitSave();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommandQuitSave(command);
                break;
            }
        }
    }

    // EFFECTS: displays menu of QuitSave options to user
    private void displayQuitSave() {
        System.out.println("Would you like to save messages before quitting?");
        System.out.println("\ty -> yes, save and quit");
        System.out.println("\tn -> no, quit without saving");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for QuitSave
    private void processCommandQuitSave(String command) {
        switch (command) {
            case "y":
                doSaveMessages();
                break;
            case "n":
                break;
            default:
                System.out.println("Selection not valid...");
                doQuitSaveProcedure();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for SMS app
    private void processCommandSMS(String command) {
        switch (command) {
            case "s":
                doSendMessage();
                break;
            case "d":
                doDeleteMessage();
                break;
            case "v":
                doDisplayMessages();
                break;
            case "n":
                doDisplaySize();
                break;
            case "z":
                doSaveMessages();
                break;
            case "x":
                doLoadMessages();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: displays menu of SMS options to user
    private void displayMenuSMS() {
        System.out.println("\nOasis SMS app \nConversation with John Doe:");
        System.out.println("\ts -> send a message");
        System.out.println("\td -> delete a message");
        System.out.println("\tv -> view all sent messages");
        System.out.println("\tn -> view number of sent messages");
        System.out.println("\tz -> save messages to file");
        System.out.println("\tx -> load messages from file");
        System.out.println("\tq -> go back to PC desktop");
    }

    // MODIFIES: this
    // EFFECTS: conducts sending of new message
    private void doSendMessage() {
        System.out.print("Enter new message contents: ");

        Scanner scan = new Scanner(System.in);
        String input = "";

        input += scan.nextLine();
        Message newMessage = new Message(input);

        sms.sendMessage(newMessage);
    }

    // MODIFIES: this
    // EFFECTS: conducts deletion of last message if there is a message, else unable to
    private void doDeleteMessage() {
        System.out.print("Deleting last message... ");
        if (sms.deleteMessage()) {
            System.out.println("\nSuccess!\n");
        } else {
            System.out.println("\nNo messages to delete!");
        }
    }

    // EFFECTS: conducts display of all messages sent in SMS
    private void doDisplayMessages() {
        System.out.print("Viewing all messages... ");
        System.out.print(sms.displayMessages());
    }

    // EFFECTS: conducts display of number of messages sent in SMS
    private void doDisplaySize() {
        System.out.print("Total number of sent messages: ");
        System.out.println(sms.numMessages());
    }

    // EFFECTS: saves messages to file
    private void doSaveMessages() {
        try {
            jsonWriterSMS.open();
            jsonWriterSMS.write(sms);
            jsonWriterSMS.close();
            System.out.println("Saved messages" + " to " + JSON_STORE_SMS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_SMS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads messages from file
    private void doLoadMessages() {
        try {
            sms = jsonReaderSMS.read();
            System.out.println("Loaded messages" + " from " + JSON_STORE_SMS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_SMS);
        }
    }

    // MODIFIES: this
    // EFFECTS: opens and processes user input for Game app
    private void doOpenGame() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenuGame();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommandGame(command);
            }
        }
        System.out.println("\nLeaving Game app...");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for Game app
    private void processCommandGame(String command) {
        if (command.equals("c")) {
            doCheckCoins();
        } else if (command.equals("t")) {
            doTapTreasure();
        } else if (command.equals("p")) {
            doBuyPirate();
        } else if (command.equals("b")) {
            doBuyBuccaneer();
        } else if (command.equals("v")) {
            doDisplaySpeed();
        } else if (command.equals("n")) {
            doDisplayCrewMates();
        } else if (command.equals("z")) {
            doSaveGame();
        } else if (command.equals("x")) {
            doLoadGame();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of Game options to user
    private void displayMenuGame() {
        System.out.println("\nAhoy Treasure Game app \nYe current voyage:");
        System.out.println("\tc -> check coins ");
        System.out.println("\tt -> tap treasure");
        System.out.println("\tp -> buy pirate for 10 coins");
        System.out.println("\tb -> buy buccaneer for 30 coins");
        System.out.println("\tv -> view auto coin speed");
        System.out.println("\tn -> view crewMate composition");
        System.out.println("\tz -> save game to file");
        System.out.println("\tx -> load game from file");
        System.out.println("\tq -> go back to PC desktop");
    }

    // EFFECTS: conducts tapping of treasure
    private void doCheckCoins() {
        System.out.println("Your total coins are: " + game.getCoins());
    }

    // MODIFIES: this
    // EFFECTS: conducts tapping of treasure
    private void doTapTreasure() {
        game.tapTreasure();
        System.out.println("Treasure has been tapped");
    }

    // MODIFIES: this
    // EFFECTS: conducts purchase of a pirate if player has sufficient coins, else unable to
    private void doBuyPirate() {
        if (game.buyPirate()) {
            System.out.println("Pirate purchased successfully");
        } else {
            System.out.println("Insufficient funds to buy pirate (costs 10 coins)");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts purchase of a buccaneer if player has sufficient coins, else unable to
    private void doBuyBuccaneer() {
        if (game.buyBuccaneer()) {
            System.out.println("Buccaneer purchased successfully");
        } else {
            System.out.println("Insufficient funds to buy buccaneer (costs 30 coins)");
        }
    }

    // EFFECTS: conducts display of current auto coin speed
    private void doDisplaySpeed() {
        System.out.println("Your current auto coin speed in seconds is: " + game.getAutoCoinSpeed());
    }

    // EFFECTS: conducts display of current number of crewMates and its composition
    private void doDisplayCrewMates() {
        System.out.println("Your current crewMate count is: " + game.getNumberOfCrewMates());
        System.out.println("There are " + game.getNumberOfPirates() + " pirates and "
                + game.getNumberOfBuccaneers() + " buccaneers on board");
    }

    // EFFECTS: saves game to file
    private void doSaveGame() {
        try {
            jsonWriterGame.open();
            jsonWriterGame.write(game);
            jsonWriterGame.close();
            System.out.println("Saved game" + " to " + JSON_STORE_GAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_GAME);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void doLoadGame() {
        try {
            game = jsonReaderGame.read();
            System.out.println("Loaded game" + " from " + JSON_STORE_GAME);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_GAME);
        }
    }

}
