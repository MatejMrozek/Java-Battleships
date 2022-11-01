package matej.mrozek.battleships;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Info {
    public static String version = "Unknown";

    public static final String author = "Matej Mrozek";

    private static int attempts = 0;

    public static void reset() {
        try (Scanner scanner = new Scanner(new File(Objects.requireNonNull(Info.class.getResource("/metadata.json")).getFile()), StandardCharsets.UTF_8.name())) {
            JSONObject jsonObject = new JSONObject(scanner.useDelimiter("\\A").next());
            version = jsonObject.getString("version");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        attempts = 0;
    }

    public static void addAttempt() {
        attempts++;
    }

    public static int getAttempts() {
        return attempts;
    }
}
