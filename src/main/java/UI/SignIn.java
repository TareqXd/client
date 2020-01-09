package UI;

import connection.Services;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.MyResponse;
import model.SignInModel;


public class SignIn extends Application {

    //Requested Data for SignIn
    private static String email,pass;
    //GUI Variables
    private static Label L_email,L_password;
    private static TextField T_email,T_password;
    private static Button submit,back;
    private static GridPane root;
    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    static Scene buildUI(Stage stage){

        initialize();
        setDetails();

        submit.setOnAction(event -> {
            email = T_email.getText();
            pass = T_password.getText();
            if(checkValidation()){
                try {
                    //Build a DTO
                    SignInModel model = new SignInModel(email,pass);
                    //Send DTO -> Get Response From Server
                    MyResponse response = Services.signIn(model);
                    if(response.getCode() == 200) {
                        //If Successfully Logged, Go to HomePage
                        stage.setScene(HomePage.buildUI(stage));
                    }else
                        System.out.println(response.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                wrongInput();
            }
        });
        back.setOnAction(event -> {
            stage.setScene(MainMenu.buildUI(stage));
        });

        setDesign();
        setStyle();
        return new Scene(root);
    }

    //Set Buttons Style
    private static void setButtonSetting(Button button){
        button.setMaxSize(100,70);
        button.setMinSize(100,70);
    }
    //Initialize Variables
    private static void initialize(){
        //Initialize Labels
        L_email = new Label("Email : ");
        L_password = new Label("Password : ");
        //Initialize TextFields
        T_email = new TextField();
        T_password= new PasswordField();
        //Initialize Buttons
        submit = new Button("Submit");
        back = new Button("Back");
        //Initialize root
        root = new GridPane();
    }
    //Set Nodes' Details
    private static void setDetails(){
        //TextFields Details
        T_email.setPromptText("Type Your Email...");
        T_password.setPromptText("Type Your Password...");
        //Button Details
        setButtonSetting(submit);
        setButtonSetting(back);
        //Root Details
        root.setHgap(10); root.setVgap(20);
        root.setPadding(new Insets(10));

    }
    //Check Validation Condition
    private static boolean checkValidation(){
        return !email.isEmpty() && !pass.isEmpty();
    }
    //Notify User for the wrong input
    private static void wrongInput(){
        System.out.println();
        if(email.length()==0)
            L_email.setTextFill(Color.RED);
        if(pass.length()==0)
            L_password.setTextFill(Color.RED);
    }
    //Bind Nodes
    private static void setDesign(){
        root.add(L_email,0,0);
        root.add(T_email,1,0);
        root.add(L_password,0,1);
        root.add(T_password,1,1);
        root.add(submit,1,2);
        root.add(back,0,2);
    }
    //Set Nodes' Style
    private static void setStyle(){

    }

    public static void main(String[] args){
        launch(args);
    }
}
