package matej.mrozek.battleships;

public class Info {
    public static String version = "1.0.0";

    public static final String author = "Matej Mrozek";

    private static int attempts = 0;

    public static void reset() {
        //TODO: Set Version from metadata.json

        attempts = 0;
    }

    public static void addAttempt() {
        attempts++;
    }

    public static int getAttempts() {
        return attempts;
    }
}
