package pontus.wearsnake;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import pontus.wearsnake.model.SnakeGame;

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

        setFocusableInTouchMode(true);
        requestFocus();
        setOnKeyListener((view, keyCode, e) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && e.getRepeatCount() == 0) {
                gotoMenu(context);
                return true;
            }
            return super.onKeyDown(keyCode, e);
        });

        setOnTouchListener((s, e) -> {
            if (game.isGameOver()) {
                gotoMenu(context);
                return true;
            }

            if (e.getAction() == MotionEvent.ACTION_UP) {
                game.changeDirection(
                        renderer.getGameX((int) e.getX()),
                        renderer.getGameY((int) e.getY())
                );
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
