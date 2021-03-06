package gui;

import exceptions.EmptyMessageException;
import model.Game;
import model.Message;
import model.SMS;
import persistence.JsonReaderGame;
import persistence.JsonReaderSMS;
import persistence.JsonWriterGame;
import persistence.JsonWriterSMS;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents a personal computer model with graphical visualizations
 */
public class InternalFrame extends JFrame {
    // PC desktop pane
    private JDesktopPane desktopPane;

    // App frames
    private JInternalFrame gameIntFrame;
    private JInternalFrame messagingIntFrame;

    // App panels
    private GamePanel gp;
    private MessagingPanel mp;

    // JSON parts
    private static final String JSON_STORE_SMS = "./data/SMS.json";
    private static final String JSON_STORE_GAME = "./data/Game.json";
    private JsonWriterSMS jsonWriterSMS;
    private JsonReaderSMS jsonReaderSMS;
    private JsonWriterGame jsonWriterGame;
    private JsonReaderGame jsonReaderGame;

    /**
     * MODIFIES: this
     * EFFECTS: create and display the main frame.
     */
    public InternalFrame() {
        // Initialize JSON parts
        jsonWriterSMS = new JsonWriterSMS(JSON_STORE_SMS);
        jsonReaderSMS = new JsonReaderSMS(JSON_STORE_SMS);

        jsonWriterGame = new JsonWriterGame(JSON_STORE_GAME);
        jsonReaderGame = new JsonReaderGame(JSON_STORE_GAME);

        // Background image for desktopPane
        ImageIcon desktopIcon = new ImageIcon("./data/desktopPic.jpg");
        Image desktopImage = desktopIcon.getImage();

        // Initialize desktopPane with background image
        desktopPane = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(desktopImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Button for opening GameFrame
        JButton openGameFrameButton = createOpenGameFrameButton();
        desktopPane.add(openGameFrameButton);

        // Button for opening MessagingFrame
        JButton openMessagingFrameButton = createOpenMessagingFrameButton();
        desktopPane.add(openMessagingFrameButton);

        // Add desktopPane to main frame
        add(desktopPane);
    }

    /**
     * MODIFIES: this
     * EFFECTS: create a new button that opens a new Game frame
     */
    private JButton createOpenGameFrameButton() {
        ImageIcon openGameFrameIcon = new ImageIcon("./data/gamePic.png");
        Image openGameFrameImage = openGameFrameIcon.getImage().getScaledInstance(100, 100,
                Image.SCALE_SMOOTH);
        openGameFrameIcon = new ImageIcon(openGameFrameImage);

        JButton openGameFrameButton = new JButton(openGameFrameIcon);
        openGameFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameIntFrame = new JInternalFrame("Ahoy Treasure Game");

                gameIntFrame.setLocation(50, 200);
                gameIntFrame.setSize(800, 600);
                gameIntFrame.setVisible(true);
                gameIntFrame.setIconifiable(true);
                gameIntFrame.setClosable(true);

                gp = new GamePanel();

                desktopPane.add(gameIntFrame);
            }
        });
        openGameFrameButton.setBorder(BorderFactory.createEmptyBorder());
        openGameFrameButton.setContentAreaFilled(false);
        openGameFrameButton.setBounds(50, 50, 100, 100);
        return openGameFrameButton;
    }

    /**
     * MODIFIES: this
     * EFFECTS: create a new button that opens a new Messaging frame
     */
    private JButton createOpenMessagingFrameButton() {
        ImageIcon openMessagingFrameIcon = new ImageIcon("./data/smsPic.png");
        Image openMessagingImage = openMessagingFrameIcon.getImage().getScaledInstance(100, 100,
                Image.SCALE_SMOOTH);
        openMessagingFrameIcon = new ImageIcon(openMessagingImage);

        JButton openMessagingFrameButton = new JButton(openMessagingFrameIcon);
        openMessagingFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagingIntFrame = new JInternalFrame("Oasis SMS");

                messagingIntFrame.setLocation(900, 200);
                messagingIntFrame.setSize(800, 600);
                messagingIntFrame.setVisible(true);
                messagingIntFrame.setIconifiable(true);
                messagingIntFrame.setClosable(true);

                mp = new MessagingPanel();

                desktopPane.add(messagingIntFrame);
            }
        });
        openMessagingFrameButton.setBorder(BorderFactory.createEmptyBorder());
        openMessagingFrameButton.setContentAreaFilled(false);
        openMessagingFrameButton.setBounds(200, 50, 100, 100);
        return openMessagingFrameButton;
    }

    /**
     * Represents the components displayed in a Game
     */
    public class GamePanel extends JPanel {
        // Game to be played
        private Game game;

        // Labels to be displayed
        private JLabel coinNumLabel;
        private JLabel coinLabel;
        private JLabel pirateNumLabel;
        private JLabel pirateLabel;
        private JLabel buccaneerNumLabel;
        private JLabel buccaneerLabel;
        private JLabel autoNumLabel;
        private JLabel autoLabel;

        /**
         * MODIFIES: this
         * EFFECTS: Create the display for a Game
         */
        public GamePanel()  {
            // JPanel for gameIntFrame
            JPanel gamePanel = createGameJPanel();
            game = new Game();

            // Icon for treasure chest
            ImageIcon treasurePicIcon = createTreasurePicIcon();

            // Button for treasure tapping
            JButton treasureButton = createTreasureButton(treasurePicIcon);

            // Label to display coinNumLabel
            createCoinNumLabel();

            // Icon for coin
            ImageIcon coinPicIcon = createCoinPicIcon();

            // Label to display coinLabel
            createCoinLabel(gamePanel, treasureButton, coinPicIcon);

            // Icon for pirate
            ImageIcon piratePicIcon = createPiratePicIcon();

            // Button for buying pirate
            JButton pirateButton = createBuyPirateButton(piratePicIcon);

            // Label to display pirateNumLabel
            createPirateNumLabel();

            // Label to display pirateLabel
            createPirateLabel(gamePanel, pirateButton, piratePicIcon);

            // Icon for buccaneer
            ImageIcon buccaneerPicIcon = createBuccaneerPicIcon();

            // Button for buying buccaneer
            JButton buccaneerButton = createBuyBuccaneerButton(buccaneerPicIcon);

            // Label to display buccaneerNumLabel
            createBuccaneerNumLabel();

            // Label to display buccaneerLabel
            createBuccaneerLabel(gamePanel, buccaneerButton, buccaneerPicIcon);

            // Icon for auto
            ImageIcon autoPicIcon = createAutoPicIcon();

            // Label to display autoNumLabel
            createAutoNumLabel();

            // Label to display autoLabel
            createAutoLabel(gamePanel, autoPicIcon);

            // Buttons for loading and saving game
            makeLoadSaveGameButtons(gamePanel);

            // Method for coins to update continuously
            updateCoins();

            gameIntFrame.add(gamePanel);
        }

        /**
         * EFFECTS: create the display for a Game
         */
        private JPanel createGameJPanel() {
            ImageIcon gameBackgroundIcon = new ImageIcon("./data/gameBackgroundPic.png");
            Image gameBackgroundImage = gameBackgroundIcon.getImage();

            JPanel gamePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.drawImage(gameBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            gamePanel.setLayout(null);
            return gamePanel;
        }

        /**
         * MODIFIES: gamePanel
         * EFFECTS: create buttons for saving and loading Game
         */
        private void makeLoadSaveGameButtons(JPanel gamePanel) {
            // Button for loading game
            JButton loadGameButton = createLoadGameButton();
            gamePanel.add(loadGameButton);

            // Button for saving game
            JButton saveGameButton = createSaveGameButton();
            gamePanel.add(saveGameButton);
        }

        /**
         * MODIFIES: this
         * EFFECTS: updates the display of coinNumLabel continuously
         */
        private void updateCoins() {
            Timer coinTimer = new Timer(1 / 100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    coinNumLabel.setText(game.getCoins() + "");
                }
            });
            coinTimer.start();
        }

        /**
         * MODIFIES: this, gamePanel
         * EFFECTS: creates label for displaying coin parts
         */
        private void createCoinLabel(JPanel gamePanel, JButton treasureButton, ImageIcon coinPicIcon) {
            coinLabel = new JLabel();
            coinLabel.setIcon(coinPicIcon);
            coinLabel.setBounds(350, 0, 200, 100);

            gamePanel.add(treasureButton);
            gamePanel.add(coinNumLabel);
            gamePanel.add(coinLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying coin number
         */
        private void createCoinNumLabel() {
            coinNumLabel = new JLabel("0");
            coinNumLabel.setBounds(410, 0, 200, 100);
            coinNumLabel.setForeground(new Color(255, 204, 51));
            Font coinFont = new Font("font1", Font.BOLD, 30);
            coinNumLabel.setFont(coinFont);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates button for tapping treasure
         */
        private JButton createTreasureButton(ImageIcon treasurePicIcon) {
            JButton treasureButton = new JButton(treasurePicIcon);
            treasureButton.addActionListener(e -> {
                game.tapTreasure();
                coinNumLabel.setForeground(Color.WHITE);
                int delayTime = 250; // 0.25 of a second
                new Timer(delayTime, e1 -> {
                    coinNumLabel.setForeground(new Color(255, 204, 51));
                    // stop the timer
                    ((Timer) e1.getSource()).stop();
                }).start();
                coinNumLabel.setText(game.getCoins() + "");
            });
            treasureButton.setBorder(BorderFactory.createEmptyBorder());
            treasureButton.setContentAreaFilled(false);
            treasureButton.setBounds(300, 250, 200, 200);
            return treasureButton;
        }

        /**
         * MODIFIES: this, gamePanel
         * EFFECTS: creates label for displaying pirate parts
         */
        private void createPirateLabel(JPanel gamePanel, JButton pirateButton, ImageIcon piratePicIcon) {
            pirateLabel = new JLabel();
            pirateLabel.setIcon(piratePicIcon);
            pirateLabel.setBounds(20, 80, 200, 100);

            gamePanel.add(pirateButton);
            gamePanel.add(pirateNumLabel);
            gamePanel.add(pirateLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying pirate number
         */
        private void createPirateNumLabel() {
            pirateNumLabel = new JLabel("0");
            pirateNumLabel.setBounds(80, 80, 200, 100);
            pirateNumLabel.setForeground(new Color(67, 229, 229));
            Font pirateFont = new Font("font1", Font.BOLD, 30);
            pirateNumLabel.setFont(pirateFont);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates button for buying pirates
         */
        private JButton createBuyPirateButton(ImageIcon piratePicIcon) {
            JButton pirateButton = new JButton("Buy Pirate (10c)");
            pirateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.buyPirate()) {
                        coinNumLabel.setText(game.getCoins() + "");
                        pirateNumLabel.setText(game.getNumberOfPirates() + "");
                        autoNumLabel.setText(game.getAutoCoinSpeed() + "");
                    }
                }
            });
            pirateButton.setBounds(10, 350, 160, 30);
            return pirateButton;
        }

        /**
         * MODIFIES: this, gamePanel
         * EFFECTS: creates label for displaying buccaneer parts
         */
        private void createBuccaneerLabel(JPanel gamePanel, JButton buccaneerButton, ImageIcon buccaneerPicIcon) {
            buccaneerLabel = new JLabel();
            buccaneerLabel.setIcon(buccaneerPicIcon);
            buccaneerLabel.setBounds(20, 130, 200, 100);

            gamePanel.add(buccaneerButton);
            gamePanel.add(buccaneerNumLabel);
            gamePanel.add(buccaneerLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying buccaneer number
         */
        private void createBuccaneerNumLabel() {
            buccaneerNumLabel = new JLabel("0");
            buccaneerNumLabel.setBounds(80, 130, 200, 100);
            buccaneerNumLabel.setForeground(new Color(133, 222, 81));
            Font buccaneerFont = new Font("font1", Font.BOLD, 30);
            buccaneerNumLabel.setFont(buccaneerFont);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates button for buying buccaneers
         */
        private JButton createBuyBuccaneerButton(ImageIcon buccaneerPicIcon) {
            JButton buccaneerButton = new JButton("Buy Buccaneer (50c)");
            buccaneerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.buyBuccaneer()) {
                        coinNumLabel.setText(game.getCoins() + "");
                        buccaneerNumLabel.setText(game.getNumberOfBuccaneers() + "");
                        autoNumLabel.setText(game.getAutoCoinSpeed() + "");
                    }
                }
            });
            buccaneerButton.setBounds(10, 380, 160, 30);
            return buccaneerButton;
        }

        /**
         * MODIFIES: this, gamePanel
         * EFFECTS: creates label for displaying auto parts
         */
        private void createAutoLabel(JPanel gamePanel, ImageIcon autoPicIcon) {
            autoLabel = new JLabel();
            autoLabel.setIcon(autoPicIcon);
            autoLabel.setBounds(20, 0, 200, 100);

            gamePanel.add(autoNumLabel);
            gamePanel.add(autoLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying auto number
         */
        private void createAutoNumLabel() {
            autoNumLabel = new JLabel("0.0");
            autoNumLabel.setBounds(80, 0, 200, 100);
            autoNumLabel.setForeground(new Color(243, 104, 16));
            Font autoFont = new Font("font1", Font.BOLD, 30);
            autoNumLabel.setFont(autoFont);
        }

        /**
         * EFFECTS: creates image icon for treasure pic
         */
        private ImageIcon createTreasurePicIcon() {
            ImageIcon treasurePicIcon = new ImageIcon("./data/treasurePic.png");
            Image treasurePic = treasurePicIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            treasurePicIcon = new ImageIcon(treasurePic);
            return treasurePicIcon;
        }

        /**
         * EFFECTS: creates image icon for coin pic
         */
        private ImageIcon createCoinPicIcon() {
            ImageIcon coinPicIcon = new ImageIcon("./data/coinPic.png");
            Image coinPic = coinPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            coinPicIcon = new ImageIcon(coinPic);
            return coinPicIcon;
        }

        /**
         * EFFECTS: creates image icon for pirate pic
         */
        private ImageIcon createPiratePicIcon() {
            ImageIcon piratePicIcon = new ImageIcon("./data/piratePic.png");
            Image piratePic = piratePicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            piratePicIcon = new ImageIcon(piratePic);
            return piratePicIcon;
        }

        /**
         * EFFECTS: creates image icon for buccaneer pic
         */
        private ImageIcon createBuccaneerPicIcon() {
            ImageIcon buccaneerPicIcon = new ImageIcon("./data/buccaneerPic.png");
            Image buccaneerPic = buccaneerPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            buccaneerPicIcon = new ImageIcon(buccaneerPic);
            return buccaneerPicIcon;
        }

        /**
         * EFFECTS: creates image icon for auto pic
         */
        private ImageIcon createAutoPicIcon() {
            ImageIcon autoPicIcon = new ImageIcon("./data/autoPic.png");
            Image autoPic = autoPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            autoPicIcon = new ImageIcon(autoPic);
            return autoPicIcon;
        }

        /**
         * MODIFIES: this
         * EFFECTS: saves game to file
         */
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

        /**
         * MODIFIES: this
         * EFFECTS: create button for saving Game
         */
        private JButton createSaveGameButton() {
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doSaveGame();
                }
            });
            saveButton.setBounds(10, 530, 80, 30);
            return saveButton;
        }

        /**
         * MODIFIES: this
         * EFFECTS: loads game from file
         */
        private void doLoadGame() {
            try {
                game = jsonReaderGame.read();
                System.out.println("Loaded game" + " from " + JSON_STORE_GAME);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE_GAME);
            }
        }

        /**
         * MODIFIES: this
         * EFFECTS: create button for loading Game
         */
        private JButton createLoadGameButton() {
            JButton loadButton = new JButton("Load");
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doLoadGame();
                    autoNumLabel.setText(game.getAutoCoinSpeed() + "");
                    pirateNumLabel.setText(game.getNumberOfPirates() + "");
                    buccaneerNumLabel.setText(game.getNumberOfBuccaneers() + "");
                }
            });
            loadButton.setBounds(10, 500, 80, 30);
            return loadButton;
        }
    }

    /**
     * Represents the components displayed in an SMS
     */
    public class MessagingPanel extends JPanel {
        // SMS to be used
        private SMS sms;

        // Labels to be displayed
        private JLabel partnerNameLabel;
        private JLabel partnerLabel;
        private JLabel infoNumLabel;
        private JLabel infoLabel;

        // Components for text area
        private JTextField sendMessageField;
        private Box box;
        private JScrollPane scrollPane;

        /**
         * MODIFIES: this
         * EFFECTS: Create the display for an SMS
         */
        public MessagingPanel() {
            // JPanel for messagingIntFrame
            JPanel messagingPanel = createMessagingJPanel();
            sms = new SMS();

            // Icon for partner
            ImageIcon partnerPicIcon = createPartnerPicIcon();

            // Label to display partnerNameLabel
            createPartnerNameLabel();

            // Label to display partnerLabel
            createPartnerLabel(messagingPanel, partnerPicIcon);

            // Button for deleting messages
            JButton deleteMessageButton = createDeleteMessageButton();
            messagingPanel.add(deleteMessageButton);

            // Button for sending messages
            JButton sendMessageButton = createSendMessageButton();
            messagingPanel.add(sendMessageButton);

            // Text field for sending messages
            sendMessageField = createSendMessageField();
            messagingPanel.add(sendMessageField);

            // Icon for info
            ImageIcon infoPicIcon = createInfoPicIcon();

            // Label to display infoNumLabel
            createInfoNumLabel();

            // Label to display infoLabel
            createInfoLabel(messagingPanel, infoPicIcon);

            // Big Box to display messages
            box = Box.createVerticalBox();
            box.setBounds(50, 100, 700, 380);
            messagingPanel.add(box);

            // ScrollPane to allow scrolling
            createScrollPane(messagingPanel, box);

            // Buttons for loading and saving messages
            makeLoadSaveMessagesButtons(messagingPanel);

            // InternalFrameListener for closing messagingIntFrame
            addMessagingClosingListener();

            messagingIntFrame.add(messagingPanel);
        }

        /**
         * EFFECTS: create the display for an SMS
         */
        private JPanel createMessagingJPanel() {
            ImageIcon messagingBackgroundIcon = new ImageIcon("./data/messagingBackgroundPic.jpg");
            Image messagingBackgroundImage = messagingBackgroundIcon.getImage();

            JPanel messagingPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.drawImage(messagingBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
            messagingPanel.setLayout(null);
            return messagingPanel;
        }

        /**
         * MODIFIES: this
         * EFFECTS: add a listener for closing messaging Frame
         */
        private void addMessagingClosingListener() {
            messagingIntFrame.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosing(InternalFrameEvent e) {
                    super.internalFrameClosing(e);
                    windowCloseMethod(messagingIntFrame);
                }
            });
        }

        /**
         * MODIFIES: this
         * EFFECTS: create display of JOptionPane when closing messaging Frame
         */
        private void windowCloseMethod(JInternalFrame frame) {
            int result = JOptionPane.showConfirmDialog(frame, "Do you want to save messages?",
                    "Select an option", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                doSaveMessages();
                setVisible(false);
                frame.dispose();
            } else if (result == JOptionPane.NO_OPTION) {
                setVisible(false);
                frame.dispose();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                setVisible(true);
            }
        }

        /**
         * MODIFIES: messagingPanel
         * EFFECTS: create buttons for saving and loading SMS
         */
        private void makeLoadSaveMessagesButtons(JPanel messagingPanel) {
            // Button for loading messages
            JButton loadMessagesButton = createLoadMessagesButton();
            messagingPanel.add(loadMessagesButton);

            // Button for saving messages
            JButton saveMessagesButton = createSaveMessagesButton();
            messagingPanel.add(saveMessagesButton);
        }

        /**
         * MODIFIES: this, messagingPanel
         * EFFECTS: creates scroll pane for text area
         */
        private void createScrollPane(JPanel messagingPanel, Box box) {
            scrollPane = new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            scrollPane.setBounds(50, 100, 700, 380);

            messagingPanel.add(scrollPane);
        }

        /**
         * MODIFIES: this, messagingPanel
         * EFFECTS: creates label for displaying partner parts
         */
        private void createPartnerLabel(JPanel messagingPanel, ImageIcon partnerPicIcon) {
            partnerLabel = new JLabel();
            partnerLabel.setIcon(partnerPicIcon);
            partnerLabel.setBounds(20, 0, 200, 100);

            messagingPanel.add(partnerNameLabel);
            messagingPanel.add(partnerLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying partner name
         */
        private void createPartnerNameLabel() {
            partnerNameLabel = new JLabel("John Doe");
            partnerNameLabel.setBounds(80, 0, 200, 100);
            partnerNameLabel.setForeground(new Color(58, 189, 137));
            Font autoFont = new Font("font1", Font.BOLD, 30);
            partnerNameLabel.setFont(autoFont);
        }

        /**
         * MODIFIES: this
         * EFFECTS: create button for deleting messages
         */
        private JButton createDeleteMessageButton() {
            JButton deleteMessageButton = new JButton("Delete");
            deleteMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sms.deleteMessage();
                    removeMessage();
                }
            });
            deleteMessageButton.setBounds(680, 530, 80, 30);
            return deleteMessageButton;
        }

        /**
         * MODIFIES: this
         * EFFECTS: create button for sending messages
         */
        private JButton createSendMessageButton() {
            JButton sendMessageButton = new JButton("Send");
            sendMessageButton.addActionListener(e -> {
                int delayTime = 1 / 1000;
                new Timer(delayTime, e1 -> {
                    String fieldInput = sendMessageField.getText();
                    Message newMessage = new Message(fieldInput);
                    if (!fieldInput.equals("")) {
                        try {
                            sms.sendMessage(newMessage);
                        } catch (EmptyMessageException exception) {
                            System.err.println("MESSAGE CONTENTS CANNOT BE EMPTY!");
                        }
                        displayMessage(newMessage.getMessage());
                    }
                    sendMessageField.setText("");
                    // stop the timer
                    ((Timer) e1.getSource()).stop();
                    infoNumLabel.setText(sms.numMessages() + " sent");
                }).start();
            });
            sendMessageButton.setBounds(680, 500, 80, 30);
            return sendMessageButton;
        }

        /**
         * EFFECTS: creates text field for text area
         */
        private JTextField createSendMessageField() {
            JTextField sendMessageField = new JTextField("", 30);

            sendMessageField.setBounds(180, 500, 500, 30);
            return sendMessageField;
        }

        /**
         * MODIFIES: this, messagingPanel
         * EFFECTS: creates label for displaying info parts
         */
        private void createInfoLabel(JPanel messagingPanel, ImageIcon infoPicIcon) {
            infoLabel = new JLabel();
            infoLabel.setIcon(infoPicIcon);
            infoLabel.setBounds(700, 30, 50, 50);

            messagingPanel.add(infoNumLabel);
            messagingPanel.add(infoLabel);
        }

        /**
         * MODIFIES: this
         * EFFECTS: creates label for displaying info number
         */
        private void createInfoNumLabel() {
            infoNumLabel = new JLabel("");
            infoNumLabel.setBounds(630, 15, 75, 75);
            infoNumLabel.setForeground(new Color(73, 69, 69, 255));
            Font autoFont = new Font("font1", Font.PLAIN, 20);
            infoNumLabel.setFont(autoFont);
        }

        /**
         * EFFECTS: creates image icon for partner pic
         */
        private ImageIcon createPartnerPicIcon() {
            ImageIcon partnerPicIcon = new ImageIcon("./data/partnerPic.png");
            Image partnerPic = partnerPicIcon.getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH);
            partnerPicIcon = new ImageIcon(partnerPic);
            return partnerPicIcon;
        }

        /**
         * EFFECTS: creates image icon for info pic
         */
        private ImageIcon createInfoPicIcon() {
            ImageIcon infoPicIcon = new ImageIcon("./data/infoPic.png");
            Image infoPic = infoPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            infoPicIcon = new ImageIcon(infoPic);
            return infoPicIcon;
        }

        /**
         * MODIFIES: this
         * EFFECTS: displays messages to text area
         */
        private void displayMessage(String message) {
            Box wrapper = new Box(getX());
            wrapper.setBounds(new Rectangle(2000, 200));
            wrapper.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
            wrapper.add(new JLabel(message));
            box.add(wrapper);
        }

        /**
         * MODIFIES: this
         * EFFECTS: removes messages from text area
         */
        private void removeMessage() {
            box.removeAll();
            if (sms.numMessages() == 0) {
                displayMessage("");
            }
            for (Message m : sms.getMessages()) {
                displayMessage(m.getMessage());
            }
            infoNumLabel.setText(sms.numMessages() + " sent");
        }

        /**
         * MODIFIES: this
         * EFFECTS: create button for loading messages
         */
        private JButton createLoadMessagesButton() {
            JButton loadButton = new JButton("Load");
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.removeAll();
                    doLoadMessages();
                    for (Message m : sms.getMessages()) {
                        displayMessage(m.getMessage());
                    }
                    infoNumLabel.setText(sms.numMessages() + " sent");
                }
            });
            loadButton.setBounds(10, 500, 80, 30);
            return loadButton;
        }

        /**
         * MODIFIES: this
         * EFFECTS: loads messages from file
         */
        private void doLoadMessages() {
            try {
                sms = jsonReaderSMS.read();
                System.out.println("Loaded messages" + " from " + JSON_STORE_SMS);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE_SMS);
            }
        }

        /**
         * MODIFIES: this
         * EFFECTS: create button for saving messages
         */
        private JButton createSaveMessagesButton() {
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doSaveMessages();
                }
            });
            saveButton.setBounds(10, 530, 80, 30);
            return saveButton;
        }

        /**
         * MODIFIES: this
         * EFFECTS: saves game to file
         */
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
    }

}