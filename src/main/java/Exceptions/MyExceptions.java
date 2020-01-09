package Exceptions;

public class MyExceptions extends Exception{

    private ExceptionType type;

    public MyExceptions(ExceptionType type) {
        this.type = type;
    }

    public MyExceptions(String s, ExceptionType type) {
        super(s);
        this.type = type;
    }

    public void setType(ExceptionType type){
        this.type = type;
    }

    public int getCode(){
        return type.code();
    }
}
