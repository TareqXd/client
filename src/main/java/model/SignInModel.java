package model;


/***
 * Data Transfare Object
**/
public class SignInModel {

    private String email;
    private String password;

    public SignInModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
