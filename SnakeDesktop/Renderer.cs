using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SnakeModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SnakeDesktop
{
    class Renderer
    {
        private SnakeGame game;

        private float scale;
        private Texture2D head;
        private Texture2D body;
        private Texture2D background;

        private int paddingX;
        private int paddingY;

        public Renderer(SnakeGame game, GraphicsDevice gd)
        {
            this.game = game;
            scale = Math.Min(gd.Viewport.Width, gd.Viewport.Height) / (float)SnakeGame.SIZE;
            paddingX = (int)((gd.Viewport.Width - scale * SnakeGame.SIZE) / 2);
            paddingY = (int)((gd.Viewport.Height - scale * SnakeGame.SIZE) / 2);

            head = new Texture2D(gd, 1, 1);
            head.SetData<Color>(new Color[] { Color.DarkOrange });

            body = new Texture2D(gd, 1, 1);
            body.SetData<Color>(new Color[] { new Color(70,70,70) });

            background = new Texture2D(gd, 1, 1);
            background.SetData<Color>(new Color[] { new Color(16,16,16) });
        }

        public void Render(SpriteBatch sb)
        {
            sb.Begin();
            sb.Draw(background, new Rectangle((int)(0 * scale) + paddingX, (int)(0 * scale) + paddingY, (int)(SnakeGame.SIZE*scale), (int)(SnakeGame.SIZE*scale)), null, Color.White);
            sb.Draw(head, new Rectangle((int)(game.Snake.Head.X * scale) + paddingX, (int)(game.Snake.Head.Y * scale) + paddingY, (int)(scale), (int)scale), null, Color.White);
            foreach(SnekPiece piece in game.Snake.Pieces)
            {
                DrawSnekPiece(piece, sb);
            }
            sb.End();
        }

        private void DrawSnekPiece(SnekPiece piece, SpriteBatch sb)
        {
            sb.Draw(body, new Rectangle((int)(piece.X*scale) + paddingX, (int)(piece.Y*scale) + paddingY, (int)(scale), (int)scale), null, Color.White);
        }
    }
}
