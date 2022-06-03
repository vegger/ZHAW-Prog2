package ch.zhaw.prog2.fxmlcalculator;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller for the MainWindow. One controller per mask (or FXML file)
 * Contains everything the controller has to reach in the view (controls)
 * and all methods the view calls based on events.
 * @author
 * @version 1.0
 */
public class ResultWindowController {
    // add datafields
    ValueHandler valueHandler;
    ResultController resultController;

	@FXML
    private TextArea result;

	@FXML
	private void closeWindow() {
		Stage stage = (Stage) result.getScene().getWindow();
		stage.close();
	}


    public void init2() {
        resultController = new ResultController(result);
    }

    public void setValueHandler(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
        bindProperties();
        if(valueHandler.areValuesOk()) {
            resultController.showResult(valueHandler.getResultBound(), true, Color.GREEN);
        } else {
            resultController.showResult(valueHandler.getResultBound(), false, Color.RED);
        }
    }

    //TODO code-smell - Code duplication
    //TODO Musterlösung anschauen
    private void bindProperties() {
        valueHandler.resultBoundProperty().addListener((observable, oldValue, newValue) -> {
            if(valueHandler.areValuesOk()) {
                resultController.showResult(newValue, true, Color.GREEN);
            } else {
                resultController.showResult(newValue, false, Color.RED);
            }
        });
    }
}
