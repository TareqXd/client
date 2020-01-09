package connection;

import model.Product;
import model.SignInModel;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class MyJsonBuilder {

    //Build JSON Object of SignUp Request Body
    static JSONObject signUp(User user) {
        try {
            return new JSONObject().put("name", user.getName()).put("email", user.getEmail())
                    .put("password", user.getPassword()).put("phone", user.getPhone())
                    .put("address", user.getAddress()).put("image",user.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Build JSON Object of SignIn Request Body
    static JSONObject SignIn(SignInModel model) {
        try {
            return new JSONObject().put("email", model.getEmail()).put("password", model.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Convert from Array of JSON objects intp Array of Products Objects
    static ArrayList<Product> json2ProductList(JSONArray jsonList) {
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i < jsonList.length(); i++) {
            try {
                Product product = toProduct(jsonList.getJSONObject(i));
                if (product != null)
                    list.add(product);
                else
                    return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
    }

    //Convert from Array of JSON objects into Array of Users Objects
    static ArrayList<User> json2UserList(JSONArray jsonList) {
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < jsonList.length(); i++) {
            try {
                User user = toUser(jsonList.getJSONObject(i));
                if (user != null)
                    list.add(user);
                else
                    return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
    }
    //Convert JSON object into Product
    static Product toProduct(JSONObject object) {
        try {
            String name = object.getString("name");
            double price = object.getDouble("price");
            String category = object.getString("category");
            String image = object.getString("image");
            int quantity = object.getInt("quantity");
            Product product = new Product(name,category,price);
            product.setImage(image);
            product.setQuantity(quantity);
            product.setId(object.getLong("id"));
            return product;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Convert JSON object into User
    static User toUser(JSONObject jsonObject) {
        try {
            long id = jsonObject.getLong("id");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            User user = new User(id, name, email, password);
            user.setPhone(jsonObject.getString("phone"));
            user.setAddress(jsonObject.getString("address"));
            user.setImage(jsonObject.getString("image"));
            return user;
        } catch (JSONException e) {
            System.err.println("NO SUCH KEY IN toUser method ..");
        }
        return null;
    }
    //Convert Product object into JSON Object
    static JSONObject product2JSON(Product product){
        try {
            return new JSONObject().put("name",product.getName()).put("price",product.getPrice()).
                                    put("quantity",product.getQuantity()).put("category",product.getCategory()).
                                    put("image",product.getImage()).put("id",product.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
