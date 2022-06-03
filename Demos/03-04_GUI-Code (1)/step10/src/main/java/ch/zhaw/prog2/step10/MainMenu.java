package ch.zhaw.prog2.step10;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Einfache Applikation mit einem Canvas und einem Menü
 * Hinweise auf das Bild zum Menüeintrag, den ShortKey und die anonyme innere Klasse im createMenu
 * Sonst keine Aktionen auf den Menüs
 * --> Canvas bleibt auf gleicher Grösse, Elemente bleiben abgeschnitten
 * @author bles
 * @version 1.0
 */
public class MainMenu extends Application {
	
	public static void main(String[] args) {
		launch(args);		
	}
    @Override
    public void start(Stage primaryStage) {
 
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
		Canvas canvas = new Canvas(250, 250);
        
        drawCanvas(canvas);
        createMenu(menuBar);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, canvas);
                
        Scene scene = new Scene(vbox, 250, 250);
        
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
        Menu helpMenu = new Menu("Help");
        Menu helpSubMenu = new Menu("from");
        
        // Create MenuItems
        MenuItem newItem = new MenuItem("New");
        MenuItem openFileItem = new MenuItem("Open File");
        MenuItem exitItem = new MenuItem("Exit");
        
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        
        CheckMenuItem helpBook = new CheckMenuItem("Book");
        helpBook.setSelected(true);
        CheckMenuItem helpDozent = new CheckMenuItem("Dozent");
        helpDozent.setSelected(false);
        
        // add picture
		Image newImage = new Image(getClass().getClassLoader().getResourceAsStream("new-icon2.png"));
		newItem.setGraphic(new ImageView(newImage));
        // add short key
        exitItem.setAccelerator(KeyCombination.keyCombination("ESC"));
        // add handler
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		Platform.exit();
        	}
        });
        // or as lambda expression
        //exitItem.setOnAction(e -> Platform.exit());

        // Add menuItems to the Menus
        fileMenu.getItems().add(newItem);
        fileMenu.getItems().add(openFileItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitItem);
        editMenu.getItems().addAll(copyItem, pasteItem);
        helpSubMenu.getItems().addAll(helpBook, helpDozent);
        helpMenu.getItems().add(helpSubMenu);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		
	}
}
