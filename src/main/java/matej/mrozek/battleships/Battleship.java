package matej.mrozek.battleships;

public class Battleship {
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
