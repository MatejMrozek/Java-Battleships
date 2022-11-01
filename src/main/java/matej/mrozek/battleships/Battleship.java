package matej.mrozek.battleships;

import java.util.List;

public class Battleship {
    final List<Coordinate> coordinates;
    final int size;
    int piecesLeft;

    final Orientation orientation;

    Battleship(List<Coordinate> coordinates, int size, Orientation orientation) {
        this.coordinates = coordinates;
        this.size = size;
        this.piecesLeft = size;
        this.orientation = orientation;
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

    enum Orientation {
        Horizontal,
        Vertical
    }
}
