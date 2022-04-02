package abs;

import abs.DTO.LoanDTO;
import abs.DTO.PaymentDTO;
import com.sun.javaws.Globals;
//import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Loan {

    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate,pace;
    private Status status;
    private int totalYazTime,activeTime;
    private List<PayBack> payBacks;
    private Map<Integer, Payment> payments;

    //CTOR
    public Loan(String id,String owner, int amount, int rate, String categoryName,int totalYazTime,int pace) {
        this.id = id;
        this.owner = owner;
        this.capital = amount;
        this.interestRate = rate;
        this.category = categoryName;
        this.totalYazTime = totalYazTime;
        this.pace = pace;
        payBacks = new ArrayList<PayBack>();
//        this.givers=new HashMap<Client,Integer>();
    }
    public int getActiveTime(){return activeTime;}
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
//
//    public Map<Client,Integer> getGivers() {
//        return this.givers;
//    }
//
//    public Collection<Payment> getPayments() {
//        return this.payments;
//    }

    public List<PayBack> getPayBacks(){return payBacks;}
    public int getTotalYazTime() {
        return totalYazTime;
    }

    public void changeToActive(int worldTime) {
        payments = new HashMap<>();
        int lastPayment = worldTime + totalYazTime;
        for (int i = worldTime; i < lastPayment; i += pace) {
            payments.put(i, new Payment(i));
        }
    }
    public int getNextTimePayment() {
        return 0;
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

    public Status addNewInvestor(Client client, int newAmountForLoan, int worldTime) {
        createNewGiver(client, newAmountForLoan);
        amountCollectedPending += newAmountForLoan;
        if (amountCollectedPending == capital) {
            status = Status.ACTIVE;
            changeToActive(worldTime);
        }
        return status;
    }
    public void createNewGiver(Client client,int amount){
        PayBack payBack=new PayBack();
        payBack.setGivesALoan(client);
        payBack.setOriginalAmount(amount);
        payBacks.add(payBack);
    }

 }
