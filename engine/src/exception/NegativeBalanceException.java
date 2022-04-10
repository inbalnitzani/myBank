package exception;

public class NegativeBalanceException extends FileException {
    private String name;
    private double balance;

    public NegativeBalanceException(String name,double balance){
        this.name=name;
        this.balance=balance;
    }

    @Override
    public String toString() {
        return "The Client "+name+" has a negative balance of "+balance+".";
    }
}
