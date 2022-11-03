package matej.mrozek.battleships;

import matej.mrozek.battleships.gui.DebugWindow;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private DebugWindow debugWindow;

    private final List<String> log = new ArrayList<>();

    private final boolean enabled;

    public Debug(boolean enabled) {
        this.enabled = enabled;
    }

    public void createDebugWindow(String title) {
        if (enabled) {
            debugWindow = new DebugWindow(this, title);
        }
    }

    public void destroyWindow() {
        debugWindow = null;
    }

    public List<String> getLog() {
        return log;
    }

    public void info(String text) {
        print("[INFO] " + text);
    }

    public void error(String text) {
        print("[ERROR] " + text);
    }

    private void print(String text) {
        if (!enabled) {
            return;
        }

        log.add(text);
        System.out.println(text);

        if (debugWindow != null) {
            debugWindow.update();
        }
    }
}
