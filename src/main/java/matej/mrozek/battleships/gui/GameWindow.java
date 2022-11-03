package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Coordinate;
import matej.mrozek.battleships.CoordinateMap;
import matej.mrozek.battleships.Debug;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends Window {
    public GameWindow(String title) {
        super(null, title);
    }

    @Override
    public void init(Debug debug, String title) {
        super.init(null, title);

        setResizable(false);
        clear();
    }

    @Override
    public void clear() {
        pack();
        setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);

        super.clear();
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
}
