package ch.zhaw.prog2.step15;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
/**
 * Nicht immer muss sich etwas bewegen. Hier wurde ein Objekt (Rechteck) erstellt und anschliessend
 * auf 3 Arten transformiert. Nach der Transformation bleibt das Objekt im Endstatium stehen...
 * @author bles
 * @version 1.0
 */
public class MainTransformation extends Application {
	   @Override 
	   public void start(Stage stage) { 
	      //Drawing a Rectangle
	      Rectangle rectangle = new Rectangle(50, 50, 100, 75); 
	      Button button = new Button();
	      button.setLayoutX(20);
	      button.setLayoutY(200);
	      button.setText("next step");
	      button.setOnMouseClicked(e -> transform(rectangle));
	      
	      //Setting the color of the rectangle 
	      rectangle.setFill(Color.BURLYWOOD); 
	      
	      //Setting the stroke color of the rectangle 
	      rectangle.setStroke(Color.BLACK); 

	      //Creating a Group object  
	      Group root = new Group(button, rectangle); 
	      
	      //Creating a scene object 
	      Scene scene = new Scene(root, 600, 300);  
	      
	      //Setting title to the Stage 
	      stage.setTitle("Multiple transformations"); 
	         
	      //Adding scene to the stage 
	      stage.setScene(scene); 
	         
	      //Displaying the contents of the stage 
	      stage.show(); 
	   }   
	   
	   private void transform(Node node) {
		      //creating the rotation transformation 
		      Rotate rotate = new Rotate(); 
		      
		      //Setting the angle for the rotation 
		      rotate.setAngle(10); 
		      
		      //Setting pivot points for the rotation 
		      rotate.setPivotX(150); 
		      rotate.setPivotY(225); 
		       
		      //Creating the scale transformation 
		      Scale scale = new Scale(); 
		      
		      //Setting the dimensions for the transformation 
		      scale.setX(1.1); 
		      scale.setY(1.1); 
		      
		      //Setting the pivot point for the transformation 
		      scale.setPivotX(300); 
		      scale.setPivotY(135); 
		       
		      //Creating the translation transformation 
		      Translate translate = new Translate();       
		      
		      //Setting the X,Y,Z coordinates to apply the translation 
		      translate.setX(20); 
		      translate.setY(0); 
		      translate.setZ(0); 
		       
		      //Adding all the transformations to the rectangle 
		      node.getTransforms().addAll(rotate, scale, translate); 
		   
	   }
	   public static void main(String args[]){ 
	      launch(args); 
	   } 

}
