package abs;

public class Payment {
    private double amount, fund, percentage;
    private int payingActualTime;
    private String loanID;

    public Payment(double fund, double percentage) {
        this.fund = fund;
        this.percentage = percentage;
        this.amount = (1+percentage)*fund;
    }

    public double getAmount(){
        return amount;
    }
    public void addToAmount(double amount){
        this.amount+=amount;
    }
    public Payment(int expectedTime,String loanID) {
        payingActualTime =expectedTime;
        this.loanID = loanID;
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
