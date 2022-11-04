package matej.mrozek.battleships;

import java.util.*;

public class CoordinateMap {
    private CoordinateStatus[][] map;
    private int size = 0;

    private final List<Battleship> battleships = new ArrayList<>();
    private int battleshipPieces = 0;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getBattleshipPieces() {
        return battleshipPieces;
    }

    public void generate() {
        Game.DEBUG.info("Generating map...");

        map = new CoordinateStatus[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                setCoordinateStatus(new Coordinate(x, y), CoordinateStatus.Water);
            }
        }

        Game.DEBUG.info("Set all coordinates to water.");

        generateShips();

        Game.DEBUG.info("Map generated!");
    }

    public void generateShips() {
        Game.DEBUG.info("Generating battleships...");
        for (int i = 0; i < (size * size) / (size * 2); i++) {
            Game.DEBUG.info("Generating a new battleship...");

            Game.DEBUG.info("Generating random size...");
            int randomSize;
            if (size >= 10) {
                randomSize = Utils.randomRange(2, 6);
            } else if (size >= 7) {
                randomSize = Utils.randomRange(2, 5);
            } else if (size >= 5) {
                randomSize = Utils.randomRange(2, 4);
            } else {
                randomSize = 2;
            }

            Game.DEBUG.info("Generated random size: " + randomSize);

            int randomX = Utils.randomRange(0, size);
            int randomY = Utils.randomRange(0, size);
            boolean horizontal = Utils.randomBoolean();
            if (horizontal) {
                randomX = Utils.randomRange(0, size - randomSize);
            } else {
                randomY = Utils.randomRange(0, size - randomSize);
            }

            Game.DEBUG.info("The battleship is " + (horizontal ? "horizontal" : "vertical") + "!");

            List<Coordinate> coordinates = new ArrayList<>();
            for (int j = horizontal ? randomX : randomY; j - (horizontal ? randomX : randomY) < randomSize; j++) {
                Coordinate coordinate = new Coordinate(horizontal ? j : randomX, horizontal ? randomY : j);
                coordinates.add(coordinate);
                Game.DEBUG.info("Added coordinate " + coordinate + " to the battleship.");
            }

            Game.DEBUG.info("Battleship generated!");

            Battleship generatedBattleship = new Battleship(coordinates, randomSize);
            boolean discard = coordinates.size() < 2;
            if (!discard) {
                Game.DEBUG.info("Checking for overlaps...");
                for (Battleship battleship : battleships) {
                    if (generatedBattleship.isOverlapping(battleship)) {
                        Game.DEBUG.error("Overlap found!");
                        discard = true;
                        break;
                    }
                }

                if (!discard) {
                    Game.DEBUG.info("No overlap found.");
                }
            } else {
                Game.DEBUG.error("The battleship is too small!");
            }

            if (discard) {
                i--;
                Game.DEBUG.info("Discarding the generated battleship.");
            } else {
                battleships.add(generatedBattleship);
                Game.DEBUG.info("Added the battleship!");
            }
        }

        for (Battleship battleship : battleships) {
            for (Coordinate coordinate : battleship.getCoordinates()) {
                setCoordinateStatus(coordinate, CoordinateStatus.Battleship);
                Game.DEBUG.info("Setting coordinate " + coordinate + " to a battleship.");

                battleshipPieces++;
                Game.DEBUG.info("Added a battleship piece to the battleship pieces variable.");
            }
        }

        Game.DEBUG.info("Battleships generated!");
    }

    public CoordinateStatus update(Coordinate coordinate) {
        Game.DEBUG.info("Updating coordinate " + coordinate + "...");
        CoordinateStatus coordinateStatus = getCoordinateStatus(coordinate);
        switch (coordinateStatus) {
            case Battleship -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Battleship);
                Game.DEBUG.info("Setting coordinate " + coordinate + " to hit battleship.");

                for (Battleship battleship : battleships) {
                    for (Coordinate battleshipCoordinate: battleship.getCoordinates()) {
                        if (coordinate.isEqualTo(battleshipCoordinate)) {
                            battleship.removePiece();
                            Game.DEBUG.info("Removed a piece from the battleship.");
                        }
                    }
                }

                boolean lastBattleshipPiece = checkLastBattleshipPiece(coordinate);
                coordinateStatus.setLastBattleshipPiece(lastBattleshipPiece);
                if (lastBattleshipPiece) {
                    Game.DEBUG.info("Setting the result as last battleship piece.");
                }

                battleshipPieces--;
                Game.DEBUG.info("Removed a battleship piece from the battleship pieces variable.");

                Info.addAttempt();
            }
            case Water -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Water);
                Game.DEBUG.info("Setting coordinate " + coordinate + " to hit water.");

                Info.addAttempt();
            }
        }

        Game.DEBUG.info("Coordinate " + coordinate + " updated!");

        return coordinateStatus;
    }

    public boolean checkLastBattleshipPiece(Coordinate coordinate) {
        Game.DEBUG.info("Checking if the coordinate is last piece of a battleship.");
        boolean lastPiece = false;
        for (Battleship battleship : battleships) {
            for (Coordinate battleshipCoordinate : battleship.getCoordinates()) {
                if (coordinate.isEqualTo(battleshipCoordinate) && battleship.isSunk()) {
                    lastPiece = true;
                    break;
                }
            }
        }
        Game.DEBUG.info("The coordinate is" + (lastPiece ? " " : " not ") + "a last piece of a battleship.");

        return lastPiece;
    }

    public void setCoordinateStatus(Coordinate coordinate, CoordinateStatus coordinateStatus) {
        map[coordinate.x()][coordinate.y()] = coordinateStatus;
    }

    public CoordinateStatus getCoordinateStatus(Coordinate coordinate) {
        return map[coordinate.x()][coordinate.y()];
    }

    public enum CoordinateStatus {
        Water('0'),
        Battleship('0'),
        Hit_Water('W'),
        Hit_Battleship('X');

        private final char character;

        private boolean lastBattleshipPiece = false;

        CoordinateStatus(char character) {
            this.character = character;
        }

        public boolean isLastBattleshipPiece() {
            return lastBattleshipPiece;
        }

        public void setLastBattleshipPiece(boolean lastBattleshipPiece) {
            this.lastBattleshipPiece = lastBattleshipPiece;
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }
}
