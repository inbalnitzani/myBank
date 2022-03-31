package abs;

import abs.DTO.LoanDTO;
//import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Loan {

    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate;
    private Status status;
    private int totalYazTime,pace, nextPayment,IntristPerPayment;
    private Map<Client,Integer> givers;
    private List<Payment> payments;

    //CTOR
    public Loan(String id,String owner, int amount, int rate, String categoryName,int totalYazTime,int pace) {
        this.id = id;
        this.owner = owner;
        this.capital = amount;
        this.interestRate = rate;
        this.category=categoryName;
        this.totalYazTime = totalYazTime;
        this.pace = pace;
    }

    public Loan(LoanDTO loanDTO){
      new Loan(loanDTO.getLoansID(),loanDTO.getOwner(),loanDTO.getOriginalAmount(),loanDTO.getInterestRate(),loanDTO.getCategory(),loanDTO.getTotalYazTime(),loanDTO.getPace());
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

    public int getCapital() {
        return this.capital;
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

    /*public int getStartingTimeUnit() {
        return this.startingTimeUnit;
    }

    public int getEndingTimeUnit() {
        return this.endingTimeUnit;
    }
*/
    public int getPace() {
        return this.pace;
    }

    public Map<Client,Integer> getGivers() {
        return this.givers;
    }

    public Collection<Payment> getPayments() {
        return this.payments;
    }

    public int getTotalYazTime() {
        return totalYazTime;
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
        return capital - amountCollectedPending;
    }

    public Status addNewInvestor(Client client, int newAmountForLoan) {
        givers.put(client,newAmountForLoan);
        amountCollectedPending+=newAmountForLoan;
        if(amountCollectedPending== capital)
            status=Status.ACTIVE;
        return status;
    }

 }
