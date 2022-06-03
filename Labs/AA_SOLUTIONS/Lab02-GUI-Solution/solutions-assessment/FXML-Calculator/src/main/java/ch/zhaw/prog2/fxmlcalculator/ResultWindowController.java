package ch.zhaw.prog2.fxmlcalculator;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller for the MainWindow. One controller per mask (or FXML file)
 * Contains everything the controller has to reach in the view (controls)
 * and all methods the view calls based on events.
 * @author bles
 * @version 1.0
 */
public class ResultWindowController implements ConnectController {
    private ValueHandler valueHandler;
    private Parent parentSceneGraph;

	@FXML private TextArea results;

	@Override
	/**
	 * Is called from Main to connect the Controller to the valueHandler
	 * The results-TextArea reacts on changes of the resultBound Property from valueHandler
	 * --> if someone else changes the resultBound, the result changes
	 */
	public void setValueHandler(ValueHandler valueHandler) {
		this.valueHandler = valueHandler;
		// either bind the result string of the valueHandler to the results TextField-property
		// results.textProperty().bind(valueHandler.resultBoundProperty());
		// or register a ChangeListener and also set the border color based on the state from of the value valueHandler
        this.valueHandler.resultBoundProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateResult();
            }

        });
        // set the result text field with current value and status from the valueHandler
        updateResult();
	}

	private void updateResult() {
	    String resultText = valueHandler.getResultBound();
        results.setText(resultText);
        Color stateColor = Color.TRANSPARENT;
        if (!resultText.isBlank()) {
            stateColor = valueHandler.areValuesOk() ? Color.GREEN : Color.RED;
        }
        results.setBorder(new Border(new BorderStroke(stateColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
	}

	@Override
	/**
	 * The controller has to know about the parentStage to recognize
	 * if the scene has changed, or it is a new window
	 */
	public void setParentSceneGraph(Parent parentSceneGraph) {
		this.parentSceneGraph = parentSceneGraph;
	}

	@FXML
	private void closeWindow() {
		// This is only to show both variants (external window and in the same window)
		Stage stage = (Stage) results.getScene().getWindow();
		if(stage.getTitle().equals("Return on Investment Calculator")) {
			// in the same window, change the scene-graph
			stage.getScene().setRoot(parentSceneGraph);
		} else {
			// in external window, close window
			stage.close();
		}
	}

}
