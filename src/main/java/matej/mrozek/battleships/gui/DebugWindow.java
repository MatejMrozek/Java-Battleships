package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DebugWindow extends Window {
    public DebugWindow(Debug debug, String title) {
        super(debug, title);
    }

    @Override
    public void init(Debug debug, String title) {
        super.init(debug, title, JFrame.HIDE_ON_CLOSE);

        pack();
        setSize(new Dimension(screen.width / 4, screen.height / 4));

        update();

        setVisible(true);
    }

    public void update() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(getWidth() - 17, getHeight() - 40));

        List<String> log = DEBUG.getLog();
        StringBuilder logBuilder = new StringBuilder();
        if (log != null) {
            for (String string : log) {
                logBuilder.append(string).append("\n");
            }
        }
        textArea.setText(logBuilder.toString());

        addComponent(scrollPane, true);
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent event) {
        update();
    }
}
