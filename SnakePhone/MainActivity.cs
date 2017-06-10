﻿using Android.App;
using Android.Widget;
using Android.OS;

namespace SnakeWear
{
    [Activity(Label = "Snake", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        private int speed = 1;
        private int highscore;
        TextView txtSpeed;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            ShowMainMenu(0);
        }

        public void ShowMainMenu(int score)
        {
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            txtSpeed = FindViewById<TextView>(Resource.Id.level);

            UpdateHighscore(score);

            Button button = FindViewById<Button>(Resource.Id.btnPlay);
            ImageView decrease = FindViewById<ImageView>(Resource.Id.decrease);
            ImageView increase = FindViewById<ImageView>(Resource.Id.increase);

            decrease.Click += delegate { if (speed > 1) speed--; UpdateSpeed(); };
            increase.Click += delegate { if (speed < 50) speed++; UpdateSpeed(); };

            button.Click += delegate
            {
                Android.Graphics.Point size = new Android.Graphics.Point();
                WindowManager.DefaultDisplay.GetRealSize(size);
                var game = new Game(this, size.X, size.Y, speed, highscore);
                SetContentView(game);
            };
        }

        private void UpdateSpeed()
        {
            txtSpeed.Text = $"{speed}";
        }

        private void UpdateHighscore(int score)
        {
            var prefs = GetPreferences(FileCreationMode.Private);
            TextView txtHighscore = FindViewById<TextView>(Resource.Id.highscore);
            highscore = Math.Max(prefs.GetInt("highscore", 0), score);
            txtHighscore.Text = $"Highscore: {highscore}";
            var edit = prefs.Edit();
            edit.PutInt("highscore", highscore);
            edit.Commit();
        }
    }
}

