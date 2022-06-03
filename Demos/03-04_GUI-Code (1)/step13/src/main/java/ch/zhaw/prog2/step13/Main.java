package ch.zhaw.prog2.step13;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Einfaches Beispiel eines Canvas mit 3 Rechtecken. Die Grösse des Canvas reicht nicht aus, um
 * alle Rechtecke vollständig zu zeigen. Eine Änderung des Pane reicht nicht aus, weil das Canvas
 * nichts von der Änderung erfährt.
 * @author bles
 * @version 1.0
 */
public class Main extends Application {
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
        gc.fillRoundRect(170, 10, 150, 50, 10, 10);
        // Change the fill color
        gc.setFill(Color.BLUE);    
        // Draw a rounded Rectangle
        gc.strokeRoundRect(10, 180, 50, 90, 10, 10);
	}
	
	public static void main(String[] args) {
		launch(args);		
	}

}
