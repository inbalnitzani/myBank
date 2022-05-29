package loan;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount, originalAmount;
    private double precentageInteger;
    private String loanID;
    private int actualPaidTime = 0;
    private boolean paidAPartOfDebt = false;
    private int riskPayTime=0;

    public Payment(String loanID, double amountIncludeInterest, double percentageInteger) {
        this.loanID = loanID;
        this.precentageInteger = percentageInteger;
        this.amount = 0 ;
        this.originalAmount = amountIncludeInterest;
    }


    public int getRiskPayTime() {
        return riskPayTime;
    }
    public void setRiskPayTime(int riskPayTime) {
        this.riskPayTime = riskPayTime;
    }
    public int getActualPaidTime() {
        return actualPaidTime;
    }
    public void addToOriginalAmount(double amount) {
        this.originalAmount += amount;
    }
    public boolean isPaid(){
        if(actualPaidTime!= 0) return true;
         else return false;
    }
    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }
    public void setAmount(double amount){
        this.amount+= amount;
    }
    public double getOriginalAmount() {
        return originalAmount;
    }
    public boolean getPaidAPartOfDebt() {
        return paidAPartOfDebt;
    }
    public double getPercentage() {
        return precentageInteger;
    }
    public String getLoanID() {
        return loanID;
    }
    public double getAmount() {
        return amount;
    }
    public void setPaidAPartOfDebt(boolean paidAPartOfDebt) {
        this.paidAPartOfDebt = paidAPartOfDebt;
    }
    public void setActualPaidTime(int actualPaidTime) {
        this.actualPaidTime = actualPaidTime;
    }
}
