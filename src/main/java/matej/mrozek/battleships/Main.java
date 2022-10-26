package matej.mrozek.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Main {
    static final String gameVersion = "1.0.0";
    static final String author = "Matej Mrozek";

    static int mapSize = 0;
    static int battleshipPiecesLeft = 0;
    static int attempts = 0;

    static List<Battleship> battleships = new ArrayList<>();

    static PositionStatus[][] map;

    public static void main(String[] args) {
        printTitle();
        print();

        askForMapSize();

        map = new PositionStatus[mapSize][mapSize];

        generateAndSaveBattleshipsToTheMap();

        while (battleshipPiecesLeft > 0) {
            printConsoleDivider();

            printTitle();

            print();

            printInformation();

            print();

            printMap();

            print();

            int line = askForLine();

            print();

            int column = askForColumn();

            print();

            updateLineAndColumnPositionStatus(line, column);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException ignored) {}
        }

        printConsoleDivider();

        printTitle();

        print();

        printMap();

        print();

        print("You have destroyed all the battleships!\nIt took you " + attempts + " attempts!");
    }

    static void printConsoleDivider() {
        print();
        print("*-------------------------*");
        print();
    }

    static void printTitle() {
        print("*-------------------------*");
        print("|       BATTLESHIPS       |");
        print("*-------------------------*");
    }

    static void printInformation() {
        StringBuilder informationStringBuilder = new StringBuilder();

        informationStringBuilder.append("Game version: ").append(gameVersion).append("\n");

        informationStringBuilder.append("Author: ").append(author).append("\n");

        informationStringBuilder.append("Map size: ").append(mapSize).append("\n");

        if (attempts > 0) {
            informationStringBuilder.append("Attempts: ").append(attempts);
        }

        print(informationStringBuilder.toString());
    }

    static void printMap() {
        StringBuilder columnStringBuilder = new StringBuilder("     ");
        columnStringBuilder.append(" ".repeat(String.valueOf(mapSize).length()));
        for (int x = 0; x < mapSize; x++) {
            columnStringBuilder.append(x + 1);
            if (x < mapSize - 1) {
                columnStringBuilder.append(" - ");
            }
        }

        print(columnStringBuilder.toString());

        print();

        StringBuilder spaceStringBuilder = new StringBuilder();
        int previousLength = 1;
        for (int x = 0; x < mapSize; x++) {
            StringBuilder lineStringBuilder = new StringBuilder(" " + (x + 1) + "    ");
            lineStringBuilder.append(" ".repeat(String.valueOf(mapSize).length() - String.valueOf(x + 1).length()));
            for (int y = 0; y < mapSize; y++) {
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

            print(lineStringBuilder.toString());
            spaceStringBuilder.delete(0, spaceStringBuilder.length());
        }
    }

    static void updateLineAndColumnPositionStatus(int line, int column) {
        PositionStatus positionStatus = PositionStatus.Hit_Water;
        switch (map[line][column]) {
            case Battleship -> {
                positionStatus = PositionStatus.Hit_Battleship;

                print("You hit a battleship!");

                if (checkLastBattleShipPiece(line, column)) {
                    print("You sunk a battleship!");
                }

                battleshipPiecesLeft--;

                attempts++;
            }
            case Hit_Battleship -> {
                print("You have already hit that battleship!");
            }
            case Water -> {
                print("You hit water!");

                attempts++;
            }
            case Hit_Water -> {
                print("You have already hit that water!");
            }
        }

        map[line][column] = positionStatus;
    }

    static void generateAndSaveBattleshipsToTheMap() {
        Random random = new Random();
        for (int i = 0; i < (mapSize * mapSize) / (mapSize * 2); i++) {
            int randomSize;
            if (mapSize > 6) {
                randomSize = random.nextInt(2, 5);
            } else if (mapSize > 4) {
                randomSize = random.nextInt(2, 4);
            } else {
                randomSize = 2;
            }

            int randomX = random.nextInt(0, mapSize);
            int randomY = random.nextInt(0, mapSize);
            Battleship.Orientation orientation;
            if (random.nextInt(0, 2) == 0) {
                orientation = Battleship.Orientation.Horizontal;
                randomX = random.nextInt(0, mapSize - randomSize);
            } else {
                orientation = Battleship.Orientation.Vertical;
                randomY = random.nextInt(0, mapSize - randomSize);
            }

            battleships.add(new Battleship(randomX, randomY, randomSize, orientation));
        }

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                map[x][y] = PositionStatus.Water;
            }
        }

        for (Battleship battleship : battleships) {
            battleshipPiecesLeft += battleship.size;

            if (battleship.orientation == Battleship.Orientation.Horizontal) {
                for (int x = 0; x < battleship.size; x++) {
                    map[battleship.x + x][battleship.y] = PositionStatus.Battleship;
                }
            } else {
                for (int y = 0; y < battleship.size; y++) {
                    map[battleship.x][battleship.y + y] = PositionStatus.Battleship;
                }
            }
        }
    }

    static boolean checkLastBattleShipPiece(int line, int column) {
        for (Battleship battleship : battleships) {
            int[][] positions = new int[mapSize][mapSize];
            if (battleship.orientation == Battleship.Orientation.Horizontal) {
                for (int x = 0; x < battleship.size; x++) {
                    positions[battleship.x + x][battleship.y] = 1;
                }
            } else {
                for (int y = 0; y < battleship.size; y++) {
                    positions[battleship.x][battleship.y + y] = 1;
                }
            }

            if (positions[line][column] == 1) {
                battleship.piecesLeft--;

                if (battleship.piecesLeft == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    static void askForMapSize() {
        while (true) {
            print("Enter the map size:");

            String inputMapSize = new Scanner(System.in).nextLine();
            try {
                mapSize = Integer.parseInt(inputMapSize);
                if (mapSize > 3) {
                    break;
                }
            } catch (Exception ignored) {}

            printInvalidValue();
        }
    }

    static int askForLine() {
        int line;
        while (true) {
            print("In which line do you want to shoot next?");

            String shootingLineInput = new Scanner(System.in).nextLine();
            try {
                line = Integer.parseInt(shootingLineInput);
                if (line < mapSize + 1 && line >= 1) {
                    break;
                }
            } catch (Exception ignored) {}

            printInvalidValue();
        }

        return line - 1;
    }

    static int askForColumn() {
        int column;
        while (true) {
            print("In which column do you want to shoot next?");

            String shootingColumnInput = new Scanner(System.in).nextLine();
            try {
                column = Integer.parseInt(shootingColumnInput);
                if (column < mapSize + 1 && column >= 1) {
                    break;
                }
            } catch (Exception ignored) {}

            printInvalidValue();
        }

        return column - 1;
    }

    static void printInvalidValue() {
        print("Invalid value! Try again!");
    }

    static void print(String text) {
        System.out.println(text);
    }

    static void print() {
        System.out.println();
    }

    static class Battleship {
        final int x;
        final int y;
        final int size;
        int piecesLeft;

        final Orientation orientation;

        Battleship(int x, int y, int size, Orientation orientation) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.piecesLeft = size;
            this.orientation = orientation;
        }

        enum Orientation {
            Horizontal,
            Vertical
        }
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
