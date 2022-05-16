package exception;

public class StringInsteadOfNumber extends Exception{
    private String input;

    public StringInsteadOfNumber(String _input){
        input=_input;
    }

    @Override
    public String toString(){
     return input+" is not a number.\nPlease enter a number";
    }
}
