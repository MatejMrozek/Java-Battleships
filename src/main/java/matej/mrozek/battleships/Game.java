package matej.mrozek.battleships;

import matej.mrozek.battleships.gui.Button;
import matej.mrozek.battleships.gui.DebugWindow;
import matej.mrozek.battleships.gui.GameWindow;

import javax.swing.*;

public class Game {
    private static GameWindow gameWindow;

    private static final CoordinateMap COORDINATE_MAP = new CoordinateMap();

    public static Debug DEBUG;

    public static void main(String[] args) {
        Info.reset();

        DEBUG = new Debug(args.length > 0 && (args[0].equals("debug=true") || args[0].equals("--debug")));
        DEBUG.info("DEBUG ENABLED!");

        DEBUG.info("Creating window...");
        gameWindow = new GameWindow("Battleships v" + Info.version);
        DEBUG.info("Window created!");

        DEBUG.info("Creating DEBUG window...");
        DEBUG.createDebugWindow("[DEBUG] Battleships v" + Info.version);
        DEBUG.info("DEBUG window created!");

        start();
    }

    public static void start() {
        DEBUG.info("Starting a new game...");

        Info.reset();

        gameWindow.clear();

        int mapSize = askForMapSize();
        COORDINATE_MAP.setSize(mapSize);
        COORDINATE_MAP.generate();

        gameWindow.loadMap(COORDINATE_MAP);

        DEBUG.info("Game started!");
    }

    public static void update(Coordinate coordinate, Button button) {
        CoordinateMap.CoordinateStatus coordinateStatus = COORDINATE_MAP.update(coordinate);
        button.setText(getCoordinateText(coordinate));
        switch (coordinateStatus) {
            case Water -> gameWindow.showMessage("Water Hit", "You have hit water!", JOptionPane.INFORMATION_MESSAGE);
            case Hit_Water -> gameWindow.showMessage("Water Already Hit", "You have already hit that water!", JOptionPane.ERROR_MESSAGE);
            case Battleship -> gameWindow.showMessage("Battleship Hit", "You have hit a battleship!", JOptionPane.INFORMATION_MESSAGE);
            case Hit_Battleship -> gameWindow.showMessage("Battleship Piece Already Hit", "You have already hit that battleship piece!", JOptionPane.ERROR_MESSAGE);
        }

        if (coordinateStatus.isLastBattleshipPiece()) {
            gameWindow.showMessage("Battleship Sunk", "You have sunk a battleship!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (COORDINATE_MAP.getBattleshipPieces() <= 0) {
            gameWindow.showMessage("Win", "You have sunk all the battleships!\nIt took you " + Info.getAttempts() + " attempts!", JOptionPane.INFORMATION_MESSAGE);

            Info.reset();

            if (askForRestart()) {
                start();
            } else {
                int code = 0;
                DEBUG.info("Exiting with exit code: " + code);
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
            String mapSizeInput = gameWindow.showInput("Map Size Selection", "How many lines and columns should the map have?\n\nHigher and equal to 3 and lower or equal to 25.");
            if (mapSizeInput == null) {
                int code = 0;
                DEBUG.info("Exiting with exit code: " + code);
                System.exit(code);
            }

            try {
                mapSize = Integer.parseInt(mapSizeInput);
                if (mapSize >= 3 && mapSize <= 25) {
                    break;
                }
            } catch (Exception exception) {
                DEBUG.error(exception.getMessage());
            }

            gameWindow.showMessage("Invalid Input", "Invalid input value!\nTry again!", JOptionPane.ERROR_MESSAGE);
        }

        return mapSize;
    }

    private static boolean askForRestart() {
        GameWindow.OptionPaneButton restartInput = gameWindow.showYesNoInput("Restart", "Do you want to play again?");
        if (restartInput == GameWindow.OptionPaneButton.Cancel) {
            int code = 0;
            DEBUG.info("Exiting with exit code: " + code);
            System.exit(code);
        }

        return restartInput == GameWindow.OptionPaneButton.Yes;
    }
}
