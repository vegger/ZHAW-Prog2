package ch.zhaw.prog2.step15a;

import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Animationen, parallel, zum zeigen
 * @author bles
 * @version 1.0
 */
public class MainAnimation extends Application {
	private final double transTime = 2000.0;
	
	public void start(Stage stage) {      

		//Creating a Group object   
		//Group root = new Group(rotate()); 
		Group root = new Group(rotate()); 
		Circle circle;
		circle = (Circle) scale();
		circle.setEffect(new Lighting());
		translateExisting(circle);
		fillTransition(circle);
//		root.getChildren().add(translate());
//		root.getChildren().add(rotate());
		root.getChildren().add(circle);

		//Creating a scene object 
		Scene scene = new Scene(root, 600, 300);   

		//Setting title to the Stage 
		stage.setTitle("Rotate transition example "); 

		//Adding scene to the stage 
		stage.setScene(scene); 

		//Displaying the contents of the stage 
		stage.show(); 
	}  
	
	private Node rotate() {
		//Creating a hexagon 
		Polygon hexagon = new Polygon();        

		//Adding coordinates to the hexagon 
		hexagon.getPoints().addAll(new Double[]{        
				200.0, 50.0, 
				400.0, 50.0, 
				450.0, 150.0,          
				400.0, 250.0, 
				200.0, 250.0,                   
				150.0, 150.0, 
		}); 
		//Setting the fill color for the hexagon 
		hexagon.setFill(Color.TRANSPARENT); 
		hexagon.setStrokeWidth(1.0);
		hexagon.setStroke(Color.BLUE);

		//Creating a rotate transition    
		RotateTransition rotateTransition = new RotateTransition(); 

		//Setting the duration for the transition 
		rotateTransition.setDuration(Duration.millis(2000)); 

		//Setting the node for the transition 
		rotateTransition.setNode(hexagon);       

		//Setting the angle of the rotation 
		rotateTransition.setByAngle(360); 

		//Setting the cycle count for the transition 
		rotateTransition.setCycleCount(50); 

		//Setting auto reverse value to false 
		rotateTransition.setAutoReverse(false); 

		//Playing the animation 
		rotateTransition.play(); 
		return hexagon;
	}
	
	private Node scale() {
	     //Drawing a Circle 
	      Circle circle = new Circle(); 
	      
	      //Setting the position of the circle 
	      circle.setCenterX(300.0f); 
	      circle.setCenterY(135.0f); 
	      
	      //Setting the radius of the circle 
	      circle.setRadius(5.0f); 
	      
	      //Setting the color of the circle 
	      circle.setFill(Color.BROWN); 
	      
	      //Setting the stroke width of the circle 
	      circle.setStrokeWidth(20); 
	       
	      //Creating scale Transition 
	      ScaleTransition scaleTransition = new ScaleTransition(); 
	      
	      //Setting the duration for the transition 
	      scaleTransition.setDuration(Duration.millis(2000)); 
	      
	      //Setting the node for the transition 
	      scaleTransition.setNode(circle); 
	      
	      //Setting the dimensions for scaling 
	      scaleTransition.setByY(50.0); 
	      scaleTransition.setByX(50.0); 
	      
	      //Setting the cycle count for the translation 
	      scaleTransition.setCycleCount(50); 
	      
	      //Setting auto reverse value to true 
	      scaleTransition.setAutoReverse(false); 
	      
	      //Playing the animation 
	      scaleTransition.play(); 
	      return circle;
	}
		
	private void translateExisting(Circle circle) {
	      TranslateTransition translateTransition = new TranslateTransition(); 
	      translateTransition.setDuration(Duration.millis(transTime)); 
	      translateTransition.setNode(circle); 
	      // target value
	      translateTransition.setByX(580); 
	      translateTransition.setCycleCount(50); 
	      translateTransition.setAutoReverse(false);       
	      //Playing the animation 
	      translateTransition.play(); 
	}
	private void fillTransition(Circle circle) {
		FillTransition fillTrans = new FillTransition();
		fillTrans.setShape(circle);
		fillTrans.setDuration(Duration.millis(transTime));
	      // target value
		fillTrans.setToValue(Color.AQUAMARINE);
		fillTrans.setCycleCount(50);
		fillTrans.play();
	}
	public static void main(String args[]){ 
		launch(args); 
	} 

}
