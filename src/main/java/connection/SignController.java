package connection;

import Exceptions.ExceptionType;
import Exceptions.MyExceptions;
import model.MyResponse;
import model.SignInModel;
import model.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SignController {
    //servers' URLs
    private static final String server_domain = "http://localhost:8080/api/";

    //requests
    private static final String signInURL = server_domain + "auth/signin";
    private static final String signUpURL = server_domain + "auth/signup";
    private static boolean isAdmin;
    //JWT token
    static String token;


    //logging methods
    static MyResponse signUp(User user) {
        CloseableHttpResponse response;
        MyResponse myResponse;
        try {
            response = postResponse(signUpURL, new StringEntity(MyJsonBuilder.signUp(user).toString()));
        } catch (UnsupportedEncodingException e) {
            myResponse = new MyResponse(new MyExceptions(ExceptionType.WrongJsonFormant));
            e.printStackTrace();
            return myResponse;
        }
        if (response.getStatusLine().getStatusCode() == 201) {
            try {
                new JSONObject(EntityUtils.toString(response.getEntity()));
                myResponse = new MyResponse(MyResponse.DONE_MESSAGE);
            } catch (IOException | JSONException e) {
                myResponse = new MyResponse(new MyExceptions(ExceptionType.WrongJsonFormant));
                e.printStackTrace();
            }
            return myResponse;
        }
        else {
            myResponse = new MyResponse(new MyExceptions(ExceptionType.UnsuccessfulConnection));
            myResponse.setMessage(ExceptionType.UnsuccessfulConnection.message()+
                        "\nBut it sent : "+response.getStatusLine().getStatusCode());
        }
        return myResponse;
    }

    static MyResponse signIn(SignInModel model)  {
        MyResponse myResponse;
        try {
            CloseableHttpResponse response = postResponse(signInURL, new StringEntity(MyJsonBuilder.SignIn(model).toString()));
            if (response.getStatusLine().getStatusCode() == 200) {
                JSONObject jsonObject;
                jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                token = jsonObject.getString("accessToken");
                Long id = Services.getIdFromToken(token);
                UsersController.me = UsersController.getUser(id);
                isAdmin = UsersController.isAdmin();
                if(UsersController.me == null)
                    return new MyResponse(new MyExceptions(ExceptionType.UndefinedID));
                myResponse = new MyResponse(MyResponse.DONE_MESSAGE);
            } else {
                myResponse = new MyResponse(new MyExceptions(ExceptionType.UnsuccessfulConnection));
                myResponse.setMessage(ExceptionType.UnsuccessfulConnection.message()+
                        "\nBut it sent : "+response.getStatusLine().getStatusCode());
            }
        }catch (Exception e){
            myResponse = new MyResponse(new MyExceptions(ExceptionType.UnhandledConnectionException));
            e.printStackTrace();
        }
        return myResponse;
    }

    //is the Logged user Admin
    public static boolean isIsAdmin() {
        return isAdmin;
    }
    //Set all logging variables NULL
    public static void logOut(){
        token = null;
        isAdmin = false;
        UsersController.me = null;
    }

    //Connection methods
    static CloseableHttpResponse postResponse(String URL, StringEntity entity) {
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-type", "application/json");
        if(token != null)
            post.setHeader("Authorization", "Bearer "+token);
        post.getRequestLine();
        post.setEntity(entity);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            return httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static CloseableHttpResponse getResponse(String URL) {
        HttpGet get = new HttpGet(URL);
        get.setHeader("Content-type", "application/json");
        if(token !=null)
            get.setHeader("Authorization","Bearer "+token);
        get.getRequestLine();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            return httpClient.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
