package ch.zhaw.prog2.fxmlcalculator;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class MainWindowController implements ConnectController {
    private static final String INFO = """
        Enter valid values to
        - Initial amount (> 0)
        - Return in % (can be +/- or 0)
        - Annual Costs (> 0)
        - Number of years (> 0)
        Calculate displays the annual balance development!";
        """;
    private ValueHandler valueHandler;
    private Parent parentSceneGraph;

	@FXML private CheckMenuItem checkInitialAmount;
	@FXML private CheckMenuItem checkReturn;
	@FXML private CheckMenuItem checkCosts;
	@FXML private CheckMenuItem checkYears;
	@FXML private TextField initialAmount;
	@FXML private TextField returnInPercent;
	@FXML private TextField annualCost;
	@FXML private TextField numberOfYears;
	@FXML private TextArea results;

	@Override
	/**
	 * Is called from Main to connect the Controller to the valueHandler
	 * The results-TextArea reacts on changes of the resultBound Property from valueHandler
	 * --> if someone else changes the resultBound, the result changes
	 */
	public void setValueHandler(ValueHandler valueHandler) {
		this.valueHandler = valueHandler;
        // bind the result string of the valueHandler to the result TextField-property
		results.textProperty().bind(this.valueHandler.resultBoundProperty());
	}
	@Override
	public void setParentSceneGraph(Parent parentSceneGraph) {
		this.parentSceneGraph = parentSceneGraph;
	}

	@FXML
	private void clearValues() {
		if (checkInitialAmount.isSelected()) {
			initialAmount.clear();
		}
		if (checkCosts.isSelected()) {
			annualCost.clear();
		}
		if (checkYears.isSelected()) {
			numberOfYears.clear();
		}
		if (checkReturn.isSelected()) {
			returnInPercent.clear();
		}
	}

	@FXML
	private void clearResult() {
		valueHandler.clearResult();
	}

	@FXML
	private void showHelp() {
		valueHandler.setResultBound(INFO);
		setBorderColor(Color.BLUE);
	}

	@FXML
	private void calculateResultBound() {
		valueHandler.checkValuesAndCalculateResult(
            initialAmount.getText(),
            returnInPercent.getText(),
            annualCost.getText(),
            numberOfYears.getText());
        setBorderColor(valueHandler.areValuesOk() ? Color.GREEN : Color.RED);
	}

	private void setBorderColor(Color color) {
        results.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

	@FXML
	private void closeWindow() {
		Stage stage = (Stage) results.getScene().getWindow();
		stage.close();
	}

	@FXML
	/**
	 * Open a new window to show the result a second time
	 */
	private void openResultWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultWindow.fxml"));
			Pane rootPane = loader.load();
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);
			// new stage for a new window
            Stage inputWindow = new Stage();
			// the controller has to know about the valueHandler and the stage
			ConnectController controller = loader.getController();
			controller.setValueHandler(valueHandler);
			controller.setParentSceneGraph(parentSceneGraph);

            // configure and show stage
            inputWindow.setScene(scene);
            inputWindow.setMinWidth(300);
            inputWindow.setMinHeight(385);
            inputWindow.setTitle("Result Window");
            inputWindow.show();

		} catch (IOException e) {
		    System.err.println("Failed to load FXML resource: " + e.getMessage());
        }
	}

	@FXML
	private void openResultInSameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultWindow.fxml"));
			Pane rootPane = loader.load();
			// fill in scene and stage setup
			parentSceneGraph.getScene().setRoot(rootPane);
			// the controller has to know about the valueHandler and the stage
			ConnectController controller = loader.getController();
			controller.setValueHandler(valueHandler);
			controller.setParentSceneGraph(parentSceneGraph);
		} catch(IOException e) {
            System.err.println("Failed to load FXML resource: " + e.getMessage());
		}

	}

}
