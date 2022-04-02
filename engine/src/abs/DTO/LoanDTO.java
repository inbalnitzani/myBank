package abs.DTO;

import abs.*;

import java.util.List;
import java.util.Map;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate, pace;
    private Status status;
    private int totalYazTime, activeTime;
    private List<PayBackDTO> payBacks;
    private Map<Integer, PaymentDTO> payments;


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
    public Map<Integer,PaymentDTO> getPayments(){return payments;}
    public int getActiveTime() {
        return activeTime;
    }
public double getTotalMoneyForPayingBack() {
    double precentage = interestRate / 100;
    return (precentage + 1) * capital;
}
    public double getTotalAmountPerPayment(){
        int fundPer1Payment=capital/pace;
        double precentage=interestRate/100;

        return fundPer1Payment*interestRate;
    }
    public int getNextPaymentTime() {
        boolean findNextPayment = false;
        int nextPayment = Globals.worldTime + 1;
        while (!findNextPayment) {
            if (payments.containsKey(nextPayment)) {
                findNextPayment = true;
            } else nextPayment++;
        }
        return nextPayment;
    }

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
public int getFirstPaymentTime() {
    int firstPayment = activeTime;
    boolean findFirstPayment = false;
    while (!findFirstPayment) {
        if (payments.containsKey(firstPayment))
            findFirstPayment = true;
        else firstPayment++;
    }
    return firstPayment;
}
    public int getLastPaymentTime() {
        int lastPayment = activeTime + totalYazTime;
        return payments.get(lastPayment).getActualPaymentTime();
    }
    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }


    public int getAmountCollected() {
        return this.amountCollectedPending;
    }

    public List<PayBackDTO> getPayBacks() {
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

