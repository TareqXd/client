package model;

import Exceptions.MyExceptions;

/**
 * DTO of Responses of Server
 */

public class MyResponse {
    public static String DONE_MESSAGE = "DONE";
    private String message;
    private MyExceptions myExceptions;
    private int code;

    public MyResponse(String message) {
        this.message = message;
        if(this.message.equalsIgnoreCase(DONE_MESSAGE))
            code = 200;
    }

    public MyResponse(MyExceptions myExceptions) {
        this.myExceptions = myExceptions;
        this.message = myExceptions.getMessage();
        this.code = myExceptions.getCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyExceptions getMyExceptions() {
        return myExceptions;
    }

    public void setMyExceptions(MyExceptions myExceptions) {
        this.myExceptions = myExceptions;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
