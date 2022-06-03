package ch.zhaw.prog2.step12a;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Applikation mit einer Zeichenfläche, einigen Rechtecken und einem Menü.
 * Die Zeichnfläche wird der Fenstergrösse angepasst. Indirekt, weil die VBox
 * den ganzen Platz ausfüllt, wenn sie wie hier direkt auf der Scene liegt.
 * Zusatz: Slider im Menü für die Fenstergrösse (beeinflusst die primaryStage)
 * @author bles
 * @version 1.0
 */
public class MainMenuSlider extends Application {
	private Slider slider;
	
	public static void main(String[] args) {
		launch(args);		
	}
    @Override
    public void start(Stage primaryStage) {
 
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
		Canvas canvas = new Canvas(200, 200);
		slider = new Slider(200.0, 400.0, canvas.getHeight());
        
        drawCanvas(canvas);
        createMenu(menuBar);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, canvas);
                
        slider.valueProperty().addListener(new ChangeListener<Number>() {
        	public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
	        	primaryStage.setWidth(slider.getValue());
	        	primaryStage.setHeight(slider.getValue());
	        }
        });
        vbox.widthProperty().addListener(new ChangeListener<Number>() {
	       	public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
	    		canvas.setWidth((double) newVal);
	    		drawCanvas(canvas); // eigene Methode zum Neuzeichnen
	    		}
	   		});
       vbox.heightProperty().addListener(new ChangeListener<Number>() {
	       	public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
	       		canvas.setHeight(vbox.getHeight() - menuBar.getHeight());
	       		drawCanvas(canvas); // eigene Methode zum Neuzeichnen
	       		}
    		});
        
        // doesn't work, because primaryStage.widthProperty is readOnly
        //primaryStage.widthProperty().bind(slider.valueProperty());

        Scene scene = new Scene(vbox, 200, 230);
        
        // Set the Title of the Stage
        primaryStage.setTitle("JavaFX Menu");
        primaryStage.setScene(scene);
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
        gc.strokeRoundRect(10, 10, 150, 150, 10, 10);
        // Draw a filled rounded Rectangle
        gc.fillRoundRect(170, 30, 120, 80, 10, 10);
        // Change the fill color
        gc.setFill(Color.BLUE);    
        // Draw a rounded Rectangle
        gc.strokeRoundRect(10, 130, 120, 170, 10, 10);
	}
	
	private void createMenu(MenuBar menuBar) {
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu editSubMenu = new Menu("Screen size");
        Menu helpMenu = new Menu("Help");
        Menu helpSubMenu = new Menu("from");
        
        // Create MenuItems
        MenuItem newItem = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem openFileItem = new MenuItem("Open File");
        // add picture
		Image newImage = new Image(getClass().getClassLoader().getResourceAsStream("new-icon2.png"));
		newItem.setGraphic(new ImageView(newImage));
        // add short key
        exitItem.setAccelerator(KeyCombination.keyCombination("ESC"));
        // add handler
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		System.exit(0);
        	}
        });
        // or as lambda expression
        //exitItem.setOnAction(e -> Platform.exit());
        
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        
        CheckMenuItem helpBook = new CheckMenuItem("Book");
        helpBook.setSelected(true);
        CheckMenuItem helpDozent = new CheckMenuItem("Dozent");
        helpDozent.setSelected(false);
        
        // Add menuItems to the Menus
        fileMenu.getItems().add(newItem);
        fileMenu.getItems().add(openFileItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitItem);
        editMenu.getItems().addAll(copyItem, pasteItem, new SeparatorMenuItem(), editSubMenu);
        helpSubMenu.getItems().addAll(helpBook, helpDozent);
        helpMenu.getItems().add(helpSubMenu);
        
        // custom menu item with slider
		CustomMenuItem customMenuItem = new CustomMenuItem(slider);
		customMenuItem.setHideOnClick(false);
        editSubMenu.getItems().add(customMenuItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		
	}
}
