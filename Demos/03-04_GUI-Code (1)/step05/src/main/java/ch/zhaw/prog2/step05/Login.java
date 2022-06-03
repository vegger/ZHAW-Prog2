package ch.zhaw.prog2.step05;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login extends Application {
	
	private final double row1 = 20;
	private final double row2 = 50;
	private final double row3 = 80;
	private final double column1 = 20;
	private final double column2 = 100;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label labelName = new Label("Name");
		Label labelPwd = new Label("Password");
		TextField textName = new TextField();
		PasswordField textPwd = new PasswordField();
		Button btnAbbrechen = new Button("cancel");
		Button btnOk = new Button("Ok");
		
		//set control positions
		labelName.setLayoutX(column1);
		labelName.setLayoutY(row1);
		labelPwd.setLayoutX(column1);
		labelPwd.setLayoutY(row2);
		textName.setLayoutX(column2);
		textName.setLayoutY(row1);
		textPwd.setLayoutX(column2);
		textPwd.setLayoutY(row2);
		btnAbbrechen.setLayoutX(column1);
		btnAbbrechen.setLayoutY(row3);
		btnOk.setLayoutX(column2);
		btnOk.setLayoutY(row3);
		
		//Create root node
		Group root = new Group();
		//Add label to the scene graph
		root.getChildren().add(labelName);
		root.getChildren().add(labelPwd);
		root.getChildren().add(textName);
		root.getChildren().add(textPwd);
		root.getChildren().add(btnAbbrechen);
		root.getChildren().add(btnOk);
		//Create scene with root node with size
		Scene scene = new Scene(root, 260, 120);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("MyStyles.css").toExternalForm());
		
		//Set stage properties
		primaryStage.setTitle("Please log in");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
