package abs.DTO;

import abs.Client;
import abs.Loan;
import abs.Payment;
import abs.Status;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate;
    private Status status;
    private int totalYazTime, pace, nextPayment,intristPerPayment;
    private Map<String,Integer> givers;
    private List <Payment> payments;


    //CTOR
    public LoanDTO(Loan loan) {
        this.owner = loan.getOwner();
        this.id = loan.getLoansID();
        this.category = loan.getCategory();
        this.capital = loan.getCapital();
        this.totalYazTime = loan.getTotalYazTime();
        this.pace = loan.getPace();
        this.interestRate = loan.getInterestRate();
        this.totalYazTime = loan.getTotalYazTime();

    }
    //GETTERS
public void setGiverDto(Map<Client,Integer> givers){
        
}

    public String getLoansID() {
        return this.id;
    }


    ;

    public Status getStatus(){
       return this.status;
     };
    public String getCategory() {
        return this.category;
    }

    ;

    public int getOriginalAmount() {
        return this.capital;
    }

    ;

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }

    ;

    public int getAmountCollected() {
        return this.amountCollectedPending;
    }

    ;

    public int getInterestRate() {
        return this.interestRate;
    }

    ;

    public int getTotalYazTime() {
        return this.totalYazTime;
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

