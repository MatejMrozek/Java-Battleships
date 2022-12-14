package matej.mrozek.battleships;

public record Coordinate(int x, int y) {
    public boolean isEqualTo(Coordinate coordinate) {
        return coordinate.x == x && coordinate.y == y;
    }

    @Override
    public String toString() {
        return "x,y=" + x + "," + y;
    }
}
