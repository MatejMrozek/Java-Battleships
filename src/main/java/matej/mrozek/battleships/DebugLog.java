package matej.mrozek.battleships;

public class DebugLog {
    private boolean enabled = false;

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void plain(String text) {
        print(text);
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

        System.out.println(text + "\n");
    }
}
