package se.nocroft.wearsnakecommon.model;

import java.util.ArrayList;
import java.util.List;

public class Snek {

    private List<SnekPiece> pieces;
    private SnekPiece head;

    public Snek(int startX, int startY, int startLength, Direction.Directions startDir) {
        pieces = new ArrayList<>();

        head = new SnekPiece(startX, startY, new Direction(startDir));
        head.setNextDirection(new Direction(startDir));
        pieces.add(
                new SnekPiece(
                        head.getX() - head.getDirection().getX(),
                        head.getY() - head.getDirection().getY(),
                        head.getDirection()));
        for (int i = 2; i < startLength; i++) {
            eat();
        }
    }

    public void update() {
        pieces.get(0).setNextDirection(head.getDirection().clone());
        for (int i = 1; i < pieces.size(); i++) {
            pieces.get(i).setNextDirection(pieces.get(i - 1).getDirection().clone());
        }

        head.update();

        for (SnekPiece piece : pieces) {
            piece.update();
        }
    }

    public List<SnekPiece> getBody() {
        return pieces;
    }

    public SnekPiece getHead() {
        return head;
    }

    void eat() {
        SnekPiece last = pieces.get(pieces.size() - 1);
        pieces.add(new SnekPiece(
                last.getX() - last.getDirection().getX(),
                last.getY() - last.getDirection().getY(),
                last.getDirection()));
    }
}