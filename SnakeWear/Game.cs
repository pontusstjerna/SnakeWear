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

        public Game(Context context, int width, int height) : base(context)
        {
            game = new SnakeGame();
            
            renderer = new Renderer(game, width, height);

            //This is why I love lambdas. This is all the game steering I need to do.
            Touch += (s, e) => { if (e.Event.Action == MotionEventActions.Up) game.Goto(renderer.GetGameX((int)e.Event.GetX()), renderer.GetGameY((int)e.Event.GetY())); };
            Touch += (s, e) =>
            {
                if (game.GameOver)
                {
                    Activity host = (Activity)context;
                    host.SetContentView(Resource.Layout.Main);
                }
            };
 

            var t = new System.Timers.Timer();
            t.Interval = 700;
            t.Elapsed += (s,e) => 
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
    }
}