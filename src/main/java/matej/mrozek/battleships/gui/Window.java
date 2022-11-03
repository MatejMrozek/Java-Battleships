package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Coordinate;
import matej.mrozek.battleships.CoordinateMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
        this.setResizable(false);
        clear();
    }

    public void clear() {
        this.pack();
        this.setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
        this.panel = new JPanel(null);
        this.setContentPane(panel);
        this.setVisible(true);
    }

    public void loadMap(CoordinateMap coordinateMap) {
        List<Component> components = new ArrayList<>();
        int mapSize = coordinateMap.getSize();
        int gap = 5;
        int componentSize = gap * gap;
        for (int x = 0; x < mapSize; x++) {
            int topTextX = gap + (x + 1) * componentSize + (x + 1) * gap;
            Text topText = new Text(String.valueOf(x + 1), topTextX, gap, componentSize, componentSize);
            components.add(topText);

            for (int y = 0; y < mapSize; y++) {
                int leftTextY = gap + (y + 1) * componentSize + (y + 1) * gap;
                Text leftText = new Text(String.valueOf(y + 1), gap, leftTextY, componentSize, componentSize);
                components.add(leftText);

                int buttonX = (gap * 2 + componentSize) + x * componentSize + x * gap;
                int buttonY = (gap * 2 + componentSize) + y * componentSize + y * gap;
                Button button = new Button(new Coordinate(x, y), buttonX, buttonY, componentSize, componentSize);
                components.add(button);
            }
        }

        for (Component component : components) {
            addComponent(component);
        }

        int size = (gap * 2 + componentSize) + (componentSize * mapSize) + (gap * mapSize);
        this.pack();
        this.setSize(size + 17, size + 40);
        this.setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
    }

    public void showMessage(String title, String text, int messageType) {
        JOptionPane.showMessageDialog(this, text, title, messageType, null);
    }

    public OptionPaneButton showYesNoInput(String title, String text) {
        int option = JOptionPane.showConfirmDialog(this, text, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        return switch (option) {
            case 0 -> OptionPaneButton.Yes;
            case 1 -> OptionPaneButton.No;
            default -> OptionPaneButton.Cancel;
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

    public enum OptionPaneButton {
        Yes,
        No,
        Cancel
    }
}
