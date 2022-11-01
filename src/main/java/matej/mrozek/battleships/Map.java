package matej.mrozek.battleships;

import java.util.ArrayList;
import java.util.List;

public class Map {
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
                setPosition(new Coordinate(x, y), CoordinateStatus.Water);
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

            boolean discard = coordinates.size() < 2;
            if (!discard) {
                for (Battleship battleship : battleships) {
                    for (Coordinate battleshipCoordinate : battleship.coordinates) {
                        for (Coordinate coordinate : coordinates) {
                            if (coordinate.isEqualTo(battleshipCoordinate)) {
                                discard = true;
                            }
                        }
                    }
                }
            }

            if (discard) {
                i--;
            } else {
                battleships.add(new Battleship(coordinates, randomSize, randomOrientation));
            }
        }

        for (Battleship battleship : battleships) {
            for (Coordinate coordinate : battleship.coordinates) {
                setPosition(coordinate, CoordinateStatus.Battleship);

                battleshipPieces++;
            }
        }
    }

    public void log() {
        StringBuilder columnStringBuilder = new StringBuilder("     ");
        columnStringBuilder.append(" ".repeat(String.valueOf(size).length()));
        for (int x = 0; x < size; x++) {
            columnStringBuilder.append(x + 1);
            if (x < size - 1) {
                columnStringBuilder.append(" - ");
            }
        }

        new Log(columnStringBuilder.toString());

        new Log();

        for (int x = 0; x < size; x++) {
            String readableX = String.valueOf(x + 1);
            StringBuilder lineStringBuilder = new StringBuilder(" " + readableX + "    ");
            lineStringBuilder.append(" ".repeat(String.valueOf(size).length() - readableX.length()));
            for (int y = 0; y < size; y++) {
                if (y > 0) {
                    lineStringBuilder.append(" - ");
                }

                int yLength = String.valueOf(y + 1).length();
                lineStringBuilder.append(map[x][y]).append(" ".repeat(yLength > 1 ? yLength - 1 : 0));
            }

            new Log(lineStringBuilder.toString());
        }
    }

    public void update(Coordinate coordinate) {
        switch (getCoordinate(coordinate)) {
            case Battleship -> {
                setPosition(coordinate, CoordinateStatus.Hit_Battleship);

                new Log("You hit a battleship!");

                if (checkLastPiece(coordinate)) {
                    new Log();

                    new Log("You sunk a battleship!");
                }

                battleshipPieces--;

                Info.addAttempt();
            }
            case Hit_Battleship -> new Log("You have already hit that battleship!");
            case Water -> {
                setPosition(coordinate, CoordinateStatus.Hit_Water);

                new Log("You hit water!");

                Info.addAttempt();
            }
            case Hit_Water -> new Log("You have already hit that water!");
        }
    }

    public boolean checkLastPiece(Coordinate coordinate) {
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

    public void setPosition(Coordinate coordinate, CoordinateStatus coordinateStatus) {
        map[coordinate.x][coordinate.y] = coordinateStatus;
    }

    public CoordinateStatus getCoordinate(Coordinate coordinate) {
        return map[coordinate.x][coordinate.y];
    }

    enum CoordinateStatus {
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
