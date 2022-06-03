package ch.zhaw.prog2.step14a;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main  extends Application {
	// Konstanden für alle Screens und deren FXML
	public static final String MAINVIEW = "Main";
	public static final String MAINVIEW_FILE = "MainWindow.fxml";
	public static final String LOGIN = "Login";
	public static final String LOGIN_FILE = "LoginWindow.fxml";

	private HashMap<String, Parent> screens = new HashMap<>();

	@Override
	public void start(Stage primaryStage) {
		loadAllScreens();
		mainWindow(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void mainWindow(Stage stage) {
		try {
			Scene scene = new Scene(screens.get(MAINVIEW));

            // configure and show stage
			stage.setScene(scene);
			stage.setMinWidth(200);
			stage.setMinHeight(185);
			stage.setTitle("Open Window");
			stage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadAllScreens() {
		loadScreen(MAINVIEW, MAINVIEW_FILE);
		loadScreen(LOGIN, LOGIN_FILE);
	}
	
	private void loadScreen(String name, String fileName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
			Parent loadScreen = (Parent) loader.load();
			ControlledScreens controlledScreen = (ControlledScreens) loader.getController();
			controlledScreen.setScreenList(screens);
			screens.put(name, loadScreen);
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
			e.getMessage();
		}		
	}

}
