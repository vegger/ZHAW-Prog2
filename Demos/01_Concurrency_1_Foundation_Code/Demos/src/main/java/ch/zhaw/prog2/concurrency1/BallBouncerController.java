package ch.zhaw.prog2.concurrency1;

import ch.zhaw.prog2.concurrency1.unimportant.Ball;
import ch.zhaw.prog2.concurrency1.unimportant.BallMover;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * JavaFX controller to choose the desired implementation
 * ({@link ch.zhaw.prog2.concurrency1.BallMoverSimple} or
 * {@link ch.zhaw.prog2.concurrency1.BallMoverMultiThread})
 * and to start and stop the animation.
 */
public class BallBouncerController {
    private BallMover ballMover;

    @FXML
    private Text title;

    @FXML
    private ToggleGroup threadType;

    @FXML
    private Canvas canvasForBall;

    @FXML
    private void startAction() {
        if (ballMover == null) {
            Toggle selectedToggle = threadType.getSelectedToggle();
            String text = ((RadioButton) selectedToggle).getText();
            if ("Simple".equals(text)) {
                ballMover = new BallMoverSimple(new Ball(canvasForBall));
                title.setText("Simple Bouncer");
            } else {
                ballMover = new BallMoverMultiThread(new Ball(canvasForBall));
                title.setText("MultiThread Bouncer");
            }
            ballMover.animateBall();
        }
    }

    @FXML
    private void stopAction() {
        title.setText("Bouncer");
        if (ballMover != null) {
            ballMover.terminate();
            ballMover = null;
        }
    }
}
