package loan;

import client.Client;
import engine.Global;

import java.io.Serializable;
import java.util.*;

public class Loan implements Serializable {

    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate, pace;
    private Status status = Status.NEW;
    private int totalYazTime, activeTime, lastRiskTime, actualLastPaymentTime=0;
    private List<PayBack> payBacks;
    private Map<Integer, Payment> payments;
    private Boolean listedForSale = false;


    public void setActualLastPaymentTime(int actualLastPaymentTime) {
        this.actualLastPaymentTime = actualLastPaymentTime;
    }

    public Boolean getListedForSale() {
        return listedForSale;
    }

    public void setListedForSale(Boolean listedForSale) {
        this.listedForSale = listedForSale;
    }

    public int getActualLastPaymentTime() {
        return actualLastPaymentTime;
    }

    //CTOR
    public Loan(String id, String owner, int amount, int rate, String categoryName, int totalYazTime, int pace) {
        this.id = id;
        this.owner = owner;
        this.capital = amount;
        this.interestRate = rate;
        this.category = categoryName;
        this.totalYazTime = totalYazTime;
        this.pace = pace;
        this.amountPaidBack = 0;
        this.lastRiskTime =0;
        payBacks = new ArrayList<PayBack>();
        payments = new HashMap<>();
    }

    public int getLastRiskTime() {
        return lastRiskTime;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public void setRiskTime(){
        if (status!=Status.RISK) {
            lastRiskTime = Global.worldTime;
            setStatus(Status.RISK);
        }
    }
    public Map<Integer, Payment> getPayments() {
        return payments;
    }


    public int getFirstPaymentTime() {
        int optionalTime = activeTime + 1;
        if (payments.size() == 0) {
            optionalTime = 0;
        } else {
            boolean timeFound = false;
            while (!timeFound) {
                if (payments.containsKey(optionalTime))
                    timeFound = true;
                else {
                    optionalTime++;
                }
            }
        }
        return optionalTime;
    }

    public int getLastPaymentTime() {
        return activeTime + totalYazTime;
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

    public void setAmountPaidBack(double amountToAdd) {
        amountPaidBack += amountToAdd;
    }

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
        int lastPayment = Global.worldTime + totalYazTime;
        double amountPerPayment = (((double) capital / (((double) totalYazTime / (double) pace)))*(1+interestRate/100.0));
        for (int i = Global.worldTime + pace; i <= lastPayment; i += pace) {
            payments.put(i, new Payment(id, amountPerPayment, interestRate));
        }
        activeTime = Global.worldTime;
    }

    public String getOwner() {
        return owner;
    }

    public int getLeftAmountToInvest() {
        return capital - amountCollectedPending;
    }

    public PayBack checkClientAlreadyInvested(Client client) {
        for (PayBack payBack : payBacks) {
            if (payBack.getClientDTOGivers().equals(client.getFullName())) {
                return payBack;
            }
        }
        return null;
    }

    public void updatePayBacks(Client client, double newAmountForLoan, PayBack payBack) {
        if (payBack != null) {
            payBack.setAmounts(newAmountForLoan, capital);
        } else {
            createNewGiver(client, newAmountForLoan);
        }
    }

    public double totalAmountToPayBack() {
        return capital * ((interestRate / 100.0) + 1);
    }

    public Status addNewInvestorToLoan(Client client, double newAmountForLoan, PayBack payBack) {
        updatePayBacks(client, newAmountForLoan, payBack);
        this.amountCollectedPending += newAmountForLoan;
        if (amountCollectedPending == capital) {
            status = Status.ACTIVE;
            changeToActive();
        } else {
            status = Status.PENDING;
        }
        return status;
    }

    public void createNewGiver(Client client, double investorAmount) {
        payBacks.add(new PayBack(client, this.capital, investorAmount));
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Payment getNextPayment() {
        boolean findNextPayment = false;
        int nextPayment = Global.worldTime + 1;
        while (!findNextPayment) {
            if (payments.containsKey(nextPayment)) {
                findNextPayment = true;
            } else nextPayment++;
        }
        return payments.get(nextPayment);
    }
    public int getNextPaymentTime() {
        boolean findNextPayment = false;
        int nextPayment = Global.worldTime + 1;
        while (!findNextPayment) {
            if (payments.containsKey(nextPayment)) {
                findNextPayment = true;
            } else nextPayment++;
        }
        return nextPayment;
    }


}
