package abs;

import abs.DTO.LoanDTO;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class Loan {

    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int originalAmount, amountPaidBack, amountCollectedPending;
    private int interestRate;
    private Status status;
    private int startingTimeUnit, endingTimeUnit, pace, nextPayment;
    private List<Client> givers;
    private List<Payment> payments;

    //CTOR
    public Loan(String id,String owner, int amount, int rate, String categoryName) {
        this.owner = owner;
        this.originalAmount = amount;
        this.interestRate = rate;
        this.category=categoryName;
        this.status =new NEWLOAN();
    }

    public Loan(LoanDTO loanDTO){
      new Loan(loanDTO.getLoansID(),loanDTO.getOwner(),loanDTO.getOriginalAmount(),loanDTO.getInterestRate(),loanDTO.getCategory());
    }

    //GETTERS
    public String getLoansID() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getCategory() {
        return this.category;
    }

    public int getOriginalAmount() {
        return this.originalAmount;
    }

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }

    public int getAmountCollectedPending() {
        return this.amountCollectedPending;
    }

    public int getInterestRate() {
        return this.interestRate;
    }

    public int getStartingTimeUnit() {
        return this.startingTimeUnit;
    }

    public int getEndingTimeUnit() {
        return this.endingTimeUnit;
    }

    public int getPace() {
        return this.pace;
    }

    public Collection<Client> getGivers() {
        return this.givers;
    }

    public Collection<Payment> getPayments() {
        return this.payments;
    }

    public int getTotalTU() {
        return this.endingTimeUnit - this.startingTimeUnit;
    }

    public int getNextPayment() {
        return nextPayment;
    }

    public String getOwner() {
        return owner;
    }

    //SETTERS
    public void setNextPayment() {
        //if(status.equals(abs.e_status.ACTIVE))
    }

    public int getLeftAmountToInvest() {
        return originalAmount - amountCollectedPending;
    }

    public Status addNewInvestor(Client client, int newAmountForLoan) {
        givers.add(client);
        amountCollectedPending+=newAmountForLoan;
        if(amountCollectedPending==originalAmount)
            status=Status.ACTIVE;
        return status;
    }

    public void addMoney(){
        if (status.getClass()==NEWLOAN.class)
            status=new
    }

}
