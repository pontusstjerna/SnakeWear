using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using SnakeModel;
using Android.Graphics;

namespace SnakeWear
{
    public class Renderer
    {
        private SnakeGame game;

        private float scale;
        private Paint head;
        private Paint body;
        private Paint background;
        private Paint food;

        private int paddingX;
        private int paddingY;

        public Renderer(SnakeGame game, int width, int height)
        {
            this.game = game;

            scale = Math.Min(width, height) / (float)SnakeGame.SIZE;
            scale *= (float)Math.Cos(Math.PI / 4);

            paddingX = (int)((width - scale * SnakeGame.SIZE) / 2);
            paddingY = (int)((height - scale * SnakeGame.SIZE) / 2);

            head = new Paint();
            head.SetARGB(255, 255, 165, 0); //Darkorange
            head.SetStyle(Paint.Style.FillAndStroke);

            body = new Paint();
            body.SetARGB(255, 70, 70, 70);
            body.SetStyle(Paint.Style.FillAndStroke);

            background = new Paint();
            background.SetARGB(255, 16, 16, 16);
            background.SetStyle(Paint.Style.FillAndStroke);

            food = new Paint();
            food.SetARGB(255, 0, 255, 255);
            food.SetStyle(Paint.Style.FillAndStroke);
        }

        public void Render(Canvas canvas)
        {
            int headX = (int)(game.Snake.Head.X * scale) + paddingX;
            int headY = (int)(game.Snake.Head.Y * scale) + paddingY;
            canvas.DrawRect(new Rect((int)(0 * scale) + paddingX, (int)(0 * scale) + paddingY, (int)(SnakeGame.SIZE * scale), (int)(SnakeGame.SIZE * scale)), background);
            canvas.DrawRect(new Rect(headX, headY, headX + (int)(scale), headY + (int)scale), head); //head
            foreach (SnekPiece piece in game.Snake.Pieces)
            {
                DrawSnekPiece(piece, canvas);
            }

            int foodX = (int)(game.FoodX * scale) + paddingX;
            int foodY = (int)(game.FoodY * scale) + paddingY;
            canvas.DrawRect(new Rect(foodX, foodY, foodX + (int)(scale), foodY + (int)scale), food); //food
        }

        public int GetGameX(int x)
        {
            return (int)((x - paddingX) / scale);
        }

        public int GetGameY(int y)
        {
            return (int)((y - paddingY) / scale);
        }

        private void DrawSnekPiece(SnekPiece piece, Canvas canvas)
        {
            int x = (int)(piece.X * scale) + paddingX;
            int y = (int)(piece.Y * scale) + paddingY;
            canvas.DrawRect(new Rect(x, y, x + (int)(scale), y + (int)scale), body); //body
        }

        private void DrawScore(Canvas canvas)
        {
            sb.DrawString(main, "Highest: 000", new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, scale - (float)(Math.Cos(Math.PI / 2) * scale / 2)), Color.Wheat);
            sb.DrawString(main, "Current: " + GetScoreString(), new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, paddingY + scale * SnakeGame.SIZE + (scale - (float)(Math.Cos(Math.PI / 2) * scale / 2))), Color.Wheat);
        }

        private string GetScoreString()
        {
            return (1000 + game.Score).ToString().Substring(1);
        }

        private void DrawBackground(Color color, Canvas canvas)
        {
            sb.Draw(background, new Rectangle((int)(0 * scale) + paddingX, (int)(0 * scale) + paddingY, (int)(SnakeGame.SIZE * scale), (int)(SnakeGame.SIZE * scale)), null, color);
        }

        private void ShowGameOver(SpriteBatch sb)
        {
            DrawBackground(Color.DarkOrange, sb);
            sb.DrawString(main, "Game over!", new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, paddingY + (SnakeGame.SIZE * scale / 2) - 2 * scale), Color.Black);
        }
    }
}