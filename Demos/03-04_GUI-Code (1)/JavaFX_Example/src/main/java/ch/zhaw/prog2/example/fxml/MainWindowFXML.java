package ch.zhaw.prog2.example.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowFXML extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        // Initialize the main window scene graph and controller
        Pane rootNode = initMainWindow();

        // setup scene and stage
        Scene scene = new Scene(rootNode);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
	}

	private Pane initMainWindow() throws IOException {
        // getClass().getResource() directly reads from the same package level as the current class (e.g. ch.zhaw.prog2.example.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));

        // read the file, build the Scene Graph and initialize the controller
        Pane rootNode = loader.load();

        // access and further configure the controller created by the FXMLLoader
        MainWindowController controller = loader.getController();
        System.out.printf("Controller is of class %s%n", controller.getClass().getCanonicalName());

        return rootNode;
	}

}
