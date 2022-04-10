package exception;

public class NegativeTimeException extends FileException{
    public String id;
    public NegativeTimeException(String id){
        this.id=id;
    }

    @Override
    public String toString() {
        return "The loan "+id+" has a negative total yaz time or a negative pace.";
    }
}
