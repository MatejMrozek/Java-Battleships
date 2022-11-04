package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.List;

public class DebugWindow extends Window {
    private ScrollableText scrollableText;

    public DebugWindow(Debug debug, String title) {
        super(debug, title);
    }

    @Override
    public void init(Debug debug, String title) {
        super.init(debug, title, JFrame.DISPOSE_ON_CLOSE);

        pack();
        setSize(new Dimension(screen.width / 3, screen.height / 3));
        setLocation(0, 0);
        scrollableText = new ScrollableText(null, 1, 1, getWidth() - 17, getHeight() - 40);

        update();
    }

    public void update() {
        List<String> log = DEBUG.getLog();
        StringBuilder logBuilder = new StringBuilder();
        if (log != null) {
            for (String string : log) {
                logBuilder.append(string).append("\n");
            }
        }

        scrollableText.updateText(logBuilder.toString());
        scrollableText.setBounds(1, 1, getWidth() - 17, getHeight() - 40);

        clear();
        addComponent(scrollableText);
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent event) {
        update();
    }

    @Override
    public void windowClosed(WindowEvent event) {
        setVisible(false);
        dispose();
        DEBUG.destroyWindow();
    }
}
