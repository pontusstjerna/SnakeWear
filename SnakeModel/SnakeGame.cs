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

        public int FoodX { get; private set; }
        public int FoodY { get; private set; }

        public bool GameOver { get; private set; }

        private Random random;

        public SnakeGame()
        {
            random = new Random();
            Snake = new Snek(random.Next(SIZE - 4) + 4, random.Next(SIZE - 4) + 4, 3, Directions.UP);
            SpawnFood();
        }

        public void Update()
        {
            if (GameOver) return;
            Snake.Update();
            CheckForFood();
            CheckBodyCollisions();
        }

        public void Goto(int x, int y)
        {
            var headDir = Snake.Head.Direction;
            var head = Snake.Head;

            if(headDir.Y == 0)
            {
                if (y > head.Y)
                    head.Direction = new Direction(Directions.DOWN);
                else
                    head.Direction = new Direction(Directions.UP);
            }
            else
            {
                if (x > head.X)
                    head.Direction = new Direction(Directions.RIGHT);
                else
                    head.Direction = new Direction(Directions.LEFT);
            }

            head.NextDirection = head.Direction;
        }

        private void CheckForFood()
        {
            if(Snake.Head.X == FoodX && Snake.Head.Y == FoodY)
            {
                SpawnFood();
                Snake.Eat();
            }
        }

        private void CheckBodyCollisions()
        {
            GameOver = Snake.Pieces.Exists(x => x.X == Snake.Head.X && x.Y == Snake.Head.Y);
        }

        private void SpawnFood()
        {
            bool valid = true;
            do
            {
                FoodX = random.Next(SIZE - 1);
                FoodY = random.Next(SIZE - 1);

                if (Snake.Head.X == FoodX && Snake.Head.Y == FoodY) valid = false;

                foreach (var piece in Snake.Pieces)
                    if (piece.X == FoodX && Snake.Head.Y == FoodY) valid = false;

            } while (!valid);
        }
    }
}
