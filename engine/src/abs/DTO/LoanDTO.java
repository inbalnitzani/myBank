package abs.DTO;

import abs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate,pace;
    private Status status;
    private int totalYazTime, activeTime ;
    private List<PayBackDTO> payBacks;

//    private Map<String, Integer> givers;
    private Map<Integer,PaymentDTO> payments;


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
public int getActiveTime(){return activeTime;}

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
        return this.capital;
    }

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }


    public int getAmountCollected() {
        return this.amountCollectedPending;
    }
public List<PayBackDTO> getPayBacks(){
        return payBacks;
}

    public int getInterestRate() {
        return this.interestRate;
    }

    public int getTotalYazTime() {
        return this.totalYazTime;
    }

    public int getPace() {
        return this.pace;
    }

    //   public Collection<Client> getGivers(){
    //      return this.givers;
    // };
    //public Collection <Payment> getPayments(){
    //   return this.payments;
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

