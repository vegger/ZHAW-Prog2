package ch.zhaw.prog2.step14a;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Controller for Login-Window
 * @author bles
 *
 */
public class LoginWindowController implements ControlledScreens {
	
	private HashMap<String, Parent> screens = new HashMap<>();
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private void cancel() {
		Stage stage = (Stage) root.getScene().getWindow();
		if(stage.getTitle().equalsIgnoreCase("Open window")) {
			root.getScene().setRoot(screens.get(Main.MAINVIEW));
		} else {
			stage.close();
		}
	}
	
	@FXML
	private void login() {
		// ... log me in
	}

	@Override
	public void setScreenList(HashMap<String, Parent> screens) {
		this.screens = screens;
	}

}
