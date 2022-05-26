package exception;

public class NotEnoughMoney extends Exception {
    private double amount;

    public NotEnoughMoney(double _amount) {
        this.amount = _amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString(){
        return "Current balance is: "+amount+".\nPlease enter amount lower than +"+amount;
    }
}
