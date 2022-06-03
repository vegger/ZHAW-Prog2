package ch.zhaw.prog2.concurrency1.unimportant;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The ball can move and draw itself respecting the boundaries of the given canvas.
 */
public class Ball {
    private static final int XSTEP = 4;
    private static final int YSTEP = 3;
    private static final int DIAMETER = 20;

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private int xDirection = 1;
    private int yDirection = 1;
    private int x = 0;
    private int y = 0;

    public Ball(Canvas canvas) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void moveBall() {
        removeBallFromCanvas();
        int move = xDirection * XSTEP;
        if (x + move + DIAMETER > canvas.getWidth() || x + move < 0) {
            xDirection = -xDirection;
        }
        move = yDirection * YSTEP;
        if (y + move + DIAMETER > canvas.getHeight() || y + move < 0) {
            yDirection = -yDirection;
        }
        x = x + xDirection * XSTEP;
        y = y + yDirection * YSTEP;
        addBallToCanvas();
    }

    private void addBallToCanvas() {
        System.out.println("Moved to " + x + ":" + y); // Needed to see progress in the BallMoverSimple
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(x, y, DIAMETER, DIAMETER);
    }

    public void removeBallFromCanvas() {
        graphicsContext.clearRect(x, y, DIAMETER, DIAMETER);
    }

}

