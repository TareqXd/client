package UI;

import javafx.application.Application;
import javafx.stage.Stage;

//MAIN CLASS

public class Main extends Application {

    public void start(Stage primaryStage){
        //CALLING SPLASH SCREEN
        Splash.buildUI(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }

}
