import java.util.Collection;
import java.util.HashSet;
import java.util.List;

enum e_status{
    PENDING,ACTIVE,RISK,FINISHED
}
public class loan {
    //DATA MEMBERS
    private String id;
    private client owner;
    private int category;
    private double originalAmount,paid,left;
    private int interestRate;
    private e_status status;
    private int startingTimeUnit,endingTimeUnit,pace;
    private List <client> givers;
    private List <payment> payments;

    //CTOR
    loan(client owner, int amount,int rate){
        this.owner = owner;
        this.originalAmount = amount;
        this.interestRate = rate;

    }
    //GETTERS
    public String getLoansID(){
        return this.id;
    };
    public String getOwnersName(){
        return this.owner.getFullName();
    };
    public e_status getStatus(){
        return this.status;
    };
    public int getCategory(){
        return this.category;
    };
    public double getOriginalAmount(){
        return this.originalAmount;
    };
    public double getPaidAmount(){
        return this.paid;
    };
    public double getAmountLeft(){
        return this.left;
    };
    public int getInterestRate(){
        return this.interestRate;
    };
    public int getStartingTimeUnit(){return this.startingTimeUnit};
    public int getEndingTimeUnit(){
        return this.endingTimeUnit;
    }
    public int getPace(){
        return this.pace;
    };
    public Collection <client> getGivers(){
        return this.givers;
    };
    public Collection <payment> getPayments(){
        return this.payments;
    }






   public void printLoansInfo(){
        System.out.println("loan's info:");
        System.out.println("loan's owner is:");
        System.out.println(this.getTakersName());
        System.out.println("loan's status is:");
        System.out.println(this.getStatus());
    }



}
