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
        setText(Game.getCoordinateText(coordinate));
        setHorizontalAlignment(JTextField.CENTER);
        setBounds(x, y, width, height);
        setMargin(new Insets(0, 0, 0, 0));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Game.update(coordinate, this);
        Game.DEBUG.info("Updated coordinate " + coordinate + " because button on " + new Coordinate(getX(), getY()) + " got clicked.");
    }
}