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
    }

    public boolean isSunk() {
        return pieces == 0;
    }

    public boolean isOverlapping(Battleship battleship) {
        for (Coordinate coordinate : coordinates) {
            for (Coordinate battleshipCoordinate : battleship.coordinates) {
                if (coordinate.isEqualTo(battleshipCoordinate)) {
                    return true;
                }
            }
        }

        return false;
    }
}
