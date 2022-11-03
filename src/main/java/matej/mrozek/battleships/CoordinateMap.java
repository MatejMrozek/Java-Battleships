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
        map = new CoordinateStatus[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                setCoordinateStatus(new Coordinate(x, y), CoordinateStatus.Water);
            }
        }

        generateShips();
    }

    public void generateShips() {
        for (int i = 0; i < (size * size) / (size * 2); i++) {
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

            int randomX = Utils.randomRange(0, size);
            int randomY = Utils.randomRange(0, size);
            boolean horizontal = Utils.randomBoolean();
            if (horizontal) {
                randomX = Utils.randomRange(0, size - randomSize);
            } else {
                randomY = Utils.randomRange(0, size - randomSize);
            }

            List<Coordinate> coordinates = new ArrayList<>();
            for (int j = horizontal ? randomX : randomY; j - (horizontal ? randomX : randomY) < randomSize; j++) {
                coordinates.add(new Coordinate(horizontal ? j : randomX, horizontal ? randomY : j));
            }

            Battleship generatedBattleship = new Battleship(coordinates, randomSize);
            boolean discard = coordinates.size() < 2;
            if (!discard) {
                for (Battleship battleship : battleships) {
                    if (generatedBattleship.isOverlapping(battleship)) {
                        discard = true;
                        break;
                    }
                }
            }

            if (discard) {
                i--;
            } else {
                battleships.add(generatedBattleship);
            }
        }

        for (Battleship battleship : battleships) {
            for (Coordinate coordinate : battleship.getCoordinates()) {
                setCoordinateStatus(coordinate, CoordinateStatus.Battleship);

                battleshipPieces++;
            }
        }
    }

    public CoordinateStatus update(Coordinate coordinate) {
        CoordinateStatus coordinateStatus = getCoordinateStatus(coordinate);
        switch (coordinateStatus) {
            case Battleship -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Battleship);

                for (Battleship battleship : battleships) {
                    for (Coordinate battleshipCoordinate: battleship.getCoordinates()) {
                        if (coordinate.isEqualTo(battleshipCoordinate)) {
                            battleship.removePiece();
                        }
                    }
                }

                coordinateStatus.setLastBattleshipPiece(checkLastBattleshipPiece(coordinate));

                battleshipPieces--;

                Info.addAttempt();
            }
            case Water -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Water);

                Info.addAttempt();
            }
        }

        return coordinateStatus;
    }

    public boolean checkLastBattleshipPiece(Coordinate coordinate) {
        for (Battleship battleship : battleships) {
            for (Coordinate battleshipCoordinate : battleship.getCoordinates()) {
                if (coordinate.isEqualTo(battleshipCoordinate) && battleship.isSunk()) {
                    return true;
                }
            }
        }

        return false;
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
