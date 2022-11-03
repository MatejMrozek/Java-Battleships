package matej.mrozek.battleships.gui;

import matej.mrozek.battleships.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Window extends JFrame implements ComponentListener, WindowListener {
    public Debug DEBUG;

    public JPanel panel;

    public final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public Window(Debug debug, String title) {
        init(debug, title);

        addComponentListener(this);
        addWindowListener(this);
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

    public void addComponent(Component component) {
        panel.add(component);
        setContentPane(panel);
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent event) {}

    @Override
    public void componentMoved(ComponentEvent event) {}

    @Override
    public void componentShown(ComponentEvent event) {}

    @Override
    public void componentHidden(ComponentEvent event) {}

    @Override
    public void windowOpened(WindowEvent event) {}

    @Override
    public void windowClosing(WindowEvent event) {}

    @Override
    public void windowClosed(WindowEvent event) {}

    @Override
    public void windowIconified(WindowEvent event) {}

    @Override
    public void windowDeiconified(WindowEvent event) {}

    @Override
    public void windowActivated(WindowEvent event) {}

    @Override
    public void windowDeactivated(WindowEvent event) {}

    public enum OptionPaneButton {
        Yes,
        No,
        Cancel
    }
}
