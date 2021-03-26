package gui;

import javax.swing.*;

/**
 * Represents the main window that starts PC Model's internalFrame
 */
public class MainGUI {
    /**
     * Runs the PC Model
     */
    public static void main(String[] args) {
        InternalFrame internalFrame = new InternalFrame();
        internalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        internalFrame.setSize(1800, 1000);
        internalFrame.setVisible(true);
    }
}
