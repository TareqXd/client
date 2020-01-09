package connection;

import model.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class UsersController {
    //Domain Path
    private static final String server_domain = "http://localhost:8080/api/";

    //Request
    private static final String getUser = server_domain + "users/";
    private static final String getAllUsers = server_domain + "users/";
    private static final String isAdmin = server_domain + "users/role";


    //Object of Logged User
    static User me;

    //user methods
    static User getUser(long id) {
        CloseableHttpResponse response = SignController.getResponse(getUser + id);
        if (response!= null && response.getStatusLine().getStatusCode() == 200) {
            JSONObject object = null;
            try {
                object = new JSONObject(EntityUtils.toString(response.getEntity()));
                return MyJsonBuilder.toUser(object);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            assert response != null;
            System.err.println("GET USER RESPONSE : " + response.getStatusLine().getStatusCode());
        }
        return null;
    }

    //Request to check if the Current User is ADMIN
    static Boolean isAdmin(){
        CloseableHttpResponse response = SignController.getResponse(isAdmin);
        if(response.getStatusLine().getStatusCode() == 200){
            try {
                return Boolean.parseBoolean(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Request for All Users in DB
    static ArrayList<User> getAllUsers(){
        CloseableHttpResponse response = SignController.getResponse(getAllUsers);
        if(response != null && response.getStatusLine().getStatusCode() == 200) {
            try {
                return MyJsonBuilder.json2UserList(new JSONArray(EntityUtils.toString(response.getEntity())));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
