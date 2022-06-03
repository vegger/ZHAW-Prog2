package ch.zhaw.prog2.example.reflection;

import javafx.application.Application;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class initializes the main window by creating the scene graph using code
 *
 * To initialize the controller and add the event handlers use reflection
 * to allow access to private fields and methods
 **/

public class MainWindowCodeReflection extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        Pane rootNode = initMainWindow();

        // setup scene
        Scene scene = new Scene(rootNode);

        primaryStage.setScene(scene);
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
        pane.setPrefSize(500.0,400.0);
        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox,0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);

        // Initialize Controller
        MainWindowController controller = new MainWindowController();
        setPrivateFieldValue(controller, "viewText", viewText);
        setPrivateFieldValue(controller, "inputText", inputText);
        showTextButton.setOnAction(event -> callPrivateMethod(controller, "handleShow"));
        clearTextButton.setOnAction(event -> callPrivateMethod(controller, "handleClear"));
        System.out.printf("Controller is of class %s%n", controller.getClass().getCanonicalName());

        return pane;
    }


    // set the value of a private object field using reflection
    private void setPrivateFieldValue( Object object, String fieldName, Object value) {
        try {
            // get the field reference for the given fieldName
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // allows access to the private field
            field.set(object, value);  // sets value to the field of the given object
        } catch (NoSuchFieldException e) {
            System.err.printf("No field %s on Object %s%n",fieldName, object);
        } catch (IllegalAccessException e) {
            System.err.printf("Access to field %s not possible!%n", fieldName);
        }
    }

    // call a private method of an object using reflection
    private void callPrivateMethod(Object object, String methodName, Object... values) {
        try {
            // get the method reference for the given methodName
            Method handleChange = object.getClass().getDeclaredMethod(methodName);
            handleChange.setAccessible(true); // allows access to the private method
            handleChange.invoke(object, values); // calls the method on the given object
        } catch (NoSuchMethodException e) {
            System.err.printf("No field %s on Object %s%n",methodName, object);
        } catch (IllegalAccessException e) {
            System.err.printf("Access to field %s not possible!%n", methodName);
        } catch (InvocationTargetException e) {
            System.err.printf("Error calling method %s: %s!%n", methodName, e.getCause().getMessage());
        }
    }
}
