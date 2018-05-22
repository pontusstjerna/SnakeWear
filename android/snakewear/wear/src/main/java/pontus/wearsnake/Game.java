package pontus.wearsnake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import pontus.wearsnake.model.SnakeGame;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pontus on 2018-01-05.
 */

public class Game extends View {
    private final int MIN_SWIPE_DIST = 100;

    private SnakeGame game;
    private Renderer renderer;
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
        // Save highscore
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), MODE_PRIVATE);
        int highscore = Math.max(prefs.getInt("highscore", 0), game.getScore());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("highscore", highscore);
        edit.apply();

        Intent goBack = new Intent(context, MainActivity.class);
        goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(goBack);
    }
}
