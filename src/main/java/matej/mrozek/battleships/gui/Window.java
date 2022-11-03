package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Coordinate;
import matej.mrozek.battleships.CoordinateMap;
import matej.mrozek.battleships.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Window extends JFrame implements ComponentListener {
    public Debug DEBUG;

    public JPanel panel;

    public final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public Window(Debug debug, String title) {
        init(debug, title);

        addComponentListener(this);
    }

    public void init(Debug debug, String title, int closeOperation) {
        this.DEBUG = debug;

        setTitle(title);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/icon.png"))).getImage());
        setDefaultCloseOperation(closeOperation);
        clear();
    }

    public void init(Debug debug, String title) {
        init(debug, title, JFrame.EXIT_ON_CLOSE);
    }

    public void clear() {
        panel = new JPanel(null);
        setContentPane(panel);
        setVisible(true);
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
        int width = size + 17;
        int height = size + 40;
        pack();
        setSize(width, height);
        setLocation(screen.width / 2 - width / 2, screen.height / 2 - height / 2);
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

    public void addComponent(Component component, boolean clearPanel) {
        if (clearPanel) {
            panel = new JPanel();
        }

        panel.add(component);
        setContentPane(panel);
        setVisible(true);
    }

    public void addComponent(Component component) {
        addComponent(component, false);
    }

    @Override
    public void componentResized(ComponentEvent event) {}

    @Override
    public void componentMoved(ComponentEvent event) {}

    @Override
    public void componentShown(ComponentEvent event) {}

    @Override
    public void componentHidden(ComponentEvent event) {}

    public enum OptionPaneButton {
        Yes,
        No,
        Cancel
    }
}
