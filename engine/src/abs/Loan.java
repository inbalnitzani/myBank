package abs;

import java.util.Collection;
import java.util.List;


public class Loan {
    public enum Status {
        PENDING,ACTIVE,RISK,FINISHED
    }
    //DATA MEMBERS
    private String id;
    private Client owner;
    private int category;
    private double originalAmount,amountPaidBack,amountCollected;
    private int interestRate;
    private Status status;
    private int startingTimeUnit,endingTimeUnit,pace,nextPayment;
    private List <Client> givers;
    private List <Payment> payments;

    //CTOR
    Loan(Client owner, int amount, int rate){
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
    public Status getStatus(){
        return this.status;
    };
    public int getCategory(){
        return this.category;
    };
    public double getOriginalAmount(){
        return this.originalAmount;
    };
    public double getAmountPaidBack(){
        return this.amountPaidBack;
    };
    public double getAmountCollected(){
        return this.amountCollected;
    };
    public int getInterestRate(){
        return this.interestRate;
    };
    public int getStartingTimeUnit(){
        return this.startingTimeUnit;
    };
    public int getEndingTimeUnit(){
        return this.endingTimeUnit;
    };
    public int getPace(){
        return this.pace;
    };
    public Collection <Client> getGivers(){
        return this.givers;
    };
    public Collection <Payment> getPayments(){
        return this.payments;
    };
    public int getTotalTU(){
        return this.endingTimeUnit - this.startingTimeUnit;
    };
    public int getNextPayment(){
        return nextPayment;
    }

    //SETTERS
    public void setNextPayment() {
        //if(status.equals(abs.e_status.ACTIVE))

    }

}
