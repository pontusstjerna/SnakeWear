using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Support.Wearable.Views;
using Android.Support.V4.App;
using Android.Support.V4.View;
using Java.Interop;
using Android.Views.Animations;

namespace SnakeWear
{
    [Activity(Label = "Snake", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        int speed = 1;
        TextView txtSpeed;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            var v = FindViewById<WatchViewStub>(Resource.Id.watch_view_stub);
            v.LayoutInflated += delegate
            {
                txtSpeed = FindViewById<TextView>(Resource.Id.level);

                // Get our button from the layout resource,
                // and attach an event to it
                Button button = FindViewById<Button>(Resource.Id.btnPlay);
                ImageView decrease = FindViewById<ImageView>(Resource.Id.decrease);
                ImageView increase = FindViewById<ImageView>(Resource.Id.increase);

                decrease.Click += delegate { if (speed > 1) speed--; UpdateSpeed(); };
                increase.Click += delegate { if (speed < 50) speed++; UpdateSpeed(); };

                button.Click += delegate
                {

                };


            };
        }

        private void UpdateSpeed()
        {
            txtSpeed.Text = $"{speed}";
        }
    }
}


