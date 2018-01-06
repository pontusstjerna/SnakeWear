package se.pontek.snakewear;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import se.pontek.snakewear.model.SnakeGame;

/**
 * Created by Pontus on 2018-01-05.
 */

public class Game extends View {
    private final int MIN_SWIPE_DIST = 100;

    private SnakeGame game;
    private Renderer renderer;
    private boolean exit = false;
    private float lastX;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, int width, int height, int speed, int highscore) {
        this(context);

        game = new SnakeGame();
        renderer = new Renderer(game, width, height, highscore);

        setOnTouchListener((s, e) -> {
            if (game.isGameOver()) {
                gotoMenu(context);
                return true;
            }

            if (e.getAction() == MotionEvent.ACTION_UP) {
                if (lastX - e.getX() > MIN_SWIPE_DIST) {
                    gotoMenu(context);
                } else {
                    game.changeDirection(
                            renderer.getGameX((int) e.getX()),
                            renderer.getGameY((int) e.getY())
                    );
                }
            } else if (e.getAction() == MotionEvent.ACTION_DOWN) {
                lastX = e.getX();
            }
            s.performClick();
            return true;
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                game.update();
                postInvalidate();
                if (game.isGameOver()) {
                    timer.cancel();
                }
            }
        };

        timer.schedule(task, 0, 700 / speed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.render(canvas);
    }

    private void gotoMenu(Context context) {
        MainActivity host = (MainActivity) context;
        host.showMainMenu(game.getScore());
    }
}
