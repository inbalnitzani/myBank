package exception;

public class NegativeAmountException extends Exception {
    private double input;

    public NegativeAmountException(double _input){
        input=_input;
    }

    @Override
    public String toString(){
        return "Please enter a positive amount";
    }
}
