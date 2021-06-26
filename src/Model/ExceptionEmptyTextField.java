package Model;

public class ExceptionEmptyTextField extends Throwable {

    private String text;

    public ExceptionEmptyTextField(){
        text = "Text Field is Empty";
    }
    public ExceptionEmptyTextField(String text){
        this.text = text;
    }

    public String  getMessage(){
        return text;
    }
}
