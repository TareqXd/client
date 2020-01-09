package UI;

import connection.Services;
import connection.SignController;
import connection.UsersController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Product;
import model.ProductCard;
import model.User;
import model.UserCard;

import java.util.ArrayList;
import java.util.Base64;

public class HomePage extends Application {
    //GUI Variables
    private static Button search,showProducts,showEmployees,basket,addProduct,logout; //Menu Buttons
    private static FlowPane menuList;
    private static ScrollPane scrollPane;
    private static GridPane root,gridPosts;
    private static ArrayList<ProductCard> products; // List Of Basket's Products;

    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    static Scene buildUI(Stage stage){

        initialize();
        setDetails();
        setDesign();
        setStyle();
        //Build A THREAD To Get Data From Server
        Task<Void> longT = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                Platform.runLater(HomePage::update);
                return null;
            }
        };
        //Set Menu's Buttons Actions
        search.setOnAction(event -> {
            stage.setScene(Search.buildUI(stage));
        });
        addProduct.setOnAction(event -> {
            stage.setScene(CreateProduct.buildUI(stage));
        });
        showProducts.setOnAction(event -> {
            ArrayList<Product> products = Services.getProducts();
            FlowPane root = new FlowPane(Orientation.VERTICAL);
            for (Product product : products) {
                ProductCard card = new ProductCard(product.getName(), product.getPrice(), Services.decodeImage(product.getImage()));
                card.setId(product.getId());
                root.getChildren().addAll(card.getUI());
            }
            Stage stage1 = new Stage();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(root);
            stage1.setScene(new Scene(scrollPane));
            stage1.show();
        });
        showEmployees.setOnAction(event -> {

        });
        basket.setOnAction(event -> {
            //Send the Basket List to the Basket UI
            Basket.setResult(products);
            Stage stage1 = new Stage();
            stage1.setScene(Basket.buildUI(stage1));
            stage1.show();
        });
        logout.setOnAction(event -> {
            Services.logOut();
            stage.setScene(MainMenu.buildUI(stage));
        });
        new Thread(longT).start();
        return new Scene(root);
    }

    //Set Buttons Style
    private static void setButtonSetting(Button button){
        button.setMaxSize(100,70);
        button.setMinSize(100,70);
    }
    //Initialize Variables
    private static void initialize(){
        products = new ArrayList<>();
        //Initialize Labels

        //Initialize Buttons
        search = new Button("Search");
        showProducts = new Button("Products");
        showEmployees = new Button("Employees");
        basket = new Button("Basket");
        addProduct = new Button("Add Product");
        logout = new Button("LogOut");
        //Initialize panes
        root = new GridPane();
        gridPosts = new GridPane();
        scrollPane = new ScrollPane();
        menuList = new FlowPane(Orientation.HORIZONTAL);
    }
    //Set Nodes' Details
    private static void setDetails(){
        //TextFields Details

        //Button Details
        setButtonSetting(search);
        setButtonSetting(showEmployees);
        setButtonSetting(showProducts);
        setButtonSetting(basket);

        setButtonSetting(addProduct);
        setButtonSetting(logout);
        //Root Details
        root.setHgap(10); root.setVgap(20);
        root.setPadding(new Insets(10));

    }
    //Bind Nodes
    private static void setDesign(){
        menuList.getChildren().addAll(basket,showProducts,search);

        if(SignController.isIsAdmin()){
            menuList.getChildren().add(addProduct);
        }
        menuList.getChildren().add(logout);
        scrollPane.setContent(gridPosts);
        root.add(menuList,0,1);
        root.add(scrollPane,0,2);
    }
    //Set Nodes' Style
    private static void setStyle(){
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
    //Update GUI When Getting Products List from Server
    private static void update(){
        //Get The Last 5 Products from the Server as ProductCards
        ArrayList<ProductCard> cards = Services.getTop();
        //For each ProductCard build its GUI Card and Add It to List
        for(int i=0;i<cards.size();i++) {
            gridPosts.add(cards.get(i).getUI(), 0, i);
            int finalI = i;
            //Set its (ADD TO THE BASKET Button) Action
            cards.get(i).setAdd2Basket(event -> {
                products.add(cards.get(finalI));
            });
        }
        //Resize the Screen
        root.resize(Math.max(scrollPane.getWidth(),menuList.getWidth()),Math.max(scrollPane.getHeight(),menuList.getHeight()));
    }
    //Delete A Product From BasketList
    static void delete(ProductCard card){
        products.remove(card);
    }

    public static void main(String[] args){
        launch(args);
    }
}
