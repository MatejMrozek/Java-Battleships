package matej.mrozek.battleships.gui;

import javax.swing.*;

public class ScrollableText extends JScrollPane {
    public ScrollableText(String text, int x, int y, int width, int height) {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setBounds(x, y, width, height);

        updateText(text);
    }

    public void updateText(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        textArea.setEditable(false);
        setViewportView(textArea);
    }
}
