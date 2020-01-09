package UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ProductCard;


import java.beans.EventHandler;
import java.util.ArrayList;

public class Basket extends Application {
    private static Label totalL;
    private static FlowPane result;
    private static FlowPane root;
    private static double totalPrice;
    private static ArrayList<ProductCard> cards;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    static Scene buildUI(Stage stage){
        initialize();
        setDetails();
        setDesign();
        setStyle();
        update();
        return new Scene(root);
    }

    private static void initialize(){
        totalL = new Label();
        result = new FlowPane(Orientation.VERTICAL);
        root = new FlowPane(Orientation.VERTICAL);
    }
    private static void setDetails(){

    }
    private static void setDesign(){
        root.getChildren().addAll(result,totalL);
    }
    private static void setStyle(){

    }

    static void setResult(ArrayList<ProductCard> c){
        cards = c;
    }

    private static void update(){
        totalPrice = 0;
        for(int i=0;i<cards.size();i++){
            result.getChildren().add(buildCard(cards.get(i),i));
            totalPrice+= cards.get(i).getPrice();
        }
        totalL.setText("TOTAL : "+totalPrice+"$");
    }

    private static GridPane buildCard(ProductCard card,int i){
        GridPane pane = new GridPane();
        pane.add(new ImageView(card.getImage()),1,0);
        Button delete = new Button("Delete");
        delete.setOnAction(event ->{
            HomePage.delete(card);
            cards.remove(card);
            delete.setDisable(true);
            result.getChildren().remove(i);
            totalPrice-= card.getPrice();
            totalL.setText("TOTAL : "+totalPrice+"$");
        });
        Button buy = new Button("Buy");
        pane.add(delete,0,1);
        pane.add(buy,2,1);
        return pane;
    }

    public static void main(String[] args){
        launch(args);
    }
}
