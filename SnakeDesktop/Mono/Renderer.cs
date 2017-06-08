using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SnakeModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SnakeDesktop.Mono
{
    class Renderer
    {
        private SnakeGame game;

        private float scale;
        private Texture2D head;
        private Texture2D body;
        private Texture2D background;
        private Texture2D food;
        private SpriteFont main;

        private int paddingX;
        private int paddingY;

        public Renderer(SnakeGame game, GraphicsDevice gd, SpriteFont main)
        {
            this.game = game;

            scale = ((Math.Min(gd.Viewport.Width, gd.Viewport.Height) / (float)SnakeGame.SIZE)) * (float)Math.Cos(Math.PI/4); //For round watch faces

            paddingX = (int)((gd.Viewport.Width - scale * SnakeGame.SIZE) / 2);
            paddingY = (int)((gd.Viewport.Height - scale * SnakeGame.SIZE) / 2);

            head = new Texture2D(gd, 1, 1);
            head.SetData<Color>(new Color[] { Color.DarkOrange });

            body = new Texture2D(gd, 1, 1);
            body.SetData<Color>(new Color[] { new Color(70,70,70) });

            background = new Texture2D(gd, 1, 1);
            background.SetData<Color>(new Color[] { Color.White });

            food = new Texture2D(gd, 1, 1);
            food.SetData<Color>(new Color[] { Color.Cyan });

            this.main = main;
        }

        public void Render(SpriteBatch sb)
        {
            sb.Begin();

            if (!game.GameOver)
            {
                DrawBackground(new Color(16, 16, 16), sb);
                sb.Draw(head, new Rectangle((int)(game.Snake.Head.X * scale) + paddingX, (int)(game.Snake.Head.Y * scale) + paddingY, (int)(scale), (int)scale), null, Color.White);
                foreach (SnekPiece piece in game.Snake.Pieces)
                {
                    DrawSnekPiece(piece, sb);
                }
                sb.Draw(food, new Rectangle((int)(game.FoodX * scale) + paddingX, (int)(game.FoodY * scale) + paddingY, (int)(scale), (int)scale), null, Color.White);
            }
            else
            {
                ShowGameOver(sb);
            }

            DrawScore(sb);

            sb.End();
        }

        public int GetGameX(int x)
        {
            return (int)((x - paddingX) / scale);
        }

        public int GetGameY(int y)
        {
            return (int)((y - paddingY) / scale);
        }

        private void DrawSnekPiece(SnekPiece piece, SpriteBatch sb)
        {
            sb.Draw(body, new Rectangle((int)(piece.X*scale) + paddingX, (int)(piece.Y*scale) + paddingY, (int)(scale), (int)scale), null, Color.White);
        }

        private void DrawScore(SpriteBatch sb)
        {
            sb.DrawString(main, "Highest: 000", new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, scale - (float)(Math.Cos(Math.PI/2)*scale/2)), Color.Wheat);
            sb.DrawString(main, "Current: " + GetScoreString(), new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, paddingY + scale*SnakeGame.SIZE + (scale - (float)(Math.Cos(Math.PI / 2) * scale / 2))), Color.Wheat);
        }

        private string GetScoreString()
        {
            return (1000 + game.Score).ToString().Substring(1);
        }

        private void DrawBackground(Color color, SpriteBatch sb)
        {
            sb.Draw(background, new Rectangle((int)(0 * scale) + paddingX, (int)(0 * scale) + paddingY, (int)(SnakeGame.SIZE * scale), (int)(SnakeGame.SIZE * scale)), null, color);
        }

        private void ShowGameOver(SpriteBatch sb)
        {
            DrawBackground(Color.DarkOrange, sb);
            sb.DrawString(main, "Game over!", new Vector2(paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5, paddingY + (SnakeGame.SIZE * scale / 2) - 2*scale), Color.Black);
        }
    }
}
