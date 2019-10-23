package pontus.wearsnake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainActivity extends Activity {

    private int speed;
    private int highscore;
    private Animation animation;

    TextView txtSpeed;
    TextView txtHighscore;
    Button btnPlay;
    ImageView imgIncrease;
    ImageView imgDecrease;

    InterstitialAd bigAd;
    AdView adTop, adBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.play_wear);

        speed = getPreferences(MODE_PRIVATE).getInt("speed",
                Integer.parseInt(getString(R.string.init_level)));

        setContentView(R.layout.activity_main);
        initComponents();
        initAnimation();
        initAds();
        showMainMenu();
    }

    public void showMainMenu()
    {
        txtSpeed.setText(String.valueOf(speed));
        updateHighscore();

        imgDecrease.setOnClickListener(x -> {
            if (speed > 1) speed--; updateSpeed(txtSpeed);
        });

        imgIncrease.setOnClickListener(x -> {
            if (speed < 50) speed++; updateSpeed(txtSpeed);
        });

        btnPlay.setOnClickListener(x ->
        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Game game = new Game(
                    this,
                    metrics.widthPixels,
                    metrics.heightPixels,
                    speed,
                    highscore,
                    bigAd
            );
            setContentView(game);
            Toast.makeText(
                    this,
                    "Tap in the direction you want to go!",
                    Toast.LENGTH_LONG
            ).show();
        });
    }

    private void initAds() {
        MobileAds.initialize(this, "ca-app-pub-2757743767659351~9555852028");
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713"); // Test


        bigAd = new InterstitialAd(this);
        bigAd.setAdUnitId("ca-app-pub-2757743767659351/2179020563");
        bigAd.loadAd(new AdRequest.Builder().build());

        //adTop = findViewById(R.id.adViewTop);
        //adBottom = findViewById(R.id.adViewBottom);
        adBottom.loadAd(new AdRequest.Builder().build());
        adTop.loadAd(new AdRequest.Builder().build());
    }

    private void initAnimation() {
        findViewById(R.id.txtPlayWear).startAnimation(animation);
        animation.start();
    }

    private void initComponents() {
        txtHighscore = findViewById(R.id.highscore);
        txtSpeed = findViewById(R.id.level);
        btnPlay = findViewById(R.id.btnPlay);
        imgDecrease = findViewById(R.id.decrease);
        imgIncrease = findViewById(R.id.increase);
    }

    @SuppressLint("SetTextI18n")
    private void updateHighscore() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        highscore = prefs.getInt("highscore", MODE_PRIVATE);
        txtHighscore.setText("High score: " + highscore);
    }

    private void updateSpeed(TextView txtSpeed) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("speed", speed);
        edit.apply();
        txtSpeed.setText(String.valueOf(speed));
    }
}
