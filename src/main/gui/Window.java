package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JWindow {
    JPanel windowPanel = new JPanel();

    public Window() {
        windowPanel.setBackground(Color.DARK_GRAY);
        add(windowPanel);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setSize(1000, 800);
        w.setVisible(true);
    }
}
