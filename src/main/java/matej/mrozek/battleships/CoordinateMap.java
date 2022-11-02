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
        return this.size;
    }

    public int getBattleshipPieces() {
        return this.battleshipPieces;
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
            Battleship.Orientation randomOrientation;
            if (Utils.randomBoolean()) {
                randomOrientation = Battleship.Orientation.Horizontal;
                randomX = Utils.randomRange(0, size - randomSize);
            } else {
                randomOrientation = Battleship.Orientation.Vertical;
                randomY = Utils.randomRange(0, size - randomSize);
            }

            List<Coordinate> coordinates = new ArrayList<>();
            boolean horizontalOrientation = randomOrientation == Battleship.Orientation.Horizontal;
            for (int j = horizontalOrientation ? randomX : randomY; j < randomSize; j++) {
                coordinates.add(new Coordinate(horizontalOrientation ? j : randomX, horizontalOrientation ? randomY : j));
            }

            Battleship generatedBattleship = new Battleship(coordinates, randomSize, randomOrientation);
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
            for (Coordinate coordinate : battleship.coordinates) {
                setCoordinateStatus(coordinate, CoordinateStatus.Battleship);

                battleshipPieces++;
            }
        }
    }

    public List<Object> update(Coordinate coordinate) {
        List<Object> list = new ArrayList<>();
        CoordinateStatus coordinateStatus = getCoordinateStatus(coordinate);
        list.add(coordinateStatus);
        list.add(false);
        switch (coordinateStatus) {
            case Battleship -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Battleship);

                list.set(1, checkLastBattleshipPiece(coordinate));

                battleshipPieces--;

                Info.addAttempt();
            }
            case Water -> {
                setCoordinateStatus(coordinate, CoordinateStatus.Hit_Water);

                Info.addAttempt();
            }
        }



        return list;
    }

    public boolean checkLastBattleshipPiece(Coordinate coordinate) {
        for (Battleship battleship : battleships) {
            boolean[][] positions = new boolean[size][size];
            for (Coordinate battleshipCoordinate : battleship.coordinates) {
                positions[battleshipCoordinate.x][battleshipCoordinate.y] = true;
            }

            if (positions[coordinate.x][coordinate.y]) {
                battleship.piecesLeft--;
                if (battleship.piecesLeft == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setCoordinateStatus(Coordinate coordinate, CoordinateStatus coordinateStatus) {
        map[coordinate.x][coordinate.y] = coordinateStatus;
    }

    public CoordinateStatus getCoordinateStatus(Coordinate coordinate) {
        return map[coordinate.x][coordinate.y];
    }

    public enum CoordinateStatus {
        Water('0'),
        Battleship('0'),
        Hit_Water('W'),
        Hit_Battleship('X');

        private final char character;

        CoordinateStatus(char character) {
            this.character = character;
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }
}
