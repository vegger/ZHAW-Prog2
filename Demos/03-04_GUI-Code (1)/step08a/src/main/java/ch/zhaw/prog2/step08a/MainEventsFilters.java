package ch.zhaw.prog2.step08a;

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
 * Zeigen von Handlern (anonyme und benannte Klassen)
 * Umschalten von SET_BUTTON_SKIN_TO_NOT_CONSUME auf true:
 * - nun wird das Ereignis nicht mehr vom Button konsumiert und wird an den nächsten Handler weitergereicht.
 * @author bles
 *
 */
public class MainEventsFilters extends Application {

	private static final boolean SET_BUTTON_SKIN_TO_NOT_CONSUME = false;

	private static final int HORIZONTAL_GAP = 5;
	private static final int VERTICAL_GAP = 3;
	private EventHandler<MouseEvent> click = new ClickHandler();
	private TextArea textHistory = new TextArea();
	private TextField textEntry = new TextField();

	/**
	 * Ein Handler, der den Event nicht weiter gibt, sondern in jedem Fall konsumiert
	 * Umgesetzt als anonyme innere Klasse. Kann auf alle Elemente der umgebenden Klasse zugreifen.
	 */
	private EventHandler<MouseEvent> clickAndConsume = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			textHistory.textProperty().set(textHistory.getText() + "ConsumeHandler: " + event.getTarget().toString() + "\n");
			event.consume();
		}
	};
	/**
	 * Innere Klasse (benannt). Kann auf alle Elemente der umgebenden Klasse zugreifen.
	 * @author bles
	 *
	 */
	class ClickHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			textHistory.textProperty().set(textHistory.getText() + "ClickHandler: " + event.getSource().toString() + "\n");
			textHistory.textProperty().set(textHistory.getText() + textEntry.getText() + "\n");
			textEntry.clear();
			textEntry.requestFocus();
		}
	}

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

		grid.setOnMouseClicked(clickAndConsume);
		buttonAdd.setOnMouseClicked(click);

		if (SET_BUTTON_SKIN_TO_NOT_CONSUME) {
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

}
