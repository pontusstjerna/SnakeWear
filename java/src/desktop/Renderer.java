package desktop;

import model.SnakeGame;
import model.SnekPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class Renderer {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private SnakeGame game;

    private double scale;
    private Color head;
    private Color body;
    private Color background;
    private Color food;
    private Font main;

    private JFrame frame;
    private JPanel surface;

    private int paddingX;
    private int paddingY;

    public Renderer(SnakeGame game) {
        this.game = game;

        scale = ((Math.min(WIDTH, HEIGHT) / (double) SnakeGame.SIZE)) * Math.cos(Math.PI / 4); //For round watch faces

        paddingX = (int) ((WIDTH - scale * SnakeGame.SIZE) / 2);
        paddingY = (int) ((HEIGHT - scale * SnakeGame.SIZE) / 2);

        head = new Color(161, 71, 0);
        body = new Color(100, 100, 100);
        background = Color.white;
        food = Color.cyan;

        main = new Font("ArialBlack", Font.BOLD, 20);
    }

    public void render(Graphics2D g) {
        if (!game.isGameOver()) {
            drawBackground(new Color(16, 16, 16), g);
            g.setColor(head);
            g.fillRect(
                    (int) (game.getSnake().getHead().getX() * scale) + paddingX,
                    (int) (game.getSnake().getHead().getY() * scale) + paddingY,
                    (int) (scale), (int) scale
            );

            for (SnekPiece piece : game.getSnake().getBody()) {
                drawSnekPiece(piece, g);
            }

            g.setColor(food);
            g.fillRect(
                    (int) (game.getFoodX() * scale) + paddingX,
                    (int) (game.getFoodY() * scale) + paddingY,
                    (int) (scale), (int) scale
            );
        } else {
            showGameOver(g);
        }

        drawScore(g);
    }

    public int getGameX(int x) {
        return (int) ((x - paddingX) / scale);
    }

    public int getGameY(int y) {
        return (int) ((y - paddingY) / scale);
    }

    public void start(MouseListener listener) {
        frame = new JFrame("Snake for Wear (but now on desktop)");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        surface = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                render((Graphics2D) graphics);
            }
        };
        surface.setFocusable(true);
        surface.setBackground(Color.black);

        surface.addMouseListener(listener);
        frame.add(surface);

        frame.setVisible(true);
    }

    public void update() {
        surface.repaint();
    }

    private void drawSnekPiece(SnekPiece piece, Graphics2D g) {
        g.setColor(body);
        g.fillRect(
                (int) (piece.getX() * scale) + paddingX,
                (int) (piece.getY() * scale) + paddingY,
                (int) (scale), (int) scale
        );
    }

    private void drawScore(Graphics2D g) {
        g.setFont(main);
        String highest = "Highest: 000";
        g.drawString(highest,
                WIDTH / 2 - g.getFontMetrics().stringWidth(highest) / 2,
                (int) (scale - (Math.cos(Math.PI / 2) * scale / 2))
        );

        String current = "Current: " + getScoreString();
        g.drawString(current,
                WIDTH / 2 - g.getFontMetrics().stringWidth(current) / 2,
                (int) (paddingY + scale * SnakeGame.SIZE + (scale - (Math.cos(Math.PI / 2) * scale / 2)))
        );
    }

    private String getScoreString() {
        return String.valueOf(1000 + game.getScore()).substring(1);
    }

    private void drawBackground(Color color, Graphics2D g) {
        g.setColor(color);
        g.fillRect(
                (int) (0 * scale) + paddingX,
                (int) (0 * scale) + paddingY,
                (int) (SnakeGame.SIZE * scale),
                (int) (SnakeGame.SIZE * scale)
        );
    }

    private void showGameOver(Graphics2D g) {
        drawBackground(head, g);
        g.setFont(main);
        g.drawString(
                "Game over!",
                (int) (paddingX + (SnakeGame.SIZE * scale / 2) - scale * 5),
                (int) (paddingY + (SnakeGame.SIZE * scale / 2) - 2 * scale)
        );
    }
}