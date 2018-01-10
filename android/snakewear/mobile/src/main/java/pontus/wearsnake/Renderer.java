package pontus.wearsnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pontus.wearsnake.model.SnakeGame;
import pontus.wearsnake.model.SnekPiece;

/**
 * Created by Pontus on 2018-01-05.
 */

public class Renderer {
    private final int DARK_ORANGE = Color.parseColor("#ee7600");
    private final int GRAY = Color.parseColor("#646464");
    private final int DARK_GRAY = Color.parseColor("#101010");
    private final int CYAN = Color.parseColor("#00ffff");
    private final int WHEAT = Color.parseColor("#f5deb3");

    private SnakeGame game;

    private float scale;

    private Paint paint;

    private float paddingX;
    private float paddingY;
    private int width;
    private int height;
    private int highscore;

    public Renderer(SnakeGame game, int width, int height, int highscore) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.highscore = highscore;

        scale = (Math.min(width, height) / SnakeGame.SIZE) * (float) (Math.cos(Math.PI / 4));

        paddingX = (width - scale * SnakeGame.SIZE) / 2;
        paddingY = (height - scale * SnakeGame.SIZE) / 2;

        paint = new Paint();
    }

    public void render(Canvas canvas) {
        if (!game.isGameOver()) {
            canvas.drawRGB(0,0,0);

            paint.setColor(DARK_GRAY);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            drawBackground(paint, canvas);
            paint.setColor(DARK_ORANGE);
            paint.setStyle(Paint.Style.STROKE);
            drawBackground(paint, canvas);

            drawHead(canvas);
            paint.setColor(GRAY);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            for (SnekPiece piece : game.getSnake().getBody()) {
                drawSnekPiece(piece, canvas);
            }

            float foodX = game.getFoodX() * scale + paddingX;
            float foodY = game.getFoodY() * scale + paddingY;
            paint.setColor(CYAN);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRect(foodX, foodY, foodX + scale, foodY + scale, paint); //food
            drawScore(canvas);
        } else {
            showGameOver(canvas);
        }
    }

    public int getGameX(int x) {
        return (int) ((x - paddingX) / scale);
    }

    public int getGameY(int y) {
        return (int) ((y - paddingY) / scale);
    }


    private void drawHead(Canvas canvas) {
        paint.setColor(DARK_ORANGE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(
                game.getSnake().getHead().getX() * scale + paddingX,
                game.getSnake().getHead().getY() * scale + paddingY,
                game.getSnake().getHead().getX() * scale + paddingX + scale,
                game.getSnake().getHead().getY() * scale + paddingY + scale,
                paint);
    }

    private void drawSnekPiece(SnekPiece piece, Canvas canvas) {
        canvas.drawRect(
                piece.getX() * scale + paddingX,
                piece.getY() * scale + paddingY,
                piece.getX() * scale + paddingX + scale,
                piece.getY() * scale + paddingY + scale,
                paint);
    }

    private void drawBackground(Paint paint, Canvas canvas) {
        canvas.drawRect(paddingX, paddingY, paddingX + SnakeGame.SIZE * scale, paddingY + SnakeGame.SIZE * scale, paint);
    }

    private void showGameOver(Canvas canvas) {
        String gameover = "Game over!";
        if (game.getScore() > highscore) gameover = "New high score!";

        paint.setColor(DARK_ORANGE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawColor(DARK_GRAY);
        paint.setTextSize(3 * scale);
        canvas.drawText(gameover, width / 2, height / 2 - scale * 2, paint);
        paint.setTextSize(2 * scale);
        canvas.drawText("Your score: " + getScoreString(), width / 2, height / 2 + scale * 2, paint);
        paint.setTextSize(scale);
        paint.setColor(WHEAT);
        canvas.drawText("Tap to continue...", width / 2, height / 2 + scale * 8, paint);
    }

    private void drawScore(Canvas canvas) {
        paint.setColor(WHEAT);
        paint.setTextSize(scale * 2);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Highest: " + getHighscoreString(), width / 2, paddingY - scale / 2, paint);
        canvas.drawText("Current: " + getScoreString(), width / 2, paddingY + SnakeGame.SIZE * scale + scale * 2, paint);
    }

    private String getScoreString() {
        return String.valueOf(1000 + game.getScore()).substring(1);
    }

    private String getHighscoreString() {
        return String.valueOf(1000 + Math.max(highscore, game.getScore())).substring(1);
    }
}
