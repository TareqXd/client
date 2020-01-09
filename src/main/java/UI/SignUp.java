package UI;

import connection.Services;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;


public class SignUp extends Application {

    //Requested Details For SignUp
    private static String name,email,pass,address,phone;
    //GUI Variables
    private static Label L_name,L_email,L_password,L_address,L_phone;
    private static TextField T_name,T_email,T_password,T_address,T_phone;
    private static Button submit,back;
    private static GridPane root;
    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    static Scene buildUI(Stage stage){
        initialize();
        setDetails();
        //Buttons Actions
        submit.setOnAction(event -> {
            email = T_email.getText();phone = T_phone.getText();
            pass = T_password.getText();address = T_address.getText();
            name = T_name.getText();
            if(checkValidation()){
                try {
                    //Build a DTO
                    User user = new User(name,email,pass,address);
                    if(!phone.isEmpty())
                        user.setPhone(phone);
                    //Send it to the Server
                    if(Services.signUp(user).getCode() == 200)
                        stage.setScene(SignIn.buildUI(stage));
                    else
                        wrongInput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        back.setOnAction(event -> {
            stage.setScene(MainMenu.buildUI(stage));
        });
        setDesign();
        setStyle();
        return new Scene(root);
    }

    //Initialize Variables
    private static void initialize(){
        //Initialize Labels
        L_name = new Label("Name : ");
        L_email = new Label("Email : ");
        L_password = new Label("Password : ");
        L_phone = new Label("Phone : ");
        L_address = new Label("Address : ");
        //Initialize TextFields
        T_name = new TextField();
        T_email = new TextField();
        T_password = new TextField();
        T_phone = new TextField();
        T_address = new TextField();
        //Initialize Buttons
        submit = new Button("Submit");
        back = new Button("Back");
        //Initialize Root Pane
        root = new GridPane();
    }
    //Set GUI Details
    private static void setDetails(){
        //TextFields Details
        T_name.setPromptText("Type Your Name...");
        T_email.setPromptText("Type Your Email...");
        T_password.setPromptText("Type Your Password...");
        T_address.setPromptText("Type Your Address...");
        T_phone.setPromptText("Type Your phone...");
        //Root Details
        root.setHgap(10); root.setVgap(20);
        root.setPadding(new Insets(10));
    }
    //Check Correctness Condition
    private static boolean checkValidation(){
        return (!email.isEmpty()) && (!name.isEmpty()) && (!pass.isEmpty()) && (!address.isEmpty());
    }
    //For Wrong inputs
    private static void wrongInput(){
        if(name.length()<2)
            L_name.setTextFill(Color.RED);
        if(email.length()<2)
            L_email.setTextFill(Color.RED);
        if(pass.length()<2)
            L_password.setTextFill(Color.RED);
        if(phone.length()<2)
            L_phone.setTextFill(Color.RED);
        if(address.length()<2)
            L_address.setTextFill(Color.RED);
    }
    //Bind the Nodes
    private static void setDesign(){
        root.add(L_name,0,0);
        root.add(T_name,1,0);
        root.add(L_email,0,1);
        root.add(T_email,1,1);
        root.add(L_password,0,2);
        root.add(T_password,1,2);
        root.add(L_address,0,3);
        root.add(T_address,1,3);
        root.add(L_phone,0,4);
        root.add(T_phone,1,4);
        root.add(submit,1,5);
    }
    //Set Node's Style
    private static void setStyle(){

    }

    public static void main(String[] args){
        launch(args);
    }
}
