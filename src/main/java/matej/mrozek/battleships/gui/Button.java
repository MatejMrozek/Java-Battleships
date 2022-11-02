package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Coordinate;
import matej.mrozek.battleships.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    private final Coordinate coordinate;

    public Button(Coordinate coordinate, int x, int y, int width, int height) {
        this.coordinate = coordinate;
        this.setText(Game.getCoordinateText(coordinate));
        this.setBounds(x, y, width, height);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.addActionListener(this);
    }

    public int getX() {
        return getLocation().x;
    }

    public void setX(int x) {
        setLocation(new Point(x, getY()));
    }

    public int getY() {
        return getLocation().y;
    }

    public void setY(int y) {
        setLocation(new Point(getX(), y));
    }

    public int getWidth() {
        return getSize().width;
    }

    public void setWidth(int width) {
        setSize(new Dimension(width, getHeight()));
    }

    public int getHeight() {
        return getSize().height;
    }

    public void setHeight(int height) {
        setSize(new Dimension(getWidth(), height));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Game.update(coordinate, this);
    }
}