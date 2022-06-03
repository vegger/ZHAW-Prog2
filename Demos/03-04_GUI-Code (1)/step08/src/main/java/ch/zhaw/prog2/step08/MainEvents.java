package ch.zhaw.prog2.step08;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * Zeigen des Ablaufes von Ereignissen. Unterschied zwischen Event-Capturing und Event-Bubbling
 * Zeigen, wie das Konsumieren eines Events verwendet werden kann
 * @author bles
 * @version 1.0
 */
public class MainEvents extends Application {
	private static final int HANDLER_ON_BUTTON_AND_SCENE = 1;
	private static final int CONSUME_HANDLER_ON_GRID_ADDITIONAL = 2;
	private static final int NON_CONSUME_HANDLER_ON_BUTTON = 3;

	private int showType = HANDLER_ON_BUTTON_AND_SCENE;

	private static final int HORIZONTAL_GAP = 5;
	private static final int VERTICAL_GAP = 3;
	private EventHandler<MouseEvent> click = new ClickHandler();
	private TextArea textHistory = new TextArea();
	private TextField textEntry = new TextField();
	
	private EventHandler<MouseEvent> clickAndConsume = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			textHistory.textProperty().set(textHistory.getText() + "ConsumeHandler: " + event.getTarget().toString() + "\n");
			event.consume();
		}
	};
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = createPane();
		Scene scene = new Scene(root, 500, 400);
		
		scene.setOnMouseClicked(click);
		
		//Set stage properties
		primaryStage.setTitle("Add text to the list");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private Parent createPane() {
		// Define controls and panes
		GridPane grid = new GridPane();
		Label labelEntry = new Label("Text:");
		Label labelHistory = new Label("Text history:");

		FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
		Button buttonCancel = new Button("Cancel");
		Button buttonAdd = new Button("Add");
		
		//set special properties
		textHistory.setMinHeight(300);
		textHistory.setDisable(true);
		labelEntry.setMinWidth(35);
		grid.setHgap(VERTICAL_GAP);
		grid.setVgap(HORIZONTAL_GAP);
		grid.setPadding(new Insets(VERTICAL_GAP, HORIZONTAL_GAP, 0, HORIZONTAL_GAP));
		
		//set event properties
		// Event capturing phase (EventFilter): scene -> grid -> buttonPane -> buttonAdd
		// Event bubbling phase (EventHandler): buttonAdd -> buttonPane -> grid -> scene		
		switch (showType) {
		case HANDLER_ON_BUTTON_AND_SCENE:	//only button and scene have an EventHandler (scene in start())
			buttonAdd.setOnMouseClicked(click);
			break;
		case CONSUME_HANDLER_ON_GRID_ADDITIONAL: //additional EventHandler on grit (one that consumes the Event, like button does)
			buttonAdd.setOnMouseClicked(click);
			grid.setOnMouseClicked(clickAndConsume);
			break;
		case NON_CONSUME_HANDLER_ON_BUTTON: //change skin of buttonAdd, so it doesn't consume the Event
			buttonAdd.setOnMouseClicked(click);
			//grid.setOnMouseClicked(clickAndConsume);
			buttonAdd.setSkin(new ButtonSkin(buttonAdd) {
			    {
			        consumeMouseEvents(false);
			    }
			});
		}
		
		// add controls and panes to panes
		buttonPane.getChildren().addAll(buttonCancel, buttonAdd);
		grid.add(labelEntry, 0, 0);
		grid.add(textEntry, 1, 0);
		grid.add(labelHistory, 0, 1);
		grid.add(textHistory, 0, 2, 2, 1);
		grid.add(buttonPane, 0, 3, 2, 1);
		return grid;
	}
	
	class ClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			//Node actNode = (Node) event.getSource().toString();
			textHistory.textProperty().set(textHistory.getText() + "ClickHandler: " + event.getSource().toString() + "\n");
			textHistory.textProperty().set(textHistory.getText() + textEntry.getText() + "\n");
			textEntry.clear();
			textEntry.requestFocus();
		}
	}

}
