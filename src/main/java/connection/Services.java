package connection;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//SERVICE LAYER (LOGIC LAYER)
public class Services {

    //Get the ID by Decoding the TOKEN
    static Long getIdFromToken(String token) {
        Claims claims = Jwts.parser().
                setSigningKey("JWTSuperSecretKey").
                parseClaimsJws(token).
                getBody();
        return Long.parseLong(claims.getSubject());
    }
    //GET THE RESPONSE OF SING IN REQUEST
    public static MyResponse signIn(SignInModel model) {
        return SignController.signIn(model);
    }
    //GET THE RESPONSE OF THE SING UP REQUEST
    public static MyResponse signUp(User user) {
        return SignController.signUp(user);
    }
    //CONVERT THE USER INTO CARD OBJECT TO DISPLAY
    private static UserCard getCard(User user) {
        String str_Image = user.getImage();
        Image image = null;//Base64.getDecoder().decode(str_Image);
        return new UserCard(image, user.getName());
    }
    //GET ARRAY OF RESULTS OF SEARCHING FOR A PRODUCT NAME
    public static ArrayList<ProductCard> getSearchResult(String name) {
        ArrayList<Product> productArrayList = ProductController.searchFor(name);
        if (productArrayList == null)
            return null;
        ArrayList<ProductCard> cardArrayList = new ArrayList<>();
        for (Product product : productArrayList) {
            ProductCard card = new ProductCard(product.getName(), product.getPrice(), decodeImage(product.getImage()));
            card.setId(product.getId());
            cardArrayList.add(card);
        }
        return cardArrayList;
    }
    //GET THE LAST 5 PRODUCTS
    public static ArrayList<ProductCard> getTop(){
        ArrayList<Product> products = ProductController.getTop5();
        ArrayList<ProductCard> cards = new ArrayList<>();
        for(Product product : products){
            ProductCard card = new ProductCard(product.getName(), product.getPrice(), decodeImage(product.getImage()));
            card.setId(product.getId());
            cards.add(card);
        }
        return cards;
    }
    //CHOOSE FILE FROM THE DEVICE
    public static File chooseFile(){
        FileChooser chooser = new FileChooser();
        return chooser.showOpenDialog(new Stage());
    }
    //ENCODE FILE BYTES INTO STRING USING BASE64
    public static String encodeImage(File image) {
        if(image == null)
            return "";
        try {
            byte[] bytes = Files.readAllBytes(image.toPath());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //DECODING A STRING INTO IMAGE USING BASE64
    public static Image decodeImage(String encoded) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(encoded));
            BufferedImage bufferedImage1 = ImageIO.read(bis);
            return SwingFXUtils.toFXImage(bufferedImage1,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //REQUEST FOR ADD A PRODUCT
    public static void addProduct(Product product) {
        if(product != null){
            ProductController.addProduct(product);
        }
    }
    //LOG OUT BY SET ALL VARIABLES OF USER NULL
    public static void logOut() {
        SignController.logOut();
    }
    //REQUEST FOR DELETE THE SPECIFIED PRODUCT
    public static void deleteProduct(long id) {
        ProductController.deleteProduct(id);
    }
    //REQUEST FOR EDITING THE SENT PRODUCT
    public static void editProduct(Product product) {
        ProductController.editProduct(product);
    }
    //GET THE DETAILS OF THE PRODUCT BY ITS ID
    public static Product getProduct(long id) {
        return ProductController.getProduct(id);
    }
    //GET ALL THE PRODUCT IN DB
    public static ArrayList<Product> getProducts() {
        return ProductController.getProducts();
    }
}
