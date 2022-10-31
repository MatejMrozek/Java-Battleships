package matej.mrozek.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private Position[][] map;
    private int size = 0;

    private final List<Battleship> battleships = new ArrayList<>();
    private int battleshipPieces = 0;

    public void setSize(int size) {
        this.size = size;
    }

    public void setBattleshipPieces(int battleshipPieces) {
        this.battleshipPieces = battleshipPieces;
    }

    public int getSize() {
        return this.size;
    }

    public int getBattleshipPieces() {
        return this.battleshipPieces;
    }

    public void generate() {
        map = new Position[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map[x][y] = Position.Water;
            }
        }

        generateShips();
    }

    public void generateShips() {
        Random random = new Random();
        for (int i = 0; i < (size * size) / (size * 2); i++) {
            int randomSize;
            if (size > 6) {
                randomSize = random.nextInt(2, 5);
            } else if (size > 4) {
                randomSize = random.nextInt(2, 4);
            } else {
                randomSize = 2;
            }

            int randomX = random.nextInt(0, size);
            int randomY = random.nextInt(0, size);
            Battleship.Orientation orientation;
            if (random.nextInt(0, 2) == 0) {
                orientation = Battleship.Orientation.Horizontal;
                randomX = random.nextInt(0, size - randomSize);
            } else {
                orientation = Battleship.Orientation.Vertical;
                randomY = random.nextInt(0, size - randomSize);
            }

            boolean overlap = false;
            for (Battleship battleship : battleships) {
                boolean[][] positions = new boolean[size][size];
                if (battleship.orientation == Battleship.Orientation.Horizontal) {
                    for (int x = 0; x < battleship.size; x++) {
                        positions[battleship.x + x][battleship.y] = true;
                    }
                } else {
                    for (int y = 0; y < battleship.size; y++) {
                        positions[battleship.x][battleship.y + y] = true;
                    }
                }

                if (positions[randomX][randomY]) {
                    overlap = true;
                }
            }

            if (overlap) {
                i--;
                continue;
            }

            battleships.add(new Battleship(randomX, randomY, randomSize, orientation));
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map[x][y] = Position.Water;
            }
        }

        for (Battleship battleship : battleships) {
            for (int i = 0; i < battleship.size; i++) {
                int x = 0;
                int y = 0;
                if (battleship.orientation == Battleship.Orientation.Horizontal) {
                    x = i;
                } else {
                    y = i;
                }

                map[battleship.x + x][battleship.y + y] = Map.Position.Battleship;

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

        StringBuilder spaceStringBuilder = new StringBuilder();
        int previousLength = 1;
        for (int x = 0; x < size; x++) {
            StringBuilder lineStringBuilder = new StringBuilder(" " + (x + 1) + "    ");
            lineStringBuilder.append(" ".repeat(String.valueOf(size).length() - String.valueOf(x + 1).length()));
            for (int y = 0; y < size; y++) {
                if (y > 0) {
                    lineStringBuilder.append(" - ");
                }

                lineStringBuilder.append(map[x][y]);

                if (String.valueOf(y + 1).length() > previousLength) {
                    spaceStringBuilder.append(" ");
                    previousLength++;
                }

                lineStringBuilder.append(spaceStringBuilder);
            }

            new Log(lineStringBuilder.toString());
            spaceStringBuilder.delete(0, spaceStringBuilder.length());
        }
    }

    public void update(int x, int y) {
        Position position = getPosition(x, y);
        switch (position) {
            case Battleship -> {
                map[x][y] = Position.Hit_Battleship;

                new Log("You hit a battleship!");

                if (checkLastPiece(x, y)) {
                    new Log();

                    new Log("You sunk a battleship!");
                }

                battleshipPieces--;

                Info.addAttempt();
            }
            case Hit_Battleship -> new Log("You have already hit that battleship!");
            case Water -> {
                new Log("You hit water!");

                Info.addAttempt();
            }
            case Hit_Water -> new Log("You have already hit that water!");
        }
    }

    public boolean checkLastPiece(int x, int y) {
        for (Battleship battleship : battleships) {
            boolean[][] positions = new boolean[size][size];
            if (battleship.orientation == Battleship.Orientation.Horizontal) {
                for (int mapX = 0; mapX < battleship.size; mapX++) {
                    positions[battleship.x + mapX][battleship.y] = true;
                }
            } else {
                for (int mapY = 0; mapY < battleship.size; mapY++) {
                    positions[battleship.x][battleship.y + mapY] = true;
                }
            }

            if (positions[x][y]) {
                battleship.piecesLeft--;
                if (battleship.piecesLeft == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public Position getPosition(int x, int y) {
        return map[x][y];
    }

    enum Position {
        Water('0'),
        Battleship('0'),
        Hit_Water('W'),
        Hit_Battleship('X');

        final char character;

        Position(char character) {
            this.character = character;
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }
}
