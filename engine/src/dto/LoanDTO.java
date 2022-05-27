package dto;

import loan.Loan;
import loan.PayBack;
import loan.Payment;
import loan.Status;
import bank.Global;

import java.util.*;
import java.util.stream.Collectors;

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
    private double finalAmount;
    private double interestLeftToPay;
    private double fundLeftToPay;
    private double fundPaid;
    private double interestPaid;

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
        this.finalAmount=capital*(interestRate/100+1);
        setPayBacks(loan.getPayBacks());
        int lastPay= loan.getActualLastPaymentTime();
        if(lastPay==0) {
            lastPay= loan.getLastPaymentTime();}
        setPayments(loan.getPayments(), loan.getFirstPaymentTime(), lastPay);
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

    public int getMissingMoneyPaymentTimes() {
        int unPaidTimes = 0;
        for (int i = lastRiskTime; i <= Global.worldTime; i += pace) {
            if (payments.get(i).getActualPaidTime() == 0) {
                unPaidTimes++;
            }
        }
        return unPaidTimes;
    }

    public double getFinalAmount(){
        return finalAmount;
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
        int nextPayment = Global.worldTime;
        while (!findNextPayment) {
            PaymentDTO paymentDTO = payments.get(nextPayment);
            if (paymentDTO != null) {
                if (!paymentDTO.isPaid()) {
                    findNextPayment = true;
                } else {
                    nextPayment++;
                }
            } else {
                nextPayment++;
            }
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

      public int getLastPaymentTime() {
        return activeTime + totalYazTime;
    }

    public int getAmountPaidBack() {
        return this.amountPaidBack;
    }

    public int getAmountCollectedPending() {
        return amountCollectedPending;
    }

    public double getFundLeftToPay() {
        return fundLeftToPay;
    }

    public double getFundPaid() {
        return fundPaid;
    }

    public double getInterestLeftToPay() {
        return interestLeftToPay;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public void calculateInfo(){
        List<PaymentDTO> paid = this.payments.values().stream()
                .filter(payment -> payment.getActualPaidTime() != 0).collect(Collectors.toList());

        this.fundPaid=0;
        this.interestPaid=0;
        if (!paid.isEmpty()) {
            for (PaymentDTO payment : paid)
            {
                double fund = payment.getFund();
                double interest = payment.getInterestPart();
                fundPaid += fund;
                interestPaid += interest;
            }
        }
        this.interestLeftToPay = getTotalMoneyForPayingBack() - capital - interestPaid;
        this.fundLeftToPay = capital - fundPaid;
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

    public double getNextPaymentAmount(){
        return payments.get(getNextPaymentTime()).getAmount();
    }

    public List<PaymentDTO> getPaidPayment(){
        int size = payments.size();
        List<PaymentDTO> paidPayments=new ArrayList<>();
        for (int i = getFirstPaymentTime() ; i < size ; i+=pace) {
            PaymentDTO payment=payments.get(i);
            if(payment.isPaid())
                paidPayments.add(payment);
        }
        return paidPayments;
    }

    public int getFirstPaymentTime() {
        int firstPayment = activeTime + 1;
        boolean findFirstPayment = false;
        while (!findFirstPayment) {
            PaymentDTO payment = payments.get(firstPayment);
            if (payment != null)
                if (payment.isPaid()) {
                    firstPayment = payment.getActualPaidTime();
                    findFirstPayment = true;
                } else firstPayment++;
        }
        return firstPayment;
    }

    public List<Integer> getUnPaidPayment(){
        List<Integer> unPaidTimes=new ArrayList<>();
        for (int i =lastRiskTime ; i <= Global.worldTime; i += pace) {
            if (!payments.get(i).isPaid()) {
                unPaidTimes.add(i);
            }
        }
        return unPaidTimes;
    }

    public String getStatusInfo() {

        if (Status.PENDING.equals(status))
            return "Pending: The amount left to activate loan is: " +
                    (getOriginalAmount() - getAmountCollectedPending());
        else if (Status.ACTIVE.equals(status))
            return "Active: Next payment yaz is: " + (getNextPaymentTime())+
                    "\nfor a total of: " + getNextPaymentAmount();
        else if (Status.RISK.equals(status))
            return "Risk: " + getUnPaidPayment().size() +
                    " Payments have not been paid" +
                    "\n for a total of: " + (getNextPaymentAmount());
        else if (Status.FINISHED.equals(status))
            return "Finished: Time activated: " + getActiveTime() +
                    "Time finished: " + getLastPaymentTime();
        else if (Status.NEW.equals(status)) return "New";
        return null;
    }
}

