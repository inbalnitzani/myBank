package abs.exception;

public class NamesException extends Exception{
    private String name;
    public NamesException(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
