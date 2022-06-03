package ch.zhaw.prog2.application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainWindowController {

    @FXML
    private TextArea textHistory;

    @FXML
    private TextField textEingabe;

    @FXML
    private Label labelTitel;

	public void connectProperties() {
		//binding properties
//		viewText.textProperty().addListener(e -> viewText.setText(viewText.getText()));
//		textEingabe.textProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldV, String newV) {
//				labelTitel.setText(textEingabe.getText());
//			}
//		});
		labelTitel.textProperty().bind(textEingabe.textProperty());		
	}

    @FXML
    private void hinzufuegenText(ActionEvent event) {
    	textHistory.setText(textHistory.getText() + textEingabe.getText() + System.lineSeparator());
    }

    @FXML
    private void leerenTextEingabe(ActionEvent event) {
    	textEingabe.clear();
    }

}

