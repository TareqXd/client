package connection;

import model.Product;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

class ProductController {

    private static final String server_domain = "http://localhost:8080/api/";
    private static final String searchByName = server_domain+"products/allByName?name=";
    private static final String top5 = server_domain+"products/top";
    private static final String addProduct = server_domain+"/products/add";
    private static final String deleteProduct = server_domain+"/products/delete/";
    private static final String editProduct = server_domain+"/products/edit";
    private static final String getProductById = server_domain+"/products/";

    static ArrayList<Product> searchFor(String name){
        CloseableHttpResponse response = SignController.getResponse(searchByName+name);
        if(response!= null && response.getStatusLine().getStatusCode() == 200){
            try {
                String string = EntityUtils.toString(response.getEntity());
                return getProductsFromJSON(new JSONArray(string));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static ArrayList<Product> getTop5(){
        CloseableHttpResponse response = SignController.getResponse(top5);
        if(response != null && response.getStatusLine().getStatusCode() == 200){
            try {
                String string = EntityUtils.toString(response.getEntity());
                return getProductsFromJSON(new JSONArray(string));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ArrayList<Product> getProductsFromJSON(JSONArray array){
        return MyJsonBuilder.json2ProductList(array);
    }

    static void addProduct(Product product) {
        try {
            CloseableHttpResponse response = SignController.postResponse(addProduct,
                    new StringEntity(MyJsonBuilder.product2JSON(product).toString()));
            if(response == null || response.getStatusLine().getStatusCode() != 200)
                System.out.println("Error");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static void deleteProduct(long id) {
       CloseableHttpResponse response = SignController.getResponse(deleteProduct+id);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    static void editProduct(Product product) {
        try {
            SignController.postResponse(editProduct,new StringEntity(MyJsonBuilder.product2JSON(product).toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static Product getProduct(long id) {
        CloseableHttpResponse response = SignController.getResponse(getProductById+id);
        if(response != null && response.getStatusLine().getStatusCode() == 200) {
            try {
                return MyJsonBuilder.toProduct(new JSONObject(EntityUtils.toString(response.getEntity())));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static ArrayList<Product> getProducts() {
        CloseableHttpResponse response = SignController.getResponse(getProductById);
        if(response != null && response.getStatusLine().getStatusCode() == 200){
            try {
                String string = EntityUtils.toString(response.getEntity());
                return getProductsFromJSON(new JSONArray(string));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
