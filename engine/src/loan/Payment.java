package loan;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount,originalAmaount;
    private double interesetByPercentage;
    private String loanID;
    private int actualPaidTime = 0;
    private boolean paidAPartOfDebt = false;

    public int getActualPaidTime() {
        return actualPaidTime;
    }

    public void addToAmount(double amount) {
        this.amount += amount;
        this.originalAmaount += originalAmaount;

    }
    public boolean isPaid(){
        if(actualPaidTime!= 0) return true;
         else return false;

    }

    public void setOriginalAmaount(double originalAmaount) {
        this.originalAmaount = originalAmaount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public Payment(String loanID, double fund, double percentage) {
        this.loanID = loanID;
        this.interesetByPercentage = percentage;
        this.amount = fund * (percentage + 1);
        originalAmaount = amount;
    }

    public double getOriginalAmaount() {
        return originalAmaount;
    }
    public boolean getPaidAPartOfDebt() {
        return paidAPartOfDebt;
    }

    public double getPercentage() {
        return interesetByPercentage;
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
