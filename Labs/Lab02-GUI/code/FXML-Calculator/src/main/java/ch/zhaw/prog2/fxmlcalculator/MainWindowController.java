package ch.zhaw.prog2.fxmlcalculator;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
public class MainWindowController {
	// please complete
    ValueHandler valueHandler;
    ResultController resultController;

    private static final String INFO = """
        Enter valid values to
        - Initial amount (> 0)
        - Return in % (can be +/- or 0)
        - Annual Costs (> 0)
        - Number of years (> 0)
        Calculate displays the annual balance development!";
        """;

    @FXML
    private MenuItem helpShowText;

    @FXML
    private CheckMenuItem clearInitialAmount;

    @FXML
    private CheckMenuItem clearReturnInPercent;

    @FXML
    private CheckMenuItem clearAnnualCost;

    @FXML
    private CheckMenuItem clearNumberOfYears;

    @FXML
    private TextField annualCost;

    @FXML
    private TextField initialAmount;

    @FXML
    private TextField numberOfYears;

    @FXML
    private TextArea result;

    @FXML
    private TextField returnRate;

    @FXML
    void handleKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.F1)){
            showHelpText();
        }
    }

    @FXML
    void handleShowHelpText(ActionEvent event) {
        showHelpText();
    }

    @FXML
    void handleClearResults(ActionEvent event) {
        result.setText("");
    }

    @FXML
    void handleClearValues(ActionEvent event) {
        if (clearInitialAmount.isSelected()) {
            initialAmount.clear();
        }
        if (clearAnnualCost.isSelected()) {
            annualCost.clear();
        }
        if (clearNumberOfYears.isSelected()) {
            numberOfYears.clear();
        }
        if (clearReturnInPercent.isSelected()) {
            returnRate.clear();
        }
    }

    @FXML
    void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleCalculate(ActionEvent event) {
        valueHandler.checkValuesAndCalculateResult(initialAmount.getText(), returnRate.getText(), annualCost.getText(), numberOfYears.getText());
    }

    @FXML
    void handleOpenResultWindow(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultWindow.fxml"));
        Pane rootNode;

        try {
            rootNode = loader.load();
        } catch (Exception e) {return;}

        ResultWindowController controller = loader.getController();
        controller.init2();
        controller.setValueHandler(valueHandler);
        Scene resultScene = new Scene(rootNode);
        Stage resultStage = new Stage();
        resultStage.setScene(resultScene);
        resultStage.show();
    }


    public void init2() {
        resultController = new ResultController(result);
    }

    public void setValueHandler(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
        bindProperties();
    }

    private void bindProperties() {
        valueHandler.resultBoundProperty().addListener((observable, oldValue, newValue) -> {
            if(valueHandler.areValuesOk()) {
                resultController.showResult(newValue, true, Color.GREEN);
            } else {
                resultController.showResult(newValue, false, Color.RED);
            }
        });
    }

    private void showHelpText() {
        resultController.showResult(INFO, true, Color.BLUE);
    }


}
