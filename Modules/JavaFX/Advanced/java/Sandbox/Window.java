package Sandbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Window extends JFrame {

    private int x;
    private int y;
    private int speed;
    private int rectangleSize;

    public Window() { // Constructor for the ObjCtrl class, initializing the GUI window.
        setTitle("W I N D O W");
        setResizable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();

                speed = Math.min(width, height) / 50;

                rectangleSize = Math.min(width, height) / 10;

                checkBounds();

                repaint();
            }
        });

        JPanel panel = getjPanel();

        add(panel);
    }

    private JPanel getjPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.fillRect(x, y, rectangleSize, rectangleSize);
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode()); }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        add(panel);

        return(panel);
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                y = Math.max(y - speed, 0);
                break;
            case KeyEvent.VK_DOWN:
                y = Math.min(y + speed, getHeight() - rectangleSize);
                break;
            case KeyEvent.VK_LEFT:
                x = Math.max(x - speed, 0);
                break;
            case KeyEvent.VK_RIGHT:
                x = Math.min(x + speed, getWidth() - rectangleSize);
                break;
        }

        System.out.println("Coordinates: X = " + x + " Y = " + y + " | Rectangle size: " + rectangleSize + " | Speed: " + speed + " | Window size: width = " + getWidth() + " height = " + getHeight() + " | ");

        checkBounds();

        repaint();

    }

    private void checkBounds() {

        int maxX = getWidth() - rectangleSize - speed;
        int maxY = (getHeight() - rectangleSize - speed) - 19;

        x = Math.max(Math.min(x, maxX), 0);
        y = Math.max(Math.min(y, maxY), 0);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window rectangleMover = new Window();
            rectangleMover.setVisible(true);
        });
    }
}