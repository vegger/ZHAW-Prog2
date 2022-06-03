package ch.zhaw.prog2.step02;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Ganz einfache Applikation. Zeigt den grundlegenden Aufbau mit den Elementen
 * Stage->Scene->RootNode->Control
 * @author bles
 * @version 1.0
 */
public class FirstWindowWithLabel extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Create a new label
		Label label = new Label("My first label");

		//Create root node
		Group root = new Group();
		//Add label to the scene graph
		root.getChildren().add(label);
		//Create scene with root node with size
		Scene scene = new Scene(root, 250, 150);
		
		//Set stage properties
		primaryStage.setTitle("First titel");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
