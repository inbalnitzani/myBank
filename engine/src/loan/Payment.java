package loan;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount;
    private double interesetByPercentage;
    private String loanID;
    private int actualPaidTime = 0;

    public int getActualPaidTime() {
        return actualPaidTime;
    }

    public void addToAmount(double amount) {
        this.amount += amount;

    }

    public Payment(String loanID, double fund, double percentage) {
        this.loanID = loanID;
        this.interesetByPercentage = percentage;
        this.amount = fund * (percentage + 1);
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

    public void setActualPaidTime(int actualPaidTime) {
        this.actualPaidTime = actualPaidTime;
    }
}
