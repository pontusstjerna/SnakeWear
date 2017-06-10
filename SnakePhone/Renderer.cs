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
        private Paint backgroundStroke;
        private Paint food;
        private Paint info;
        private Paint gameOver;

        private float paddingX;
        private float paddingY;
        private int width;
        private int height;
        private int highscore;

        public Renderer(SnakeGame game, int width, int height, int highscore)
        {
            this.game = game;
            this.width = width;
            this.height = height;
            this.highscore = highscore;

            scale = (Math.Min(width, height) / (float)SnakeGame.SIZE) * ((float)Math.Cos(Math.PI / 4));

            paddingX = (width - scale * SnakeGame.SIZE) / 2;
            paddingY = (height - scale * SnakeGame.SIZE) / 2;

            head = new Paint();
            head.Color = Color.DarkOrange;
            head.SetStyle(Paint.Style.FillAndStroke);

            body = new Paint();
            body.Color = new Color(100, 100, 100);
            body.SetStyle(Paint.Style.FillAndStroke);

            background = new Paint();
            background.Color = new Color(16, 16, 16);
            background.SetStyle(Paint.Style.Fill);

            backgroundStroke = new Paint();
            backgroundStroke.Color = Color.DarkOrange;
            backgroundStroke.SetStyle(Paint.Style.Stroke);

            food = new Paint();
            food.Color = Color.Cyan;
            food.SetStyle(Paint.Style.FillAndStroke);

            info = new Paint();
            info.Color = Color.Wheat;
            info.TextSize = scale*2;
            info.TextAlign = Paint.Align.Center;
            info.SetStyle(Paint.Style.Fill);

            gameOver = new Paint();
            gameOver.Color = Color.DarkOrange;
            gameOver.SetStyle(Paint.Style.FillAndStroke);
            gameOver.TextSize = 4 * scale;
            gameOver.TextAlign = Paint.Align.Center;
        }

        public void Render(Canvas canvas)
        {
            if (!game.GameOver)
            {
                canvas.DrawColor(Color.Black);
                DrawBackground(background, canvas);
                DrawBackground(backgroundStroke, canvas);
                canvas.DrawRect(game.Snake.Head.X * scale + paddingX, game.Snake.Head.Y * scale + paddingY, game.Snake.Head.X * scale + paddingX + scale, game.Snake.Head.Y * scale + paddingY + scale, head);
                foreach (SnekPiece piece in game.Snake.Pieces)
                {
                    DrawSnekPiece(piece, canvas);
                }

                float foodX = game.FoodX * scale + paddingX;
                float foodY = game.FoodY * scale + paddingY;
                canvas.DrawRect(foodX, foodY, foodX + scale, foodY + scale, food); //food
                DrawScore(canvas);
            }
            else
            {
                ShowGameOver(canvas);
            }
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
            canvas.DrawRect(piece.X * scale + paddingX, piece.Y * scale + paddingY, piece.X * scale + paddingX + scale, piece.Y * scale + paddingY + scale, body);
        }

        private void DrawScore(Canvas canvas)
        {
            canvas.DrawText("Highest: " + GetHighscoreString(), width/2, paddingY - scale/2, info);
            canvas.DrawText("Current: " + GetScoreString(), width/2, paddingY + SnakeGame.SIZE*scale + scale*2, info);
        }

        private string GetScoreString()
        {
            return (1000 + game.Score).ToString().Substring(1);
        }

        private string GetHighscoreString()
        {
            return (1000 + Math.Max(highscore, game.Score)).ToString().Substring(1);
        }

        private void DrawBackground(Paint paint, Canvas canvas)
        {
            canvas.DrawRect(paddingX, paddingY, paddingX + SnakeGame.SIZE * scale, paddingY + SnakeGame.SIZE * scale, paint);
        }

        private void ShowGameOver(Canvas canvas)
        {
            string gameover = "Game over!";
            if (game.Score > highscore) gameover = "New highscore!";

            canvas.DrawPaint(background);
            gameOver.TextSize = 4 * scale;
            canvas.DrawText(gameover, width/2, height/2 - scale*2, gameOver);
            gameOver.TextSize = 3 * scale;
            canvas.DrawText("Your score: " + GetScoreString(), width / 2, height / 2 + scale * 2, gameOver);
            gameOver.TextSize = 2 * scale;
            canvas.DrawText("Tap to continue...", width / 2, height / 2 + scale * 8, gameOver);
        }
    }
}