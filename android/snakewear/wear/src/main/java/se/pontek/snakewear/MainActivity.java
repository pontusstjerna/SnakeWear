package se.pontek.snakewear;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends WearableActivity {

    private int speed;
    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        speed = getPreferences(MODE_PRIVATE).getInt("speed",
                Integer.parseInt(getString(R.string.init_level)));

        showMainMenu(0);
    }

    public void showMainMenu(int score)
    {
        // Set our view from the "activity_main" layout R
        setContentView(R.layout.activity_main);

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
                    "Swipe left to go back",
                    Toast.LENGTH_LONG
            ).show();
        });
    }

//    @Override
//    public void onEnterAmbient(Bundle ambientDetails) {
//        super.onEnterAmbient(ambientDetails);
//        //showMainMenu(0);
//    }
//
//    @Override
//    public void onUpdateAmbient() {
//        super.onUpdateAmbient();
//        //showMainMenu(0);
//    }
//
//    @Override
//    public void onExitAmbient() {
//        super.onExitAmbient();
//        //showMainMenu(0);
//    }

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
