package ch.zhaw.prog2.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindow extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		openMainWindow(primaryStage);
	}
	
	private void openMainWindow(Stage stage) {
		try {
            // Create FXMLLoader for `MainWindow.fxml` from `resources` folder
            // using the classloader `getClass().getClassLoader()`, it is reading from the top-level resources folder
            // FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/MainWindow.fxml"));
            // FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));

            // getClass().getResource() directly reads from the same package level as the current class (e.g. application)
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));


            // here the SceneGraph would be built starting from the root-node
            // -> load root-node pane from FXML-SceneGraph
            Pane rootNode = loader.load();

            // access and configure the controller created by the FXMLLoader
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.connectProperties();

            // setup scene
			Scene scene = new Scene(rootNode);

            // load `style.css` from the top-level `resources` folder and add it to the scene
			// scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
            // alternative, you can also use the absolut path name `/style.css` to declare the path from the resources root.
            //scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            // prepare the stage
//            stage.setHeight(400);
//            stage.setWidth(500);
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
