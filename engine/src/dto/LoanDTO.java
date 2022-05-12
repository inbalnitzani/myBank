package dto;

import loan.Loan;
import loan.PayBack;
import loan.Payment;
import loan.Status;
import bank.Global;

import java.util.*;

public class LoanDTO {
    //DATA MEMBERS
    private String id;
    private String owner;
    private String category;
    private int capital, amountPaidBack, amountCollectedPending;
    private int interestRate, pace;
    private Status status;
    private int totalYazTime, activeTime, lastRiskTime;
    private List<PayBackDTO> payBacks;
    private Map<Integer, PaymentDTO> payments;
private String MoreInfo="hi";
    //CTOR
    public LoanDTO(Loan loan) {
        this.id = loan.getLoansID();
        this.owner = loan.getOwner();
        this.category = loan.getCategory();
        this.capital = loan.getCapital();
        this.amountCollectedPending = loan.getAmountCollectedPending();
        this.interestRate = loan.getInterestRate();
        this.pace = loan.getPace();
        this.status = loan.getStatus();
        this.totalYazTime = loan.getTotalYazTime();
        this.activeTime = loan.getActiveTime();
        this.amountPaidBack = loan.getAmountPaidBack();
        this.lastRiskTime =loan.getLastRiskTime();
        setPayBacks(loan.getPayBacks());
        setPayments(loan.getPayments(), loan.getFirstPaymentTime(), loan.getLastPaymentTime());
    }


    // PRIVATE SETTERS
    private void setPayBacks(List<PayBack> payBack) {
        payBacks = new ArrayList<>();
        for (PayBack pay : payBack) {
            payBacks.add(new PayBackDTO(pay));
        }
    }

    public double sumMissingPayments() {
        double amountNextPayment = payments.get(getNextPaymentTime()).getAmount();
        return (amountNextPayment - getTotalAmountPerPayment()) / getTotalAmountPerPayment();
    }

    private void setPayments(Map<Integer, Payment> payments, int firstPayTime, int lastPayTime) {
        this.payments = new HashMap<>();
        if (payments.size() != 0) {
            for (int i = firstPayTime; i <= lastPayTime; i += pace) {
                this.payments.put(i, new PaymentDTO(payments.get(i)));
            }
        }
    }

    //GETTERS
    public Map<Integer, PaymentDTO> getPayments() {
        return payments;
    }

    public int getCapital() {
        return capital;
    }

    public String getId() {
        return id;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public int getLastRiskTime() {
        return lastRiskTime;
    }

    public double getTotalMoneyForPayingBack() {
        double precentage = interestRate / 100.0;
        return (precentage + 1) * capital;
    }

    public double getTotalAmountPerPayment() {
        return getTotalMoneyForPayingBack() / (totalYazTime / pace);
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
        int firstPayment = activeTime + 1;
        boolean findFirstPayment = false;
        while (!findFirstPayment) {
            if (payments.containsKey(firstPayment))
                findFirstPayment = true;
            else firstPayment++;
        }
        return firstPayment;
    }

    public int getLastPaymentTime() {
        return activeTime + totalYazTime;
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

    public String getOwner() {
        return owner;
    }

    public String getInfo() {
        return "More Info";
    }
}

