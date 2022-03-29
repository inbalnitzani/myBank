package abs;

import java.util.Collection;
import java.util.List;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private Client owner;
    private int category;
    private double originalAmount, amountPaidBack, amountCollectedPending;
    private int interestRate;
    private int status;
    private int startingTimeUnit, endingTimeUnit, pace, nextPayment;
    //private List<Client> givers;
    //private List <Payment> payments;

    //CTOR
    LoanDTO(Loan loan) {
        this.owner = loan.getOwner();
        this.id = loan.getLoansID();
        this.category = loan.getCategory();
        this.originalAmount = loan.getOriginalAmount();
        this.startingTimeUnit = loan.getStartingTimeUnit();
        this.endingTimeUnit = loan.getEndingTimeUnit();
        this.pace = loan.getPace();
        this.interestRate = loan.getInterestRate();
    }
    //GETTERS

    public String getLoansID() {
        return this.id;
    }

    ;

    public String getOwnersName() {
        return this.owner.getFullName();
    }

    ;

    public int getStatus(){
       return this.status;
     };
    public int getCategory() {
        return this.category;
    }

    ;

    public double getOriginalAmount() {
        return this.originalAmount;
    }

    ;

    public double getAmountPaidBack() {
        return this.amountPaidBack;
    }

    ;

    public double getAmountCollectedPending() {
        return this.amountCollectedPending;
    }

    ;

    public int getInterestRate() {
        return this.interestRate;
    }

    ;

    public int getStartingTimeUnit() {
        return this.startingTimeUnit;
    }

    ;

    public int getEndingTimeUnit() {
        return this.endingTimeUnit;
    }

    ;

    public int getPace() {
        return this.pace;
    }

    ;

    //   public Collection<Client> getGivers(){
    //      return this.givers;
    // };
    //public Collection <Payment> getPayments(){
    //   return this.payments;
    //};
    public int getTotalTU() {
        return this.endingTimeUnit - this.startingTimeUnit;
    }

    ;

    //public int getNextPayment(){
    //   return nextPayment;
    //}
    public Client getOwner() {
        return owner;
    }
    //SETTERS
    // public void setNextPayment() {
    //if(status.equals(abs.e_status.ACTIVE))

}

