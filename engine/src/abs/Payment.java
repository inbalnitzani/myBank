package abs;

public class Payment {
    private double fund;
    private double percentage;
    private int payingActualTime;

    public Payment(int expectedTime) {
        payingActualTime =expectedTime;
    }

    public Payment(double fund, double percentage) {
        this.fund = fund;
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getFund() {
        return fund;
    }

    public int getPayingActualTime() {
        return payingActualTime;
    }
}
