package dto;

import loan.Loan;
import loan.PayBack;
import loan.Payment;
import loan.Status;
import engine.Global;

import java.text.DecimalFormat;
import java.util.*;

public class LoanDTO {
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
    private Boolean listedForSale;

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
        this.listedForSale = loan.getListedForSale();
        setPayBacks(loan.getPayBacks());
        setPayments(loan.getPayments());
    }

    // PRIVATE SETTERS
    private void setPayBacks(List<PayBack> payBack) {
        payBacks = new ArrayList<>();
        for (PayBack pay : payBack) {
            payBacks.add(new PayBackDTO(pay));
        }
    }

    public double sumMissingPayments(int globalTime) {
        double amountNextPayment = payments.get(getNextPaymentTime(globalTime)).getAmount();
        return (amountNextPayment - getTotalAmountPerPayment()) / getTotalAmountPerPayment();
    }

    private void setPayments(Map<Integer, Payment> paymentsToCheck) {
        this.payments = new HashMap<>();
        int size =paymentsToCheck.size();
        int i=1, paymentAdd=0;
        if (size != 0) {
            while (paymentAdd<size)
            {
                Payment payment=paymentsToCheck.get(i);
                if (payment!=null){
                    payments.put(i,new PaymentDTO(payment));
                    paymentAdd++;
                }
                i++;
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

    public int getNextPaymentTime(int worldTime) {
        boolean findNextPayment = false;
        int nextPayment = worldTime;
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
        List<PaymentDTO> paid =getPaidPayment();
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

    public double getNextPaymentAmount(int globalTime){
        PaymentDTO payment = payments.get(getNextPaymentTime(globalTime));
        return payment.getOriginalAmount()-payment.getAmount();
    }

    public List<PaymentDTO> getPaidPayment() {
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for (PaymentDTO paymentDTO : payments.values()) {
            if (paymentDTO.isPaid() || paymentDTO.getPaidAPartOfDebt() ||paymentDTO.getRiskPayTime()!=0)
                paymentDTOList.add(paymentDTO);
        }
        return paymentDTOList;
    }

    public int getFirstPaymentTime() {
        int firstPayment = activeTime + 1;
        boolean findFirstPayment = false;
        while (!findFirstPayment) {
            PaymentDTO payment = payments.get(firstPayment);
            if (payment != null){
                findFirstPayment = true;
                if (payment.isPaid()) {
                    firstPayment = payment.getActualPaidTime();
                }
            }
            else {
                firstPayment++;
            }
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

    public String getStatusInfo(int globalTime) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        double fundPayBack = 0, interestPayBack = 0;
        int time=0;
        String paymentsStr = "Payments:\n";
        List<PaymentDTO> paid=getPaidPayment();
        for (PaymentDTO payment:paid) {
            if(!payment.isPaid()&&payment.getPaidAPartOfDebt()) {
                time  =payment.getRiskPayTime();
            }else {
                time=payment.getActualPaidTime();
            }
            double fund = payment.getFund();
            double interest = payment.getInterestPart();
            paymentsStr += "\nPayment time: " + time
                    + "\nfund: " + df.format(fund)
                    + "\ninterest: " + df.format(interest) + " ."
                    + "\nTotal amount paid: " + df.format(payment.getAmount())+"\n";
            fundPayBack += fund;
            interestPayBack += interest;
        }
        double originalFundAmount = getOriginalAmount();
        double interestLeftToPay = getTotalMoneyForPayingBack() - originalFundAmount - interestPayBack;
        double fundLeftToPay = originalFundAmount - fundPayBack;
        paymentsStr+= "Total fund paid back: " + df.format(fundPayBack)
                + "\n Total interest paid back: " + df.format(interestPayBack) + "."
                + "\nFund amount left to pay: " + df.format(fundLeftToPay)
                + "\nInterest amount left to pay: " + df.format(interestLeftToPay) + " .";

        String str ="Capital: "+capital+ "\nInvestors:\n";
        for (PayBackDTO payBack:payBacks)
            str+= payBack.getGiversName() +" - "+ payBack.getAmountInvested()+'\n';
        if (Status.PENDING.equals(status))
            return "Pending:\namount colected: "+amountCollectedPending +
                    "\namount left to activate: " + (getOriginalAmount() - amountCollectedPending)
                    +"\n"+str;

        else if (Status.ACTIVE.equals(status)){
            return "ACTIVE:\ntime activated: " +activeTime+
                    "\nNext payment yaz is: " + (getNextPaymentTime(globalTime))+
                    "\nfor a total of: " + getNextPaymentAmount(globalTime)+
                    "\n" + str + "\n"+ paymentsStr;
        }
        else if (Status.RISK.equals(status))
            return "RISK:\ntime activated: " +activeTime+
                    "\nNext payment yaz is: " + (getNextPaymentTime(globalTime))+
                    "\n"+getUnPaidPayment().size() + "Payments have not been paid" +
                    "\n for a total of: " + (getNextPaymentAmount(globalTime))
                    +"\n" + paymentsStr;
        else if (Status.FINISHED.equals(status))
            return "Finished: Time activated: " + getActiveTime() +
                    "Time finished: " + getLastPaymentTime()+str;
        else if (Status.NEW.equals(status)) return "New";
        return null;
    }

    public Boolean getListedForSale() {
        return listedForSale;
    }
}

