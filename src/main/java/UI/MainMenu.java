package UI;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage)  {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    public static Scene buildUI(Stage primaryStage) {
        //Declare Buttons
        Button sign_up = new Button("SIGN UP");
        Button sign_in = new Button("SIGN IN");
        //Set Buttons Actions
        sign_in.setOnAction(event -> {
            primaryStage.setScene(SignIn.buildUI(primaryStage));
        });
        sign_up.setOnAction(event -> {
            primaryStage.setScene(SignUp.buildUI(primaryStage));
        });
        //Set Root Pane Details
        FlowPane root = new FlowPane(Orientation.VERTICAL,sign_in,sign_up);
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        //Set Background
        root.setStyle("-fx-background-image: url(test.png); " +
                        "-fx-background-repeat: no-repeat ;" +
                        "-fx-background-position: left top ;" +
                        "-fx-background-size: cover , auto;");
        return new Scene(root,500,500);
    }

    public static void main(String[] args){
        launch(args);
    }

}
