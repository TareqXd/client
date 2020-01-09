package model;

import UI.EditProduct;
import connection.Services;
import connection.SignController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/***
 * GUI Object
 */
public class ProductCard {

    private long id;

    private String name;

    private double price;

    private Image image;

    private Button add2Basket;

    public ProductCard(String name, double price, Image image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public GridPane getUI(){
        GridPane pane = new GridPane();
        Label nameText = new Label(name); nameText.setPadding(new Insets(10));
        Label priceText = new Label(String.valueOf(price)); priceText.setPadding(new Insets(10));
        FlowPane detailsPane = new FlowPane(Orientation.VERTICAL,nameText,priceText);
        ImageView view = new ImageView(image);
        add2Basket = new Button("Add");
        Button like = new Button("Favorite");
        Button delete = new Button("delete");
        delete.setOnAction(event -> {
            Services.deleteProduct(id);
            delete.setDisable(true);
        });
        Button edit = new Button("edit");
        edit.setOnAction(event -> {
            Stage stage = new Stage();
            System.out.println(id);
            Product product = Services.getProduct(id);
            EditProduct.setProduct(product);
            stage.setScene(EditProduct.buildUI(stage));
            stage.show();
        });
        pane.add(view,0,0);
        pane.add(add2Basket,0,1);
        pane.add(like,1,1);
        if(SignController.isIsAdmin()){
            pane.add(delete,0,2);
            pane.add(edit,1,2);
        }
        pane.add(detailsPane,1,0);
        pane.setPadding(new Insets(10));
        return pane;
    }

    public void setAdd2Basket(EventHandler<ActionEvent> eventHandler){
        add2Basket.setOnAction(eventHandler);
    }
}
