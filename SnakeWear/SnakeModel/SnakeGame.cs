using System;
using System.Collections.Generic;
using System.Text;

namespace SnakeModel
{
    public class SnakeGame
    {

        public const int SIZE = 20;
        public Snek Snake { get; private set; }
        public int Score { get { return Snake.Pieces.Count; } }

        public SnakeGame()
        {
            Snake = new Snek(10, 5, 3, Directions.RIGHT);
        }

        public void Update()
        {
            Snake.Update();
        }

        public void Goto(int x, int y)
        {
            var headDir = Snake.Head.Direction;
            var head = Snake.Head;

            if(headDir.Y == 0)
            {
                if (y > head.Y)
                    head.NextDirection = new Direction(Directions.DOWN);
                else
                    head.NextDirection = new Direction(Directions.UP);
            }
            else
            {
                if (x > head.X)
                    head.NextDirection = new Direction(Directions.RIGHT);
                else
                    head.NextDirection = new Direction(Directions.LEFT);
            }
        }
    }
}
