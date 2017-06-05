using System;
using System.Collections.Generic;
using System.Text;

namespace SnakeModel
{
    public enum Directions { LEFT, UP, RIGHT, DOWN };

    public class Direction
    {
        public int X { get; }
        public int Y { get; }

        public Direction(Directions direction)
        {
            switch (direction)
            {
                case Directions.LEFT:
                    X = -1;
                    Y = 0;
                    break;
                case Directions.UP:
                    X = 0;
                    Y = -1;
                    break;
                case Directions.RIGHT:
                    X = 1;
                    Y = 0;
                    break;
                case Directions.DOWN:
                    X = 0;
                    Y = 1;
                    break;
            }
        }

        private Direction(int x, int y)
        {
            X = x;
            Y = y;
        }

        public Direction Clone()
        {
            return new Direction(X, Y);
        }
    }
}
