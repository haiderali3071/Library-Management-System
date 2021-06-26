package Model;

public class ExceptionNotFound extends Throwable {

    private String text;

    public ExceptionNotFound(){
        text = "searched book isn't found";
    }
    public ExceptionNotFound(String text){
        this.text = text;
    }

    public String  getMessage(){
        return text;
    }
}
