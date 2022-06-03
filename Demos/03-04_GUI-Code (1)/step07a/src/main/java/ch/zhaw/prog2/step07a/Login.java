package ch.zhaw.prog2.step07a;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 * Zeigen der Verwendung von vorgefertigten Dialogen für die Prüfung
 * der Benutzereingaben
 * Passwort bestätigen mit TextInputDialog --> kann kein Textfeld mit Passwort-
 * verhalten zeigen
 * @author bles
 * @version 1.0
 */
public class Login extends Application {
	
	private PasswordField textPwd;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label labelName = new Label("Name");
		Label labelPwd = new Label("Password");
		TextField textName = new TextField();
		textPwd = new PasswordField();
		Button btnAbbrechen = new Button("cancel");
		Button btnOk = new Button("Ok");
		
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setAlignment(Pos.CENTER);
		
		grid.add(labelName, 0, 0);
		grid.add(labelPwd, 0, 1);
		grid.add(textName, 1, 0);
		grid.add(textPwd, 1, 1);
		grid.add(btnAbbrechen, 0, 2);
		grid.add(btnOk, 1, 2);
		
		//add Eventshandlers
		addButtonEventCancel(btnAbbrechen);
		addButtonEventOk(btnOk);
		
		//Create scene with root node with size
		Scene scene = new Scene(grid, 260, 120);
		
		//Set stage properties
		primaryStage.setTitle("Please log in");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void addButtonEventCancel(Button button) {
		button.setOnMouseClicked(e -> System.exit(0));
	}
	private void addButtonEventOk(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(textPwd.getText().length()<8) {
					showAlertToShort();					
				} else {
					if(confirmPwd()) {
						System.exit(0);
					};
				}
				
			}
		});
	}
	private void showAlertToShort() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("To short!");
		alert.setHeaderText("The password has to be at least 8 characters long.");
		alert.setContentText("Please try again or use <cancel> to close the window.");
		alert.showAndWait();		
	}
	private boolean confirmPwd() {
		TextInputDialog textInput = new TextInputDialog();
		textInput.setTitle("Confirm password");
		textInput.setHeaderText("Please enter password again:");
		// Change Button-Text
		Button cancelButton = (Button) textInput.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setText("Cancel");
		
		Optional<String> result = textInput.showAndWait();
		String entered = "";
		boolean isOk = false;
		if (result.isPresent()) {
			entered = result.get();
			if(!entered.equals(textPwd.getText())) {
				showAlertWrongPwd();
			} else {
				isOk = true;
			}
		}
		return isOk;
	}
	private void showAlertWrongPwd() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wrong password!");
		alert.setHeaderText("Not the same password!");
		alert.showAndWait();		
	}
}
