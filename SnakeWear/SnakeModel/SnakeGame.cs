﻿using System;
using System.Collections.Generic;
using System.Text;

namespace SnakeModel
{
    public class SnakeGame
    {
        public Snek Snake { get; private set; }

        public SnakeGame()
        {
            Snake = new Snek(10, 5, 3, Directions.RIGHT);
        }

        public void Update()
        {
            Snake.Update();
        }

        public void TurnLeft()
        {

        }

        public void TurnRight()
        {

        }
    }
}
