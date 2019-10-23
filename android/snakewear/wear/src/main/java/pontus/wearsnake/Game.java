package pontus.wearsnake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import se.nocroft.wearsnakecommon.model.SnakeGame;

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

    @SuppressLint("ClickableViewAccessibility")
    public Game(Context context, int width, int height, int speed, int highscore) {
        this(context);

        game = new SnakeGame();
        renderer = new Renderer(game, width, height, highscore);

        setOnTouchListener((view, motionEvent) -> onTap(view, motionEvent, context));

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

    private boolean onTap(View view, MotionEvent motionEvent, Context context) {
        if (game.isGameOver()) {
            gotoMenu(context);
            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (lastX - motionEvent.getX() > MIN_SWIPE_DIST) {
                gotoMenu(context);
            } else {
                game.changeDirection(
                        renderer.getGameX((int) motionEvent.getX()),
                        renderer.getGameY((int) motionEvent.getY())
                );
            }
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = motionEvent.getX();
        }
        view.performClick();
        return true;
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
