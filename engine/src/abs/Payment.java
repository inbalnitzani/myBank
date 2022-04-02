package abs;

public class Payment {
    private double fund;
    private double percentage;
    private int payingExpectedTime;

    public Payment(int expectedTime) {
        payingExpectedTime=expectedTime;
    }

    public Payment(int ut, double fund, double percentage) {
        this.fund = fund;
        this.percentage = percentage;
    }
}
