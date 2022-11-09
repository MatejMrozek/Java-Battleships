package matej.mrozek.battleships;

import org.json.JSONObject;

import java.io.*;

public class Info {
    public static String version = "Unknown";

    private static int attempts = 0;

    public static void reset(boolean resetAttempts) {
        if (version.equalsIgnoreCase("unknown")) {
            boolean failed = false;
            String bonusInfo = "Unknown";
            try {
                Game.DEBUG.info("Trying to load game version...");
                InputStream inputStream = Info.class.getResourceAsStream("/metadata.json");
                if (inputStream == null) {
                    failed = true;
                    bonusInfo = "InputStream from /metadata.json is null.";
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder rawJson = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    rawJson.append(line).append("\n");
                }

                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                JSONObject jsonObject = new JSONObject(rawJson.toString());
                version = jsonObject.getString("version");

                Game.DEBUG.info("Game version loaded! You are playing Battleships v" + version + ".");
            } catch (IOException exception) {
                failed = true;
                bonusInfo = exception.getMessage();
            }

            if (failed) {
                Game.DEBUG.error("Could not load Battleships version: " + bonusInfo);
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
