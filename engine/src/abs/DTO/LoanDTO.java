package abs.DTO;

import abs.Client;
import abs.Loan;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int originalAmount, amountPaidBack, amountCollectedPending;
    private int interestRate;
    private int status;
    private int startingTimeUnit, endingTimeUnit, pace, nextPayment;
    //private List<Client> givers;
    //private List <Payment> payments;

    //CTOR
    public LoanDTO(Loan loan) {
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

    public int getStatus(){
       return this.status;
     };
    public String getCategory() {
        return this.category;
    }

    ;

    public int getOriginalAmount() {
        return this.originalAmount;
    }

    ;

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }

    ;

    public int getAmountCollectedPending() {
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
    public String getOwner() {
        return owner;
    }
    //SETTERS
    // public void setNextPayment() {
    //if(status.equals(abs.e_status.ACTIVE))

}

