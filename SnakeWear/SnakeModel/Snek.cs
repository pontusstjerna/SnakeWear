using System;
using System.Collections.Generic;
using System.Text;

namespace SnakeModel
{
    public class Snek
    {

        public List<SnekPiece> Pieces { get; private set; }
        public SnekPiece Head { get; private set; }

        public Snek(int startX, int startY, int startLength, Directions startDir)
        {
            Pieces = new List<SnekPiece>();

            Head = new SnekPiece(startX, startY, new Direction(startDir));
            Head.NextDirection = new Direction(startDir);
            Pieces.Add(new SnekPiece(Head.X - Head.Direction.X, Head.Y - Head.Direction.Y, Head.Direction));
            for (int i = 2; i < startLength; i++)
            {
                Eat();
            }
        }

        public void Update()
        {
            Pieces[0].NextDirection = Head.Direction;
            for(int i = 1; i < Pieces.Count; i++)
            {
                Pieces[i].NextDirection = Pieces[i - 1].NextDirection;
            }

            Head.Update();
            foreach (SnekPiece piece in Pieces)
                piece.Update();
        }

        public void Eat()
        {
            SnekPiece last = Pieces[Pieces.Count - 1];
            Pieces.Add(new SnekPiece(last.X - last.Direction.X, last.Y - last.Direction.Y, last.Direction));
        }

        
    }
}
