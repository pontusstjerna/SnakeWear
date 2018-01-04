package desktop;

import model.SnakeGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputController implements MouseListener {
    private SnakeGame game;


    public InputController(SnakeGame game) {
        this.game = game;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        game.changeDirection(e.getX(), e.getY());
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
