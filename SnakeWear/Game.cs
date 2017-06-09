using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Android.Graphics.Drawables;
using SnakeModel;

namespace SnakeWear
{
    public class Game : View
    {
        private SnakeGame game;
        private Renderer renderer;
        private System.Timers.Timer longPressTimer;
        private bool exit = false;

        public Game(Context context, int width, int height, int speed) : base(context)
        {
            game = new SnakeGame();

            renderer = new Renderer(game, width, height);

            //This is why I love lambdas. This is all the game steering I need to do.
            Touch += (s, e) => { if (e.Event.Action == MotionEventActions.Up) game.Goto(renderer.GetGameX((int)e.Event.GetX()), renderer.GetGameY((int)e.Event.GetY())); };
            Touch += (s, e) => { if (game.GameOver) GotoMenu(context); };
            ConfigureLongPress(context);

            var t = new System.Timers.Timer();
            t.Interval = 700/speed;
            t.Elapsed += (s, e) =>
            {
                game.Update();
                PostInvalidate();
                if (game.GameOver) t.Stop();
            };
            t.Start();
        }

        protected override void OnDraw(Canvas canvas)
        {
            base.OnDraw(canvas);
            renderer.Render(canvas);
        }

        private void GotoMenu(Context context)
        {
            MainActivity host = (MainActivity)context;
            host.ShowMainMenu();
        }

        private void ConfigureLongPress(Context context)
        {
            Touch += (s, e) =>
            {
                if (e.Event.Action == MotionEventActions.Down) longPressTimer.Start();
                if (e.Event.Action == MotionEventActions.Up)
                {
                    longPressTimer.Stop();
                    if (exit) GotoMenu(context);
                }
            };

            longPressTimer = new System.Timers.Timer();
            longPressTimer.Interval = 3000;
            longPressTimer.Elapsed += (s, e) => exit = true;
        }
    }   
}