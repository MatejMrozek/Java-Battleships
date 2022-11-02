package matej.mrozek.battleships.gui;

import javax.swing.*;
import java.awt.*;

public class Text extends JTextField {
    public Text(String text, int x, int y, int width, int height) {
        this.setText(text);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setBounds(x, y, width, height);
        this.setMargin(new Insets(0, 0, 0, 0));
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
}
