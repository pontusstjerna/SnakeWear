package pontus.wearsnake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pontus.wearsnake.model.SnakeGame;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pontus on 2018-01-05.
 */

public class Game extends View {
    private SnakeGame game;
    private Renderer renderer;
    private Timer timer;
    private InterstitialAd bigAd;
    private Random random;
    public Game(Context context) {
        super(context);
    }

    public Game(Context context, int width, int height, int speed, int highscore, InterstitialAd bigAd) {
        this(context);

        this.bigAd = bigAd;
        game = new SnakeGame();
        renderer = new Renderer(game, width, height, highscore);

        random = new Random();

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
            }

            s.performClick();
            return true;
        });

        timer = new Timer();
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
        timer.cancel();

        // Save highscore
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), MODE_PRIVATE);
        int highscore = Math.max(prefs.getInt("highscore", 0), game.getScore());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("highscore", highscore);
        edit.apply();

        Intent goBack = new Intent(context, MainActivity.class);
        goBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (random.nextBoolean()) {
            bigAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    context.startActivity(goBack);
                }
            });
            bigAd.show();
        } else {
            context.startActivity(goBack);
        }
    }
}
