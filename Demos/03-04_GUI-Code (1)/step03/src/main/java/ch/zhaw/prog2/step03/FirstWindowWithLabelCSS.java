package ch.zhaw.prog2.step03;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Verwenden der Konstanten für das Umstellen und Zeigen der unterschiedlichen
 * Möglichkeiten zum Einstellen der Darstellung eines Labels
 * @author bles
 * @version 1.0
 */
public class FirstWindowWithLabelCSS extends Application {
	private static final int STYLE_FROM_CODE = 1;
	private static final int STYLE_FROM_CODE_WITH_SETSTYLE = 2;
	private static final int STYLE_FROM_CSS = 3;
	
	private int showType = STYLE_FROM_CODE;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Create a new label
		Label label = new Label("A second Label");
		//Set size
		label.setPrefSize(200, 50);
		//Set position
		label.setLayoutX(50);
		label.setLayoutY(50);
		label.setTooltip(new Tooltip("This is a label!"));

		//Create root node
		Group root = new Group();
		//Add label to the scene graph
		root.getChildren().add(label);
		//Create scene with root node with size
		Scene scene = new Scene(root, 300, 180);
		
		// change between different stylings
		switch (showType) {
		case STYLE_FROM_CODE:
			//Set style (font and background)
			label.setFont(new Font("Arial", 24));
			label.setBackground(new Background(new BackgroundFill(Color.rgb( 220, 120, 180), null, null)));
			label.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
			break;
		case STYLE_FROM_CODE_WITH_SETSTYLE:
			// use inline style
			label.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
			break;
		case STYLE_FROM_CSS:
			//use stylesheet
			scene.getStylesheets().add(getClass().getClassLoader().getResource("MyLabel.css").toExternalForm());
		}
		
		//Set stage properties
		primaryStage.setTitle("Second titel");
		primaryStage.setAlwaysOnTop(true);
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
