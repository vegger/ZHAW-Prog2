package ch.zhaw.prog2.example.fxml;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowController {
    // controls in views
    @FXML private Label viewText;      // reference to viewText Label
    @FXML private TextField inputText; // reference to intputText TextField

    // event handler methods
    @FXML
    private void handleShow() {        // called by showText Button
        viewText.setText(inputText.getText());
    }
    @FXML
    private void handleClear() {       // called by clearText Button
        inputText.clear();
    }
}


