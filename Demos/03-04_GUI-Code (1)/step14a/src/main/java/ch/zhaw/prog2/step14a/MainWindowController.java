package ch.zhaw.prog2.step14a;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController implements ControlledScreens {

	private HashMap<String, Parent> screens = new HashMap<>();

	@FXML
	private RadioButton radioNew;
	@FXML
	private RadioButton radioSame;
	@FXML
	private CheckBox checkModal;
	@FXML
	private AnchorPane root;

	@FXML
	private void anmelden() {
        System.out.println("Show Login Window...");
		if(radioNew.isSelected()) {
			openNewWindow();
		} else {
			setNewScene();
		}
        System.out.println("Main Window Controller continues...");
	}

	private void openNewWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
			Pane rootPane = loader.load();
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);
			// new stage for a new window
            Stage inputWindow = new Stage();
            // configure and show stage
            if(checkModal.isSelected()) {
            	inputWindow.initOwner(root.getScene().getWindow());
            	inputWindow.initModality(Modality.WINDOW_MODAL);  // to disable events for owner window hierarchy only
                //inputWindow.initModality(Modality.APPLICATION_MODAL);  // to disable events for all other windows of applications
            }
            inputWindow.setScene(scene);
            inputWindow.setTitle("Login Window");
            inputWindow.showAndWait();  // blocks until window is closed, starts a nested event loop for window
            //inputWindow.show();  // does not block (no nested event loop), but does not accept events for main window

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void setNewScene() {
		root.getScene().setRoot(screens.get(Main.LOGIN));
	}

	@Override
	public void setScreenList(HashMap<String, Parent> screens) {
		this.screens = screens;
	}
}
