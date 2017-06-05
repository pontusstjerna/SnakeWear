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
            X += Direction.X;
            Y += Direction.Y;
            Direction = NextDirection;
        }
    }
}
