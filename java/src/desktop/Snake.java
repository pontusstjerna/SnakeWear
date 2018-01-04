package desktop;

import model.SnakeGame;

import javax.swing.*;

public class Snake {
    private SnakeGame game;
    private Renderer renderer;
    private InputController inputController;
    private Timer timer;

    public static void main(String[] args) {
        new Snake().run();
    }

    private void run() {
        game = new SnakeGame();
        renderer = new Renderer(game);
        inputController = new InputController(game);
        renderer.start(inputController);

        timer = new Timer(200, (x) -> update());
        timer.start();
    }

    private void update() {
        game.update();
        renderer.update();
    }
}
