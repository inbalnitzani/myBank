package abs;

import java.io.Serializable;

public class Payment implements Serializable {
    private double amount, fund, percentage;
    private int payingActualTime;
    private String loanID;

    public double getAmount(){
        return amount;
    }
    public void addToAmount(double amount){
        this.amount+=amount;
    }
    public Payment(int expectedTime,String loanID,double amount) {
        payingActualTime =expectedTime;
        this.loanID = loanID;
        this.amount = amount;
    }
    public double getFund() {
        return fund;
    }
    public double getPercentage() {
        return percentage;
    }
    public int getPayingActualTime() {
        return payingActualTime;
    }
    public String getLoanID(){return loanID;}

}
