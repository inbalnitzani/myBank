package loan;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount;
    private double fund;
    private double percentage;
    private boolean paid=false;
    private String loanID;

    public void addToAmount(double amount) {
        this.amount += amount;
    }

    public Payment(String loanID, double fund, double percentage) {
        this.loanID = loanID;
        this.fund = fund;
        this.percentage = percentage;
        this.amount = fund * (percentage + 1);
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getFund() {
        return fund;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getLoanID() {
        return loanID;
    }

    public double getAmount() {
        return amount;
    }
}
