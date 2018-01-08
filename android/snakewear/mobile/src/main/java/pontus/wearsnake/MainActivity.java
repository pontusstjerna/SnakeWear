package pontus.wearsnake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {

    private int speed;
    private int highscore;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.play_wear);
       // animation.setRepeatCount(Animation.INFINITE);

        speed = getPreferences(MODE_PRIVATE).getInt("speed",
                Integer.parseInt(getString(R.string.init_level)));

        showMainMenu(0);
    }

    public void showMainMenu(int score)
    {
        // Set our view from the "activity_main" layout R
        setContentView(R.layout.activity_main);

        initAds();
        initAnimation();

        TextView txtSpeed = findViewById(R.id.level);
        txtSpeed.setText(String.valueOf(speed));

        updateHighscore(score);

        Button button = findViewById(R.id.btnPlay);
        ImageView decrease = findViewById(R.id.decrease);
        ImageView increase = findViewById(R.id.increase);

        decrease.setOnClickListener(x -> {
            if (speed > 1) speed--; updateSpeed(txtSpeed);
        });

        increase.setOnClickListener(x -> {
            if (speed < 50) speed++; updateSpeed(txtSpeed);
        });

        button.setOnClickListener(x ->
        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Game game = new Game(
                    this,
                    metrics.widthPixels,
                    metrics.heightPixels,
                    speed,
                    highscore
            );
            setContentView(game);
            Toast.makeText(
                    this,
                    "Press back to go to menu",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void initAds() {
        AdView adViewTop = findViewById(R.id.adViewTop);
        adViewTop.loadAd(new AdRequest.Builder().build());

        AdView adViewBottom = findViewById(R.id.adViewBottom);
        adViewBottom.loadAd(new AdRequest.Builder().build());
    }

    private void initAnimation() {
        findViewById(R.id.txtPlayWear).startAnimation(animation);
        animation.start();
    }

    @SuppressLint("SetTextI18n")
    private void updateHighscore(int score) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        TextView txtHighscore = findViewById(R.id.highscore);
        highscore = Math.max(prefs.getInt("highscore", 0), score);
        txtHighscore.setText("High score: " + highscore);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("highscore", highscore);
        edit.apply();
    }

    private void updateSpeed(TextView txtSpeed) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("speed", speed);
        edit.apply();
        txtSpeed.setText(String.valueOf(speed));
    }
}
