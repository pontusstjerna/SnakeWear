package se.nocroft.wearsnakecommon.model;

import java.util.Random;

public class SnakeGame {

    public final static int SIZE = 20;

    private Snek snake;

    private int foodX;
    private int foodY;

    public boolean gameOver = false;

    private Random random;

    public SnakeGame() {
        random = new Random();
        snake = new Snek(
                random.nextInt(SIZE - 8) + 8,
                random.nextInt(SIZE - 8) + 8, 3, Direction.Directions.UP);
        spawnFood();
    }

    public void update() {
        if (gameOver) return;
        snake.update();
        checkBodyCollisions();
        if (gameOver) return;
        checkForFood();
    }

    public void changeDirection(int x, int y) {
        Direction headDir = snake.getHead().getDirection();
        SnekPiece head = snake.getHead();

        if (headDir.getY() == 0) {
            if (y > head.getY())
                head.setDirection(new Direction(Direction.Directions.DOWN));
            else
                head.setDirection(new Direction(Direction.Directions.UP));
        } else {
            if (x > head.getX())
                head.setDirection(new Direction(Direction.Directions.RIGHT));
            else
                head.setDirection(new Direction(Direction.Directions.LEFT));
        }

        head.setNextDirection(head.getDirection());
    }

    public Snek getSnake() {
        return snake;
    }

    public int getScore() {
        return snake.getBody().size() + 1;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    private void checkForFood() {
        if (snake.getHead().getX() == foodX && snake.getHead().getY() == foodY) {
            spawnFood();
            snake.eat();
        }
    }

    private void checkBodyCollisions() {
        for (SnekPiece bodyPart : snake.getBody()) {
            if (bodyPart.getX() == snake.getHead().getX() && bodyPart.getY() == snake.getHead().getY()) {
                gameOver = true;
                return;
            }
        }
    }

    private void spawnFood() {
        boolean valid = true;
        do {
            valid = true;
            foodX = random.nextInt(SIZE - 1);
            foodY = random.nextInt(SIZE - 1);

            if (snake.getHead().getX() == foodX && snake.getHead().getY() == foodY) valid = false;

            for (SnekPiece piece : snake.getBody()) {
                if (piece.getX() == foodX && piece.getY() == foodY) valid = false;
            }
        } while (!valid);
    }
}