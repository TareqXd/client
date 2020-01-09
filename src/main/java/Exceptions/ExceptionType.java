package Exceptions;

public enum ExceptionType {
    WrongJsonFormant,UnsuccessfulConnection,UnhandledConnectionException,UndefinedID;

    public String message(){
        switch (this){
            case WrongJsonFormant: return "Wrong Message Format, maybe it doesn't have some fields or have different names";
            case UnsuccessfulConnection: return "Server didn't send OK message";
            case UnhandledConnectionException: return "Undefined Connection Problem, Maybe the server is OFF, URL is wrong ...etc";
            case UndefinedID: return "The ID requested is not found";
            default:{
                return "UnDefined Exception";
            }
        }
    }

    public int code(){
        switch (this){
            case WrongJsonFormant: return 10;
            case UnsuccessfulConnection: return 100;
            case UndefinedID: return 101;
            case UnhandledConnectionException: return -1;
            default: return -1;
        }
    }
}
