package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class Splash extends Application {
    //GUI VARIABLES
    static BorderPane pane;
    static ImageView view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        buildUI(primaryStage);
    }

    //BUILD GUI
    static void buildUI(Stage stage){
        initialize();
        FlowPane root = new FlowPane() ;
        root.setStyle("-fx-background-image: url(test.png); " +
                "-fx-background-repeat: no-repeat ;" +
                "-fx-background-position: center center;" +
                "-fx-background-size: cover , auto;");
        stage.setScene(new Scene(root));
        stage.show();
        AtomicBoolean b = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                b.set(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!b.get()){}
        stage.setScene(MainMenu.buildUI(stage));
    }

    //Initialize Variables
    private static void initialize(){
        pane = new BorderPane();
    }
    //Set Design's Details
    private static void setDetails(){
        view.setStyle("-fx-background-color: BLACK");
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);

    }
}
