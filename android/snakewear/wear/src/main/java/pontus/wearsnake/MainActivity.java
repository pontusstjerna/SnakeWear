package pontus.wearsnake;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends WearableActivity {

    private int speed;
    private int highscore;

    TextView txtSpeed;
    TextView txtHighscore;
    Button btnPlay;
    ImageView imgIncrease;
    ImageView imgDecrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        speed = getPreferences(MODE_PRIVATE).getInt("speed",
                Integer.parseInt(getString(R.string.init_level)));

        setContentView(R.layout.activity_main);

        initComponents();

        showMainMenu();
    }

    public void showMainMenu()
    {
        txtSpeed.setText(String.valueOf(speed));
        updateHighscore();

        imgDecrease.setOnClickListener(x -> {
            if (speed > 1) {
                speed--;
                updateSpeed(txtSpeed);
            }
        });

        imgIncrease.setOnClickListener(x -> {
            if (speed < 50) {
                speed++;
                updateSpeed(txtSpeed);
            }
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
                    highscore
            );
            setContentView(game);
            Toast.makeText(
                    this,
                    "Swipe left to go back",
                    Toast.LENGTH_LONG
            ).show();
        });
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

    private void initComponents() {
        txtHighscore = findViewById(R.id.highscore);
        txtSpeed = findViewById(R.id.level);
        btnPlay = findViewById(R.id.btnPlay);
        imgDecrease = findViewById(R.id.decrease);
        imgIncrease = findViewById(R.id.increase);
    }
}
