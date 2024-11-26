package RectangleMover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ObjCtrl extends JFrame {

    private int x;
    private int y;

    private int speed;
    private int rectangleSize;

    private static final int DEFAULT_SPEED_FACTOR = 50;
    private static final int DEFAULT_RECTANGLE_SIZE_FACTOR = 10;

    public ObjCtrl() {
        setTitle("W I N D O W");
        setResizable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        JPanel panel = createPanel();
        add(panel);
    }

    private void handleResize() {
        int width = getWidth();
        int height = getHeight();

        speed = Math.min(width, height) / DEFAULT_SPEED_FACTOR;
        rectangleSize = Math.min(width, height) / DEFAULT_RECTANGLE_SIZE_FACTOR;

        checkBounds();

        repaint();
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.fillRect(x ,y, rectangleSize, rectangleSize);
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        return panel;
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

        System.out.println("Coordinates: X = " + x + " Y = " + y + " | Rectangle size: " + rectangleSize + " | Speed: " + speed + " | ");

        checkBounds();
        repaint();

    }

    private void checkBounds() {
        int maxX = getWidth() - rectangleSize;
        int maxY = getHeight() - rectangleSize;

        x = Math.max(Math.min(x, maxX), 0);
        y = Math.max(Math.min(y, maxY), 0);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ObjCtrl rectangleMover = new ObjCtrl();
            rectangleMover.setVisible(true);
        });
    }

}
