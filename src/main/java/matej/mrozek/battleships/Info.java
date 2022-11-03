package matej.mrozek.battleships;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Info {
    public static String version = "Unknown";

    private static int attempts = 0;

    public static void reset(boolean resetAttempts) {
        if (version.equalsIgnoreCase("unknown")) {
            Game.DEBUG.info("Trying to load game version...");
            try (Scanner scanner = new Scanner(new File(Objects.requireNonNull(Info.class.getResource("/metadata.json")).getFile()), StandardCharsets.UTF_8.name())) {
                JSONObject jsonObject = new JSONObject(scanner.useDelimiter("\\A").next());
                version = jsonObject.getString("version");

                Game.DEBUG.info("Game version loaded! You are playing Battleships v" + version + ".");
            } catch (IOException exception) {
                Game.DEBUG.error("Could not load Battleships version: " + exception.getMessage());
            }
        }

        if (resetAttempts) {
            attempts = 0;
            Game.DEBUG.info("Set attempts to 0.");
        }
    }

    public static void addAttempt() {
        attempts++;
        Game.DEBUG.info("Added one attempt.");
    }

    public static int getAttempts() {
        return attempts;
    }
}
