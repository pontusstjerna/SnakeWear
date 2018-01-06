package desktop;

import model.SnakeGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputController implements MouseListener {
    private SnakeGame game;
    private Renderer renderer;


    public InputController(SnakeGame game, Renderer renderer) {
        this.game = game;
        this.renderer = renderer;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        game.changeDirection(renderer.getGameX(e.getX()), renderer.getGameY(e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
