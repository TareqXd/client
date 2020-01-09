package model;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


public class UserCard {

    private Image image;
    private String name;

    public UserCard(Image image, String name) {
        this.image = image;
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public FlowPane getUI(){
        Label nameLabel = new Label(name);
        return new FlowPane(Orientation.VERTICAL,nameLabel);
    }
}
