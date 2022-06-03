package ch.zhaw.prog2.step13a;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Demoprogramm für das Verbinden von Properties (mit bind)
 * --> Ebenfalls zeigbar: Reihenfolge spielt keine Rolle
 * --> Property Binding am Schluss, damit das Fenster nicht mit Höhe 0 gezeigt wird.
 * @author bles
 * @version 1.0
 */
public class MainBindings extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Canvas canvas = new Canvas(200, 200);
        
        Pane pane = new Pane();
        pane.getChildren().add(canvas);

        drawCanvas(canvas);
                 
        // Create the Scene
        Scene scene = new Scene(pane);
        // Add the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title of the Stage
        primaryStage.setTitle("Drawing on a Canvas");
        // Display the Stage
        primaryStage.show();     
        
        pane.widthProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
        		canvas.setWidth((double) newVal);
        		drawCanvas(canvas); // eigene Methode zum Neuzeichnen
        		}
       		});
         // with property binding
        canvas.heightProperty().bind(pane.heightProperty());
       
        // as lambda expression
//        pane.widthProperty().addListener(a -> {
//        	canvas.setWidth(pane.getWidth());
//        	drawCanvas(canvas); // eigene Methode zum Neuzeichnen
//        	});
//        pane.heightProperty().addListener(a -> {
//        	canvas.setHeight(pane.getHeight());
//        	drawCanvas(canvas); // eigene Methode zum Neuzeichnen
//        	});
        
		
	}
	
	private void drawCanvas(Canvas canvas) {
        // Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Set line width
        gc.setLineWidth(2.0);
        gc.setStroke(Color.BLUEVIOLET);
        gc.setFill(Color.RED);
        // Draw a rounded Rectangle
        gc.strokeRoundRect(10, 10, 50, 50, 10, 10);
        // Draw a filled rounded Rectangle
        gc.fillRoundRect(170, 10, 100, 50, 10, 10);
        // Change the fill color
        gc.setFill(Color.BLUE);    
        // Draw a rounded Rectangle
        gc.strokeRoundRect(10, 110, 50, 170, 10, 10);
	}
	
	public static void main(String[] args) {
		launch(args);		
	}

}
