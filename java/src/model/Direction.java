package model;

public class Direction {
    public enum Directions {LEFT, UP, RIGHT, DOWN}

    private int x, y;

    public Direction(Directions direction) {
        switch (direction) {
            case LEFT:
                x = -1;
                y = 0;
                break;
            case UP:
                x = 0;
                y = -1;
                break;
            case RIGHT:
                x = 1;
                y = 0;
                break;
            case DOWN:
                x = 0;
                y = 1;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Direction clone() {
        return new Direction(x, y);
    }
}