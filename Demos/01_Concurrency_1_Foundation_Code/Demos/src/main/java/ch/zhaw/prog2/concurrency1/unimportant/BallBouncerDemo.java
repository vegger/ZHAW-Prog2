package ch.zhaw.prog2.concurrency1.unimportant;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * JavaFX application to see an animated ball bouncing from the walls.
 */
public class BallBouncerDemo extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    private void mainWindow() {
        Pane rootNode = loadPane("Bouncer.fxml");
        Scene scene = new Scene(rootNode);
        URL cssUrl = getClass().getClassLoader().getResource("style.css");
        Objects.requireNonNull(cssUrl, "Could not identify CSS file");
        scene.getStylesheets().add(cssUrl.toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane loadPane(String fxmlFileName) {
        URL fxmUrl = getClass().getClassLoader().getResource(fxmlFileName);
        FXMLLoader loader = new FXMLLoader(fxmUrl);
        Pane rootNode;
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load resource " + fxmlFileName);
        }
        return rootNode;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}
