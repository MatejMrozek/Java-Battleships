package matej.mrozek.battleships;

import java.util.*;

public class Main {
    static final Map MAP = new Map();

    static boolean start = true;

    public static void main(String[] args) {
        while (start) {
            Info.reset();

            start = false;

            logTitle();

            new Log();

            logInfo();

            new Log();

            int mapSize = askForMapSize();

            MAP.setSize(mapSize);
            MAP.generate();

            while (MAP.getBattleshipPieces() > 0) {
                logDivider();

                logTitle();

                new Log();

                logInfo();

                new Log();

                MAP.log();

                new Log();

                int x = askForY();

                new Log();

                int y = askForX();

                new Log();

                MAP.update(x, y);

                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ignored) {}
            }

            logDivider();

            logTitle();

            new Log();

            MAP.log();

            new Log();

            logWin();

            new Log();

            start = askForRestart();

            new Log();
        }
    }

    static void logDivider() {
        new Log("\n*-------------------------*\n");
    }

    static void logTitle() {
        String titleBuilder = "*-------------------------*\n|       BATTLESHIPS       |\n*-------------------------*";
        new Log(titleBuilder);
    }

    private static void logInfo() {
        StringBuilder informationStringBuilder = new StringBuilder();

        informationStringBuilder.append("Game version: ").append(Info.version);

        informationStringBuilder.append("\n").append("Author: ").append(Info.author);

        int mapSize = MAP.getSize();
        if (mapSize > 0) {
            informationStringBuilder.append("\n").append("Map size: ").append(mapSize);
        }

        int attempts = Info.getAttempts();
        if (attempts > 0) {
            informationStringBuilder.append("\n").append("Attempts: ").append(attempts);
        }

        new Log(informationStringBuilder.toString());
    }

    private static int askForMapSize() {
        while (true) {
            new Log("Enter the map size: ", false);

            String inputMapSize = new Scanner(System.in).nextLine();
            try {
                int size = Integer.parseInt(inputMapSize);
                if (size > 3) {
                    return size;
                }
            } catch (Exception ignored) {}

            logInvalidValue();
        }
    }

    private static int askForX() {
        int x;
        while (true) {
            new Log("In which line do you want to shoot? ", false);

            String lineInput = new Scanner(System.in).nextLine();
            try {
                x = Integer.parseInt(lineInput);
                if (x < MAP.getSize() + 1 && x > 0) {
                    break;
                }
            } catch (Exception ignored) {}

            logInvalidValue();
        }

        return x - 1;
    }

    private static int askForY() {
        int y;
        while (true) {
            new Log("In which column do you want to shoot? ", false);

            String columnInput = new Scanner(System.in).nextLine();
            try {
                y = Integer.parseInt(columnInput);
                if (y < MAP.getSize() + 1 && y > 0) {
                    break;
                }
            } catch (Exception ignored) {}

            logInvalidValue();
        }

        return y - 1;
    }

    private static boolean askForRestart() {
        while (true) {
            new Log("Do you want to play again (\"Yes\" or \"No\")? ", false);

            String restartInput = new Scanner(System.in).nextLine();
            switch (restartInput.toLowerCase(Locale.ROOT)) {
                case "yes" -> {
                    return  true;
                }
                case "no" -> {
                    return false;
                }
                default -> logInvalidValue();
            }
        }
    }

    private static void logWin() {
        new Log("You have destroyed all the battleships!");
        new Log("It took you " + Info.getAttempts() + " attempts!");
    }

    private static void logInvalidValue() {
        new Log("Invalid value! Try again!");
    }
}
