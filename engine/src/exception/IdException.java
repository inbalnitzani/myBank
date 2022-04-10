package exception;

public class IdException extends FileException{
    private String ID;
    public IdException(String ID){
        this.ID = ID;
    }

    @Override
    public String toString(){
        return "The loan's ID: " + ID +" appears more than once in the loans list";
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> main
