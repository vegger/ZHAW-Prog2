package ch.zhaw.prog2.fxmlcalculator;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main-Application. Opens the first window (MainWindow) and the common ValueHandler
 * @author
 * @version 1.0
 */
public class Main extends Application {
    private ValueHandler valueHandler;
    private StringProperty resultContent;

	public static void main(String[] args) {
		launch(args);
	} // TODO Was macht launch?

	@Override
	public void start(Stage primaryStage) {
		valueHandler = new ValueHandler();
		mainWindow(primaryStage);
	}

	private void mainWindow(Stage primaryStage) {
		//load main window
        Pane rootNode;
        try {
            rootNode = initMainWindow();
        } catch (Exception e) {
            return;
        }
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab02");
        primaryStage.show();
	}

    private Pane initMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Pane rootNode = loader.load();

        MainWindowController controller = loader.getController();
        controller.init2();
        controller.setValueHandler(valueHandler);

        return rootNode;
    }

    /*private Pane initResultWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultWindow.fxml"));
        Pane rootNode = loader.load();

        ResultWindowController controller = loader.getController();
        controller.setValueHandler(valueHandler);
        controller.init();

        return rootNode;
    }*/
}

