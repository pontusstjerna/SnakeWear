package se.pontek.snakewear.model;

public class SnekPiece {

    private Direction direction;
    private Direction nextDirection;

    private int x, y;

    public SnekPiece(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void update() {
        if (x + direction.getX() >= SnakeGame.SIZE) x -= SnakeGame.SIZE;
        else if (x + direction.getX() < 0) x += SnakeGame.SIZE;
        else if (y + direction.getY() >= SnakeGame.SIZE) y -= SnakeGame.SIZE;
        else if (y + direction.getY() < 0) y += SnakeGame.SIZE;

        x += direction.getX();
        y += direction.getY();

        direction = nextDirection;
    }

    Direction getDirection() {
        return direction;
    }

    Direction getNextDirection() {
        return nextDirection;
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }

    void setNextDirection(Direction direction) {
        this.nextDirection = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }
}
