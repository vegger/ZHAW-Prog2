package ch.zhaw.prog2.step01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Demo-Applikation zum zeigen am Anfang. Wird im Zusammenhang mit dem Scene-Graph verwendet
 * Keine Hinweise auf die Programmierung, nur Show
 * Kein Anspruch auf CleanCode
 * @author bles
 * @version 1.0
 */
public class SenseOfLife extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label anzeige = new Label("Click to see the sense of life...");
		anzeige.setFont(new Font("Arial", 18));
		Button button = new Button();
		button.setMinWidth(200);
		button.setMinHeight(100);
		button.setFont(new Font("Arial", 30));
		button.setDisable(false);
		button.setText("Here it is");
		button.setOnMouseEntered(e -> button.setDisable(true));
		Image image = new Image(getClass().getResourceAsStream("SenseOfLife.png"));
		ImageView imgView = new ImageView(image);
		imgView.setFitHeight(200);
		imgView.setFitWidth(180);
		
		GridPane root = new GridPane();
		root.setVgap(20);
		root.setHgap(20);
		root.setAlignment(Pos.TOP_LEFT);
		root.setBackground(new Background(new BackgroundFill(Color.rgb( 200, 180, 180), CornerRadii.EMPTY, Insets.EMPTY)));

		root.add(imgView, 1, 1, 3, 6);
		root.add(anzeige, 5, 2);
		root.add(button, 5, 4);
		root.setOnMouseMoved(e -> { if (e.getSceneX() < button.getLayoutX() 					
									|| e.getSceneX() > (button.getLayoutX() + button.getWidth())
									|| e.getSceneY() < button.getLayoutY()
									|| e.getSceneY() > (button.getLayoutY() + button.getWidth())) button.setDisable(false);
		});
		
		Scene scene = new Scene(root, 500, 250);
		primaryStage.setTitle("The meaning of life");
		primaryStage.setScene(scene);
		primaryStage.setX(700);
		primaryStage.setY(500);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
