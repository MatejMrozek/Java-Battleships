package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Log;
import matej.mrozek.battleships.Map;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Window extends JFrame {
    private final JPanel panel = new JPanel(null);

    public Window(String title) {
        init(title);
    }

    private void init(String title) {
        this.setTitle(title);
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/icon.png"))).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screen.width / 2, screen.height / 2);
        this.setVisible(true);
    }

    public void loadMap(Map map) {
        int mapSize = map.getSize();
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y< mapSize; y++) {
                Button button = new Button(map.getPositionString(x, y), x * 25 + x * 5, y * 25 + y * 5, 25, 25);
                addComponent(button);
            }
        }
    }

    private void addComponent(Component component) {
        panel.add(component);
        this.setContentPane(panel);
        this.setVisible(true);
    }
}