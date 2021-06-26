package Model;

public class ExceptionFine extends Exception {

    private double fine;
    private int expBooks;

    public ExceptionFine(){
        super("Candidate have fine");
    }

    public ExceptionFine(String s){
        super(s);
    }

    public ExceptionFine(double f){
        super();
        fine = f;
    }

    public double getFine(){
        return fine;
    }

    public void setExpiredBooks(int l){
        expBooks = l;
    }

    public int getExpiredBooks(){
        return expBooks;
    }
}
