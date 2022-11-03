package matej.mrozek.battleships.gui;

import javax.swing.*;
import java.awt.*;

public class Text extends JTextField {
    public Text(String text, int x, int y, int width, int height) {
        setText(text);
        setEditable(false);
        setHorizontalAlignment(JTextField.CENTER);
        setBounds(x, y, width, height);
        setMargin(new Insets(0, 0, 0, 0));
    }
}
