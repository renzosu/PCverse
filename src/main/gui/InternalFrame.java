package gui;

import model.Game;
import model.Message;
import model.SMS;
import persistence.JsonReaderGame;
import persistence.JsonReaderSMS;
import persistence.JsonWriterGame;
import persistence.JsonWriterSMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InternalFrame extends JFrame {

    JDesktopPane desktopPane = new JDesktopPane();
    JInternalFrame gameIntFrame = new JInternalFrame("Ahoy Treasure Game");
    JInternalFrame messagingIntFrame = new JInternalFrame("Oasis SMS");

    GamePanel gp = new GamePanel();
    MessagingPanel mp = new MessagingPanel();

    private static final String JSON_STORE_SMS = "./data/SMS.json";
    private static final String JSON_STORE_GAME = "./data/Game.json";
    private JsonWriterSMS jsonWriterSMS;
    private JsonReaderSMS jsonReaderSMS;
    private JsonWriterGame jsonWriterGame;
    private JsonReaderGame jsonReaderGame;

    public InternalFrame() {
        jsonWriterSMS = new JsonWriterSMS(JSON_STORE_SMS);
        jsonReaderSMS = new JsonReaderSMS(JSON_STORE_SMS);

        jsonWriterGame = new JsonWriterGame(JSON_STORE_GAME);
        jsonReaderGame = new JsonReaderGame(JSON_STORE_GAME);

        gameIntFrame.setLocation(50, 50);
        gameIntFrame.setSize(800, 600);
        gameIntFrame.setVisible(true);
        gameIntFrame.setIconifiable(true);

        messagingIntFrame.setLocation(900, 50);
        messagingIntFrame.setSize(800, 600);
        messagingIntFrame.setVisible(true);
        messagingIntFrame.setIconifiable(true);

        desktopPane.add(gameIntFrame);
        desktopPane.add(messagingIntFrame);
        desktopPane.setBackground(Color.PINK);
        add(desktopPane);
    }

    public class GamePanel extends JPanel {

        private Game game;

        private JLabel coinNumLabel;
        private JLabel coinLabel;

        private JLabel pirateNumLabel;
        private JLabel pirateLabel;

        private JLabel buccaneerNumLabel;
        private JLabel buccaneerLabel;

        private JLabel autoNumLabel;
        private JLabel autoLabel;

        public GamePanel()  {
            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(null);
            gamePanel.setBackground(new Color(102, 255, 102));

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

        private void makeLoadSaveGameButtons(JPanel gamePanel) {
            // Button for loading game
            JButton loadGameButton = createLoadGameButton();
            gamePanel.add(loadGameButton);

            // Button for saving game
            JButton saveGameButton = createSaveGameButton();
            gamePanel.add(saveGameButton);
        }

        private void updateCoins() {
            Timer coinTimer = new Timer(1 / 100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    coinNumLabel.setText(game.getCoins() + "");
                }
            });
            coinTimer.start();
        }

        private void createCoinLabel(JPanel gamePanel, JButton treasureButton, ImageIcon coinPicIcon) {
            coinLabel = new JLabel();
            coinLabel.setIcon(coinPicIcon);
            coinLabel.setBounds(350, 0, 200, 100);

            gamePanel.add(treasureButton);
            gamePanel.add(coinNumLabel);
            gamePanel.add(coinLabel);
        }

        private void createCoinNumLabel() {
            coinNumLabel = new JLabel("0");
            coinNumLabel.setBounds(410, 0, 200, 100);
            coinNumLabel.setForeground(new Color(255, 204, 51));
            Font coinFont = new Font("font1", Font.BOLD, 30);
            coinNumLabel.setFont(coinFont);
        }

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

        private void createPirateLabel(JPanel gamePanel, JButton pirateButton, ImageIcon piratePicIcon) {
            pirateLabel = new JLabel();
            pirateLabel.setIcon(piratePicIcon);
            pirateLabel.setBounds(20, 80, 200, 100);

            gamePanel.add(pirateButton);
            gamePanel.add(pirateNumLabel);
            gamePanel.add(pirateLabel);
        }

        private void createPirateNumLabel() {
            pirateNumLabel = new JLabel("0");
            pirateNumLabel.setBounds(80, 80, 200, 100);
            pirateNumLabel.setForeground(new Color(112, 212, 157));
            Font pirateFont = new Font("font1", Font.BOLD, 30);
            pirateNumLabel.setFont(pirateFont);
        }

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

        private void createBuccaneerLabel(JPanel gamePanel, JButton buccaneerButton, ImageIcon buccaneerPicIcon) {
            buccaneerLabel = new JLabel();
            buccaneerLabel.setIcon(buccaneerPicIcon);
            buccaneerLabel.setBounds(20, 130, 200, 100);

            gamePanel.add(buccaneerButton);
            gamePanel.add(buccaneerNumLabel);
            gamePanel.add(buccaneerLabel);
        }

        private void createBuccaneerNumLabel() {
            buccaneerNumLabel = new JLabel("0");
            buccaneerNumLabel.setBounds(80, 130, 200, 100);
            buccaneerNumLabel.setForeground(new Color(77, 171, 232));
            Font buccaneerFont = new Font("font1", Font.BOLD, 30);
            buccaneerNumLabel.setFont(buccaneerFont);
        }

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

        private void createAutoLabel(JPanel gamePanel, ImageIcon autoPicIcon) {
            autoLabel = new JLabel();
            autoLabel.setIcon(autoPicIcon);
            autoLabel.setBounds(20, 0, 200, 100);

            gamePanel.add(autoNumLabel);
            gamePanel.add(autoLabel);
        }

        private void createAutoNumLabel() {
            autoNumLabel = new JLabel("0.0");
            autoNumLabel.setBounds(80, 0, 200, 100);
            autoNumLabel.setForeground(new Color(222, 50, 47));
            Font autoFont = new Font("font1", Font.BOLD, 30);
            autoNumLabel.setFont(autoFont);
        }

        private ImageIcon createTreasurePicIcon() {
            ImageIcon treasurePicIcon = new ImageIcon("./data/treasurePic.png");
            Image treasurePic = treasurePicIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            treasurePicIcon = new ImageIcon(treasurePic);
            return treasurePicIcon;
        }

        private ImageIcon createCoinPicIcon() {
            ImageIcon coinPicIcon = new ImageIcon("./data/coinPic.png");
            Image coinPic = coinPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            coinPicIcon = new ImageIcon(coinPic);
            return coinPicIcon;
        }

        private ImageIcon createPiratePicIcon() {
            ImageIcon piratePicIcon = new ImageIcon("./data/piratePic.png");
            Image piratePic = piratePicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            piratePicIcon = new ImageIcon(piratePic);
            return piratePicIcon;
        }

        private ImageIcon createBuccaneerPicIcon() {
            ImageIcon buccaneerPicIcon = new ImageIcon("./data/buccaneerPic.png");
            Image buccaneerPic = buccaneerPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            buccaneerPicIcon = new ImageIcon(buccaneerPic);
            return buccaneerPicIcon;
        }

        private ImageIcon createAutoPicIcon() {
            ImageIcon autoPicIcon = new ImageIcon("./data/autoPic.png");
            Image autoPic = autoPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            autoPicIcon = new ImageIcon(autoPic);
            return autoPicIcon;
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

    public class MessagingPanel extends JPanel {

        private SMS sms;

        private JLabel partnerNameLabel;
        private JLabel partnerLabel;

        private JLabel infoNumLabel;
        private JLabel infoLabel;

        private JTextField sendMessageField;

        //private JTextArea textArea;
        private Box box;

        private JScrollPane scrollPane;

        public MessagingPanel() {
            JPanel messagingPanel = new JPanel();
            messagingPanel.setLayout(null);
            messagingPanel.setBackground(new Color(51, 204, 255));

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

            // Button for displaying infoNum
            JButton displayInfoButton = createDisplayInfoButton(infoPicIcon);

            // Label to display infoNumLabel
            createInfoNumLabel();

            // Label to display infoLabel
            createInfoLabel(messagingPanel, displayInfoButton);

            // TextArea to display messages
            //TODO: check accuracy, clean up
//            textArea = new JTextArea(20, 10);
//            textArea.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
//            messagingPanel.add(textArea);

            // Box to display messages
            box = Box.createVerticalBox();
            box.setBounds(50, 100, 700, 380);
            messagingPanel.add(box);

            // ScrollPane to allow scrolling
            createScrollPane(messagingPanel, box);

            // Buttons for loading and saving messages
            makeLoadSaveMessagesButton(messagingPanel);

            messagingIntFrame.add(messagingPanel);
        }

        private void makeLoadSaveMessagesButton(JPanel messagingPanel) {
            // Button for loading messages
            JButton loadMessagesButton = createLoadMessagesButton();
            messagingPanel.add(loadMessagesButton);

            // Button for saving messages
            JButton saveMessagesButton = createSaveMessagesButton();
            messagingPanel.add(saveMessagesButton);
        }

        private void createScrollPane(JPanel messagingPanel, Box box) {
            scrollPane = new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            scrollPane.setBounds(50, 100, 700, 380);

            messagingPanel.add(scrollPane);
        }


        private void createPartnerLabel(JPanel messagingPanel, ImageIcon partnerPicIcon) {
            partnerLabel = new JLabel();
            partnerLabel.setIcon(partnerPicIcon);
            partnerLabel.setBounds(20, 0, 200, 100);

            messagingPanel.add(partnerNameLabel);
            messagingPanel.add(partnerLabel);
        }

        private void createPartnerNameLabel() {
            partnerNameLabel = new JLabel("John Doe");
            partnerNameLabel.setBounds(80, 0, 200, 100);
            partnerNameLabel.setForeground(new Color(65, 117, 215));
            Font autoFont = new Font("font1", Font.BOLD, 30);
            partnerNameLabel.setFont(autoFont);
        }

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

        private JButton createSendMessageButton() {
            JButton sendMessageButton = new JButton("Send");
            sendMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int delayTime = 1 / 1000;
                    new Timer(delayTime, e1 -> {
                        String fieldInput = sendMessageField.getText();
                        Message newMessage = new Message(fieldInput);
                        if (!fieldInput.equals("")) {
                            sms.sendMessage(newMessage);
                        }
                        sendMessageField.setText("");
                        displayMessage(box, newMessage.getMessage());
                        // stop the timer
                        ((Timer) e1.getSource()).stop();
                        infoNumLabel.setText(sms.numMessages() + " sent");
                    }).start();
                    //infoNumLabel.setText(sms.numMessages() + " sent");
                }
            });
            sendMessageButton.setBounds(680, 500, 80, 30);
            return sendMessageButton;
        }

        private JTextField createSendMessageField() {
            //TODO: check if "" works as intended, check if columns is same as characters
            JTextField sendMessageField = new JTextField("", 30);

            sendMessageField.setBounds(180, 500, 500, 30);
            return sendMessageField;
        }

        private JButton createDisplayInfoButton(ImageIcon infoPicIcon) {
            JButton displayInfoButton = new JButton(infoPicIcon);
//            displayInfoButton.addActionListener(e -> {
//                int delayTime = 500;
//                new Timer(delayTime, e1 -> {
//                    infoNumLabel.setText("");
//                    // stop the timer
//                    ((Timer) e1.getSource()).stop();
//                }).start();
//                infoNumLabel.setText(sms.numMessages() + " sent");
//            });
            displayInfoButton.setBorder(BorderFactory.createEmptyBorder());
            displayInfoButton.setContentAreaFilled(false);
            displayInfoButton.setBounds(700, 30, 50, 50);
            return displayInfoButton;
        }

        private void createInfoLabel(JPanel messagingPanel, JButton displayInfoButton) {
            infoLabel = new JLabel();
            infoLabel.setBounds(700, 30, 50, 50);

            messagingPanel.add(displayInfoButton);
            messagingPanel.add(infoNumLabel);
            messagingPanel.add(infoLabel);
        }

        private void createInfoNumLabel() {
            infoNumLabel = new JLabel("");
            infoNumLabel.setBounds(630, 10, 75, 75);
            infoNumLabel.setForeground(new Color(0, 0, 0));
            Font autoFont = new Font("font1", Font.PLAIN, 20);
            infoNumLabel.setFont(autoFont);
        }

        private ImageIcon createPartnerPicIcon() {
            ImageIcon partnerPicIcon = new ImageIcon("./data/partnerPic.png");
            Image partnerPic = partnerPicIcon.getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH);
            partnerPicIcon = new ImageIcon(partnerPic);
            return partnerPicIcon;
        }

        private ImageIcon createInfoPicIcon() {
            ImageIcon infoPicIcon = new ImageIcon("./data/infoPic.png");
            Image infoPic = infoPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            infoPicIcon = new ImageIcon(infoPic);
            return infoPicIcon;
        }

        private void displayMessage(Container box, String message) {
            //Box wrapper = Box.createHorizontalBox();
            Box wrapper = new Box(getX());
            wrapper.setBounds(new Rectangle(2000, 200));
            wrapper.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
            wrapper.add(new JLabel(message));
            box.add(wrapper);
        }

        private void removeMessage() {
            int lastMessageIndex = sms.numMessages();
            if (sms.numMessages() >= 1) {
                box.remove(lastMessageIndex);
            } else {
                box.removeAll();
            }
//            box.removeAll();
//            for (int i = 1; i <= box.getComponentCount(); i++) {
//                sms.deleteMessage();
//            }
        }

        private JButton createLoadMessagesButton() {
            JButton loadButton = new JButton("Load");
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.removeAll();
                    doLoadMessages();
                    for (Message m : sms.getMessages()) {
                        displayMessage(box, m.getMessage());
                    }
                }
            });
            loadButton.setBounds(10, 500, 80, 30);
            return loadButton;
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
    }


    public static void main(String[] args) {
        InternalFrame internalFrame = new InternalFrame();
        internalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        internalFrame.setSize(1800, 1000);
        internalFrame.setVisible(true);
    }
}
