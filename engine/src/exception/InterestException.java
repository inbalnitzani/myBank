package exception;

public class InterestException extends FileException{
    private int interest;
    private String id;
    public InterestException(int interest, String id){
        this.interest=interest;
        this.id=id;
    }

    @Override
    public String toString() {
        return "The loan "+id+" includes the interest "+interest+" which is out of range.\n"+
                "An interest must be in a range of 0 to 100";
    }
}
