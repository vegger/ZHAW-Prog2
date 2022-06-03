package ch.zhaw.prog2.concurrency1;

import ch.zhaw.prog2.concurrency1.unimportant.Ball;
import ch.zhaw.prog2.concurrency1.unimportant.BallMover;

import java.util.concurrent.atomic.AtomicBoolean;

public class BallMoverMultiThread extends Thread implements BallMover {
    private final Ball ball;

    // doContinue is accessed from different threads, so we use AtomicBoolean
    private final AtomicBoolean doContinue = new AtomicBoolean(true);

    public BallMoverMultiThread(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void animateBall() {
        start();
    }

    @Override
    public void run() {
        while (doContinue.get()) {
            ball.moveBall();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception");
            }
            Thread.yield();
        }
    }

    @Override
    public void terminate() {
        ball.removeBallFromCanvas();
        doContinue.set(false);
    }
}
