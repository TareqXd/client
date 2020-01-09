package UI;

import connection.Services;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Product;

import java.io.File;

public class EditProduct extends Application {

    private static Label nameL,price,quantity;
    private static TextField nameT,priceT,quantityT;
    private static Button back,submit,chooseFile;
    private static ComboBox<String> cats;
    private static File file;
    private static GridPane root;
    private static Product product;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(buildUI(primaryStage));
        primaryStage.show();
    }

    public static Scene buildUI(Stage stage){
        initialize();
        setDetails();
        setDesign();
        setStyle();
        back.setOnAction(event -> {
            stage.setScene(HomePage.buildUI(stage));
        });
        submit.setOnAction(event -> {
            if(!nameT.getText().isEmpty() && !priceT.getText().isEmpty() && !quantityT.getText().isEmpty()){
                try {
                    int q = Integer.parseInt(quantityT.getText());
                    double p = Double.parseDouble(priceT.getText());
                    String name = nameT.getText();
                    String category = cats.getValue();
                    product.setName(name);product.setPrice(p);
                    product.setQuantity(q);product.setCategory(category);
                    if (file != null)
                        product.setImage(Services.encodeImage(file));
                    Services.editProduct(product);
                }catch (Exception e){
                    System.out.println("Wrong Input");
                }
            }
        });
        chooseFile.setOnAction(event -> {
            file = Services.chooseFile();
        });

        return new Scene(root);
    }

    private static void initialize(){
        nameL = new Label("Product Name : ");
        price = new Label("Product Price : ");
        quantity = new Label("Product Quantity : ");

        nameT = new TextField(product.getName());
        priceT = new TextField(product.getPrice()+"");
        quantityT = new TextField(product.getQuantity()+"");

        chooseFile = new Button("Choose Picture");
        submit = new Button("Submit");
        back = new Button("back");

        cats = new ComboBox<>();

        root = new GridPane();
    }

    private static void setDetails(){
        cats.setItems(FXCollections.observableArrayList(
                "Foods","Others"));
        cats.setValue(product.getCategory());
    }

    private static void setDesign(){
        root.add(nameL,0,0);root.add(nameT,1,0);
        root.add(price,0,1);root.add(priceT,1,1);
        root.add(quantity,0,2);root.add(quantityT,1,2);
        root.add(chooseFile,0,3);root.add(cats,1,3);
        root.add(back,0,4);root.add(submit,1,4);
    }

    private static void setStyle(){

    }

    public static void setProduct(Product p){
        product = p;
    }

    public static void main(String[] args){
        launch(args);
    }
}
