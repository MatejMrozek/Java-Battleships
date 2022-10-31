package matej.mrozek.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private PositionStatus[][] map;
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
        map = new PositionStatus[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                setPosition(x, y, PositionStatus.Water);
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
            if (random.nextBoolean()) {
                orientation = Battleship.Orientation.Horizontal;
                randomX = random.nextInt(0, size - randomSize);
            } else {
                orientation = Battleship.Orientation.Vertical;
                randomY = random.nextInt(0, size - randomSize);
            }

            boolean overlap = false;
            for (Battleship battleship : battleships) {
                boolean[][] positions = new boolean[size][size];
                for (int j = 0; j < battleship.size; j++) {
                    if (battleship.orientation == Battleship.Orientation.Horizontal) {
                        positions[battleship.x + j][battleship.y] = true;
                    } else {
                        positions[battleship.x][battleship.y + j] = true;
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

        for (Battleship battleship : battleships) {
            for (int i = 0; i < battleship.size; i++) {
                boolean horizontal = battleship.orientation == Battleship.Orientation.Horizontal;
                setPosition(battleship.x + (horizontal ? i : 0), battleship.y + (!horizontal ? i : 0), PositionStatus.Battleship);

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

                lineStringBuilder.append(getPosition(x, y));

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
        switch (getPosition(x, y)) {
            case Battleship -> {
                setPosition(x, y, PositionStatus.Hit_Battleship);

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
                setPosition(x, y, PositionStatus.Hit_Water);

                new Log("You hit water!");

                Info.addAttempt();
            }
            case Hit_Water -> new Log("You have already hit that water!");
        }
    }

    public boolean checkLastPiece(int x, int y) {
        for (Battleship battleship : battleships) {
            boolean[][] positions = new boolean[size][size];
            for (int i = 0; i < battleship.size; i++) {
                if (battleship.orientation == Battleship.Orientation.Horizontal) {
                    positions[battleship.x + i][battleship.y] = true;
                } else{
                    positions[battleship.x][battleship.y + i] = true;
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

    public void setPosition(int x, int y, PositionStatus positionStatus) {
        map[x][y] = positionStatus;
    }

    public PositionStatus getPosition(int x, int y) {
        return map[x][y];
    }

    enum PositionStatus {
        Water('0'),
        Battleship('0'),
        Hit_Water('W'),
        Hit_Battleship('X');

        private final char character;

        PositionStatus(char character) {
            this.character = character;
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }
}
