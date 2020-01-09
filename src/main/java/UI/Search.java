package UI;

import connection.Services;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Product;
import model.ProductCard;

import java.util.ArrayList;


public class Search extends Application {
    private static TextField searchField;
    private static Button search,back;
    private static FlowPane searchBar,resultList,root;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        initialize();
        setDetails();
        setDesign();
        setStyle();
        primaryStage.show();
    }

    static Scene buildUI(Stage stage){
        initialize();
        setDetails();
        setDesign();
        setStyle();
        setActions();
        return new Scene(root);
    }

    private static void initialize(){
        searchField = new TextField();
        search = new Button("Search");
        back = new Button("Back");
        searchBar = new FlowPane(Orientation.HORIZONTAL);
        resultList = new FlowPane(Orientation.VERTICAL);
        root = new FlowPane(Orientation.VERTICAL);
    }
    private static void setDetails(){

    }
    private static void setDesign() {
        searchBar.getChildren().addAll(searchField,search);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(resultList);
        root.getChildren().addAll(searchBar,scrollPane,back);
    }
    private static void setStyle(){

    }
    private static void setActions(){
        search.setOnAction(event -> {
            String name = searchField.getText();
            ArrayList<ProductCard> cards = Services.getSearchResult(name);
            assert cards != null;
            for(ProductCard card : cards)
                resultList.getChildren().addAll(card.getUI());
        });
    }
    public static void main(String[] args){
        launch(args);
    }
}
