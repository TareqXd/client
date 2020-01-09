package model;

/**
 * DTO of Products
 */
public class Product {

    private long id;
    private String name;
    private int quantity;
    private String category;
    private double price;
    private String image;

    public Product(String name, String cat, double price) {
        this.name = name;
        this.category = cat;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " : "+price+" : "+quantity+" : "+ category +" : "+ ((image == null)?"null":"exists");
    }
}
