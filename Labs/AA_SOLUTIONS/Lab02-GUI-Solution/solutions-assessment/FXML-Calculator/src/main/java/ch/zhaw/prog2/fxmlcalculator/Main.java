package ch.zhaw.prog2.fxmlcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Main-Application. Opens the first window (MainWindow) and the common ValueHandler
 * @author bles
 * @version 1.0
 */
public class Main extends Application {

    private ValueHandler valueHandler;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		valueHandler = new ValueHandler();
		mainWindow(primaryStage);
	}

	private void mainWindow(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Pane rootPane = loader.load();
			MainWindowController controller = loader.getController();
			controller.setValueHandler(valueHandler);
			controller.setParentSceneGraph(rootPane);
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);

            // configure and show stage
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(385);
            primaryStage.setTitle("Return on Investment Calculator");
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

