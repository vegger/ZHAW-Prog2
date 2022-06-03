package ch.zhaw.prog2.step06;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * Applikation mit GridPane realisiert, Center alignment
 * @author bles
 * @version 1.0
 */
public class Login extends Application {
	
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
		
		//Create scene with root node with size
		Scene scene = new Scene(grid, 260, 120);
		
		//Set stage properties
		primaryStage.setTitle("Please log in");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
