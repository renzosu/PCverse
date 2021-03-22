package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents PC Model GUI implementation
 */
public class GUI extends JFrame {
//    JDesktopPane desktop;
//
//    public GUI() {
//
//        // UI components
//        CustomDesktopPane desktop = new CustomDesktopPane();
//        GamePanel gamePanel = new GamePanel();
//        setTitle("MY PC");
//        setSize(1000, 800);
//        setVisible(true);
//    }
//
//    public class CustomDesktopPane extends JFrame {
//        public void displayDesktop(CustomDesktopPane dp) {
//            JInternalFrame internalFrame = new JInternalFrame("My internalFrame 1", true, true, true, true);
//            internalFrame.setBounds(1, 1, 500, 400);
//            Container c1 = internalFrame.getContentPane();
//            c1.add(new JLabel("it's my PC apps:"));
//            dp.add(internalFrame);
//            internalFrame.add(c1);
//            internalFrame.setVisible(true);
//        }
//    }
//
//
//
//    public class GamePanel extends JPanel {
//
//        private int numCoins = 0;
//        private JLabel coinNumLabel;
//        private JLabel coinLabel;
//        private JFrame gameFrame;
//        private JPanel bigPanel;
//
//        public GamePanel() {
//            // Panel
//            bigPanel = new JPanel();
//            //bigPanel.setLayout(new GridLayout(8, 10, 0, 0));
//            bigPanel.setLayout(null);
//
//            //bigPanel.setSize(new Dimension(300, 300));
//            //bigPanel.setLocation(0, 0);
//            //bigPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//
//            //bigPanel.setLayout(new GridBagLayout());
//
//            // Frame for our Game
//            gameFrame = new JFrame();
//            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            gameFrame.setTitle("My Game");
//
//            //gameFrame.pack();
//            gameFrame.setSize(1000, 800);
//            gameFrame.add(bigPanel);
//
//            // Icon for treasure tapping
//            ImageIcon treasurePicIcon = new ImageIcon("./data/treasurePic.png");
//            Image treasurePic = treasurePicIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//            treasurePicIcon = new ImageIcon(treasurePic);
//
//            // Button for treasure tapping
//            JButton treasureButton = new JButton(treasurePicIcon);
//            treasureButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    numCoins++;
//                    coinNumLabel.setForeground(Color.WHITE);
//                    int delayTime = 1 * 250; // 1/4 sec
//                    new Timer(delayTime, e1 -> {
//                        coinNumLabel.setForeground(new Color(255, 204, 51));
//                        // stop the timer
//                        ((Timer) e1.getSource()).stop();
//                    }).start();
//                    coinNumLabel.setText(numCoins + "");
//                }
//            });
//            treasureButton.setBorder(BorderFactory.createEmptyBorder());
//            treasureButton.setContentAreaFilled(false);
//            treasureButton.setBounds(400, 250, 200, 200);
//
//            // Label to display coinNumLabel
//            coinNumLabel = new JLabel("0");
//            coinNumLabel.setBounds(510, 0, 200, 100);
//            coinNumLabel.setForeground(new Color(255, 204, 51));
//            Font coinFont = new Font("font1", Font.BOLD, 30);
//            coinNumLabel.setFont(coinFont);
//
//            // Icon for coin
//            ImageIcon coinPicIcon = new ImageIcon("./data/coinPic.png");
//            Image coinPic = coinPicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//            coinPicIcon = new ImageIcon(coinPic);
//
//            // Label to display coinLabel
//            coinLabel = new JLabel();
//            coinLabel.setIcon(coinPicIcon);
//            coinLabel.setBounds(450, 0, 200, 100);
//
//            //bigPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//            bigPanel.add(treasureButton);
//            bigPanel.add(coinNumLabel);
//            bigPanel.add(coinLabel);
//            //bigPanel.setLayout(new GridLayout(0, 1));
//
//
//            gameFrame.add(bigPanel);
//            //gameFrame.add(bigPanel, BorderLayout.CENTER);
//
//
//            JTextField userText = new JTextField(20);
//            userText.setBounds(300, 300, 165, 50);
//            //bigPanel.add(userText, new GridBagConstraints());
//            gameFrame.setVisible(true);
//        }
//
//
//    }

}
