package ch.zhaw.prog2.example.code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class initializes the main window by creating the scene graph using code
 *
 * To initialize the controller and add the event handlers we need to change the Controller
 * -> MainWindowControllerPublic:
 * - add a Constructor (or setter) to initialize the fields
 * - make the action methods public
 **/

public class MainWindowCodePublic extends Application {

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


    private Pane initMainWindow()  {
        // Input Text Field
        TextField inputText = new TextField();
        inputText.setLayoutX(60.0);
        inputText.setLayoutY(175.0);

        // Change and Clear Buttons
        Button showTextButton = new Button("Show Text");
        Button clearTextButton = new Button("Clear Text");

        // ViewText Label
        Label viewText = new Label("Show Text Here");
        viewText.setLayoutX(172.0);
        viewText.setLayoutY(121.0);

        // putting it all together
        HBox hbox = new HBox(5.0, inputText, showTextButton, clearTextButton);
        hbox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20.0, viewText, hbox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutY(121.0);

        AnchorPane pane = new AnchorPane(vBox);
        pane.setPrefSize(500.0,150.0);
        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox,0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);

        // Initialize the Controller with the controls it needs access to
        MainWindowControllerPublic controller = new MainWindowControllerPublic(viewText, inputText);
        // create EventHandler using an anonymous class
        showTextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShow();
            }
        });
        // create EventHandler using lambda expression
        clearTextButton.setOnAction(event -> controller.handleClear());
        System.out.printf("Controller is of class %s%n", controller.getClass().getCanonicalName());

        return pane;
    }

}
