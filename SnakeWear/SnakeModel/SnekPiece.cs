using System;
using System.Collections.Generic;
using System.Text;

namespace SnakeModel
{
    public class SnekPiece
    {
        public int X { get; set; }
        public int Y { get; set; }
        public Direction Direction { get; private set; }
        public Direction NextDirection { get; set; }

        public SnekPiece(int x, int y, Direction direction)
        {
            X = x;
            Y = y;
            this.Direction = direction;
        }

        public void Update()
        {
            if (X + Direction.X >= SnakeGame.SIZE) X -= SnakeGame.SIZE;
            else if (X + Direction.X <= 0) X += SnakeGame.SIZE;
            else if (Y + Direction.Y >= SnakeGame.SIZE) X -= SnakeGame.SIZE;
            else if (Y + Direction.Y <= 0) Y += SnakeGame.SIZE;

            X += Direction.X;
            Y += Direction.Y;
            
            Direction = NextDirection;
        }
    }
}
