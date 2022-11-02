package matej.mrozek.battleships;

public class Coordinate {
    public int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this. y = y;
    }

    public boolean isEqualTo(Coordinate coordinate) {
        return coordinate.x == x && coordinate.y == y;
    }
}
