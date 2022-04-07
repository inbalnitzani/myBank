package exception;

public class NamesException extends FileException{
    private String name;
    public NamesException(String name){
        this.name = name;
    }
    /*public String getName(){
        return name;
    }
*/
    @Override
    public String toString(){
        return "The customer " + name +" appears more than once in the customer list";
    }
}
