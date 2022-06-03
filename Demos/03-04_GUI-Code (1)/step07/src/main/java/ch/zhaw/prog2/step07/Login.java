package ch.zhaw.prog2.step07;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Applikation mit mehreren FlowPanes in einer VBox
 * Zeigen, dass die Organisation der Panes entscheidet (SHOW_PART umstellen)
 *  - mit 0: Linker Rand zu klein
 *  - mit 1: Abstand links einstellen in jedem einzelnen FlowPane
 *  - mit 2: Abstand links einstellen mit zusätzlichem StackPane
 * @author bles
 * @version 1.0
 */
public class Login extends Application {
	private static final int NO_GAP_ON_LEFT_SIDE = 0;
	private static final int GAP_ON_EVERY_CONTROL = 1;
	private static final int GAP_ON_PANE = 2;

	private int showType = NO_GAP_ON_LEFT_SIDE;

	private static final int VERTICAL_GAP = 5;
	private static final int ROW_HEIGHT = 30;
	private static final int LABEL_LENGTH = 80;
	private static final int SHOW_PART = 0;

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
		
		//set sizes
		labelName.setMinWidth(LABEL_LENGTH);
		labelPwd.setMinWidth(LABEL_LENGTH);
		btnAbbrechen.setMinWidth(LABEL_LENGTH);
		textPwd.setPrefWidth(100);
		textName.setPrefWidth(100);
				
		VBox rows = new VBox(VERTICAL_GAP);
		rows.setAlignment(Pos.CENTER);
		FlowPane row1 = new FlowPane(VERTICAL_GAP, VERTICAL_GAP, labelName, textName);
		FlowPane row2 = new FlowPane(VERTICAL_GAP, VERTICAL_GAP, labelPwd, textPwd);
		FlowPane row3 = new FlowPane(VERTICAL_GAP, VERTICAL_GAP, btnAbbrechen, btnOk);
		
		rows.getChildren().addAll(row1, row2, row3);
		Scene scene = null;
		
		switch (showType) {
		case NO_GAP_ON_LEFT_SIDE:
			// no margin on the left side
			scene = new Scene(rows, 260, 120);
			break;
		case GAP_ON_EVERY_CONTROL:
			// setMargin on every FlowPane
			VBox.setMargin(row1, new Insets(0, 0, 0, VERTICAL_GAP));
			VBox.setMargin(row2, new Insets(0, 0, 0, VERTICAL_GAP));
			VBox.setMargin(row3, new Insets(0, 0, 0, VERTICAL_GAP));
			scene = new Scene(rows, 260, 120);
			break;
		case GAP_ON_PANE:
			// setMargin on the surrounding StackPane
			StackPane root = new StackPane();
			root.getChildren().add(rows);
			StackPane.setMargin(rows, new Insets(0, 0, 0, VERTICAL_GAP));
			//Create scene with root node with size
			scene = new Scene(root, 260, 120);
		}
		
		//Set stage properties
		primaryStage.setTitle("Please log in");
		//Add scene to the stage and make it visible
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
