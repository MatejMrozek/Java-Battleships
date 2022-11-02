package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Coordinate;
import matej.mrozek.battleships.CoordinateMap;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Window extends JFrame {
    private JPanel panel = new JPanel(null);

    private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public Window(String title) {
        init(title);
    }

    private void init(String title) {
        this.setTitle(title);
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/icon.png"))).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(this.screen.width / 2, this.screen.height / 2);
        this.setResizable(false);
        this.setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
        clear();
    }

    public void clear() {
        this.panel = new JPanel(null);
        this.setContentPane(panel);
        this.setVisible(true);
    }

    public void loadMap(CoordinateMap coordinateMap) {
        int mapSize = coordinateMap.getSize();
        boolean resize = true;
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                int buttonSize = 25;
                int buttonX = 5 + x * buttonSize + x * 5;
                int buttonY = 5 + y * buttonSize + y * 5;
                Button button = new Button(new Coordinate(x, y), buttonX, buttonY, buttonSize, buttonSize);
                addComponent(button);
            }

            if (resize) {
                resize = false;
                int size = 5 + (25 * mapSize) + (5 * mapSize);
                this.pack();
                this.setSize(size + 17, size + 40);
                this.setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
            }
        }
    }

    public void showMessage(String title, String text, int messageType) {
        JOptionPane.showMessageDialog(this, text, title, messageType, null);
    }

    public String showYesNoInput(String title, String text) {
        int option = JOptionPane.showConfirmDialog(this, text, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        return switch (option) {
            case 0 -> "yes";
            case 1 -> "no";
            default -> null;
        };
    }

    public String showInput(String title, String text) {
        return (String) JOptionPane.showInputDialog(this, text, title, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    private void addComponent(Component component) {
        this.panel.add(component);
        this.setContentPane(this.panel);
        this.setVisible(true);
    }
}
