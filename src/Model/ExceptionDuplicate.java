package Model;

public class ExceptionDuplicate extends Throwable {

    private String text;

    public ExceptionDuplicate(){
        text = "Same Book ID Already exist";
    }
    public ExceptionDuplicate(String text){
        this.text = text;
    }

    public String  getMessage(){
        return text;
    }
}
