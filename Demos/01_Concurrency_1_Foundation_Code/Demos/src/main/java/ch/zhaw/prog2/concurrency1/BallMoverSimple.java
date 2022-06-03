package ch.zhaw.prog2.concurrency1;

import ch.zhaw.prog2.concurrency1.unimportant.Ball;
import ch.zhaw.prog2.concurrency1.unimportant.BallMover;

/**
 * Simple implementation to animate the ball.
 */
public class BallMoverSimple implements BallMover {
    private final Ball ball;
    private boolean doContinue = true;

    public BallMoverSimple(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void animateBall() {
        while (doContinue) {
            ball.moveBall();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception");
            }
        }
    }

    @Override
    public void terminate() {
        ball.removeBallFromCanvas();
        doContinue = false;
    }
}
