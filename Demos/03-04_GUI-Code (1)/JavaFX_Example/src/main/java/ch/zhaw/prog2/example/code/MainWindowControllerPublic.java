package ch.zhaw.prog2.example.code;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowControllerPublic  {

    public MainWindowControllerPublic(Label viewText, TextField inputText) {
        this.viewText = viewText;
        this.inputText = inputText;
    }

    // controls in views
    private Label viewText;        // reference to viewText Label
    private TextField inputText;   // reference to intputText TextField

    // event handling methods - public to be called from the event handler

    public void handleShow() {     // called by showText Button
        viewText.setText(inputText.getText());
    }

    public void handleClear() {    // called by clearText Button
        inputText.clear();
    }

}


