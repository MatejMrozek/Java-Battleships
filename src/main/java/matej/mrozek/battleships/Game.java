package matej.mrozek.battleships;

import matej.mrozek.battleships.gui.Button;
import matej.mrozek.battleships.gui.Window;

import javax.swing.*;
import java.util.*;

public class Game {
    private static Window WINDOW;

    private static final CoordinateMap COORDINATE_MAP = new CoordinateMap();

    public static final DebugLog DEBUG_LOG = new DebugLog();

    public static void main(String[] args) {
        boolean debug = args.length > 0 && (args[0].equals("debug=true") || args[0].equals("--debug"));
        DEBUG_LOG.setEnabled(debug);
        DEBUG_LOG.plain("DEBUG ENABLED");

        Info.reset();

        DEBUG_LOG.info("Creating Window...");
        WINDOW = new Window("Battleships v" + Info.version + (debug ? " [Debug Mode]" : ""));
        DEBUG_LOG.info("Window created!");

        start();
    }

    public static void start() {
        DEBUG_LOG.info("Starting a new game...");

        Info.reset();

        WINDOW.clear();

        int mapSize = askForMapSize();
        COORDINATE_MAP.setSize(mapSize);
        COORDINATE_MAP.generate();

        WINDOW.loadMap(COORDINATE_MAP);

        DEBUG_LOG.info("Game started!");
    }

    public static void update(Coordinate coordinate, Button button) {
        List<Object> list = COORDINATE_MAP.update(coordinate);
        CoordinateMap.CoordinateStatus coordinateStatus = (CoordinateMap.CoordinateStatus) list.get(0);
        button.setText(getCoordinateText(coordinate));
        switch (coordinateStatus) {
            case Water -> WINDOW.showMessage("Water Hit", "You have hit water!", JOptionPane.INFORMATION_MESSAGE);
            case Hit_Water -> WINDOW.showMessage("Water Already Hit", "You have already hit that water!", JOptionPane.ERROR_MESSAGE);
            case Battleship -> WINDOW.showMessage("Battleship Hit", "You have hit a battleship!", JOptionPane.INFORMATION_MESSAGE);
            case Hit_Battleship -> WINDOW.showMessage("Battleship Piece Already Hit", "You have already hit that battleship piece!", JOptionPane.ERROR_MESSAGE);
        }

        if ((Boolean) list.get(1)) {
            WINDOW.showMessage("Battleship Sunk", "You have sunk a battleship!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (COORDINATE_MAP.getBattleshipPieces() <= 0) {
            WINDOW.showMessage("Win", "You have sunk all the battleships!\nIt took you " + Info.getAttempts() + " attempts!", JOptionPane.INFORMATION_MESSAGE);

            Info.reset();

            if (askForRestart()) {
                start();
            } else {
                int code = 0;
                DEBUG_LOG.info("Exiting with exit code: " + code);
                System.exit(code);
            }
        }
    }

    public static String getCoordinateText(Coordinate coordinate) {
        return String.valueOf(COORDINATE_MAP.getCoordinateStatus(coordinate));
    }

    private static int askForMapSize() {
        int mapSize;
        while (true) {
            String mapSizeInput = WINDOW.showInput("Map Size Selection", "How many lines and columns should the map have?\n\nHigher and equal to 3 and lower or equal to 25.");
            if (mapSizeInput == null) {
                int code = 0;
                DEBUG_LOG.info("Exiting with exit code: " + code);
                System.exit(code);
            }

            try {
                mapSize = Integer.parseInt(mapSizeInput);
                if (mapSize >= 3 && mapSize <= 25) {
                    break;
                }
            } catch (Exception exception) {
                DEBUG_LOG.error(exception.getMessage());
            }

            WINDOW.showMessage("Invalid Input", "Invalid input value!\nTry again!", JOptionPane.ERROR_MESSAGE);
        }

        return mapSize;
    }

    private static boolean askForRestart() {
        String restartInput = WINDOW.showYesNoInput("Restart", "Do you want to play again?");
        if (restartInput == null) {
            int code = 0;
            DEBUG_LOG.info("Exiting with exit code: " + code);
            System.exit(code);
        }

        return restartInput.equals("yes");
    }
}
