package model;

public enum Direction {
    DOWN(1, 0), LEFT(0, -1), UP(-1, 0), RIGHT(0, 1);

    public final int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int directionToInt(Direction dir) {
        switch (dir) {
            case DOWN -> {
                return 0;
            }
            case LEFT -> {
                return 1;
            }
            case UP -> {
                return 2;
            }
            case RIGHT -> {
                return 3;
            }
        }
        return -1;
    }

    public static String intToDirection(int d) {
        return switch (d) {
            case 0 -> "Down";
            case 1 -> "Left";
            case 2 -> "Up";
            case 3 -> "Right";
            default -> null;
        };
    }
}
