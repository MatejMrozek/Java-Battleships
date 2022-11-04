package matej.mrozek.battleships;

import java.util.List;

public class Battleship {
    private final List<Coordinate> coordinates;

    private int pieces;

    Battleship(List<Coordinate> coordinates, int size) {
        this.coordinates = coordinates;
        this.pieces = size;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void removePiece() {
        pieces--;
        
        Game.DEBUG.info("Battleship piece removed.");
    }

    public boolean isSunk() {
        return pieces == 0;
    }

    public boolean isOverlapping(Battleship battleship) {
        Game.DEBUG.info("Checking for an overlap.");

        boolean overlap = false;
        for (Coordinate coordinate : coordinates) {
            for (Coordinate battleshipCoordinate : battleship.coordinates) {
                if (coordinate.isEqualTo(battleshipCoordinate)) {
                    overlap = true;
                    break;
                }
            }
        }

        Game.DEBUG.info("The battleship is" + (overlap ? " " : " not ") + "overlapping a battleship.");

        return overlap;
    }
}
