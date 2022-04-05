package abs;

import abs.DTO.LoanDTO;
import java.util.*;

public class Loan {

    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack = 0, amountCollectedPending;
    private int interestRate, pace;
    private Status status = Status.NEW;
    private int totalYazTime, activeTime;
    private List<PayBack> payBacks;
    private Map<Integer, Payment> payments;


    //CTOR
    public Loan(String id, String owner, int amount, int rate, String categoryName, int totalYazTime, int pace) {
        this.id = id;
        this.owner = owner;
        this.capital = amount;
        this.interestRate = rate;
        this.category = categoryName;
        this.totalYazTime = totalYazTime;
        this.pace = pace;
        payBacks = new ArrayList<PayBack>();
    }

    public int getActiveTime() {
        return activeTime;
    }

    public Loan(LoanDTO loanDTO) {
        new Loan(loanDTO.getLoansID(), loanDTO.getOwner(), loanDTO.getOriginalAmount(), loanDTO.getInterestRate(), loanDTO.getCategory(), loanDTO.getTotalYazTime(), loanDTO.getPace());
    }

    public Map<Integer, Payment> getPayments() {
        return payments;
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

    public int getCapital() {
        return this.capital;
    }

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }
    public void setAmountPaidBack(double amountToAdd){amountPaidBack += amountToAdd;}

    public int getAmountCollectedPending() {
        return this.amountCollectedPending;
    }

    public int getInterestRate() {
        return this.interestRate;
    }

    public int getPace() {
        return this.pace;
    }

    public List<PayBack> getPayBacks() {
        return payBacks;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public void changeToActive() {
        payments = new HashMap<>();
        int lastPayment = Globals.worldTime + totalYazTime;
        for (int i = Globals.worldTime+pace; i < lastPayment; i += pace) {
            payments.put(i, new Payment(i,id));
        }
        activeTime=Globals.worldTime;
    }

    public int getNextTimePayment() {
        return 0;
    }

    public String getOwner() {
        return owner;
    }

    public void setNextPayment() {
        //if(status.equals(abs.e_status.ACTIVE))
    }

    public int getLeftAmountToInvest() {
        return capital - amountCollectedPending;
    }

    public Status addNewInvestor(Client client, int newAmountForLoan) {
        createNewGiver(client, newAmountForLoan);
        amountCollectedPending += newAmountForLoan;
        if (amountCollectedPending == capital) {
            status = Status.ACTIVE;
            changeToActive();
        }
        return status;
    }

    public void createNewGiver(Client client, int investorAmount) {
        payBacks.add(new PayBack(client,this.capital,investorAmount));
    }

    public void setStatus(Status status){this.status = status;}
}
