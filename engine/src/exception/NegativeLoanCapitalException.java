package exception;

public class NegativeLoanCapitalException extends FileException {
    private String id;
    private double capital;

    public NegativeLoanCapitalException(String id, double capital){
        this.capital=capital;
        this.id=id;
    }

    @Override
    public String toString() {
        return "The loan "+id+" has a negative capital of "+capital;
    }
}
