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

        private float Scale = 20;
        private Texture2D head;
        private Texture2D body;

        public Renderer(SnakeGame game, GraphicsDevice gd)
        {
            this.game = game;

            head = new Texture2D(gd, 1, 1);
            head.SetData<Color>(new Color[] { Color.DarkOrange });

            body = new Texture2D(gd, 1, 1);
            body.SetData<Color>(new Color[] { new Color(70,70,70) });
        }

        public void Render(SpriteBatch sb)
        {
            sb.Begin();
            sb.Draw(head, new Rectangle((int)(game.Snake.Head.X * Scale), (int)(game.Snake.Head.Y * Scale), (int)(Scale), (int)Scale), null, Color.White);
            foreach(SnekPiece piece in game.Snake.Pieces)
            {
                DrawSnekPiece(piece, sb);
            }
            sb.End();
        }

        private void DrawSnekPiece(SnekPiece piece, SpriteBatch sb)
        {
            sb.Draw(body, new Rectangle((int)(piece.X*Scale), (int)(piece.Y*Scale), (int)(Scale), (int)Scale), null, Color.White);
        }
    }
}
