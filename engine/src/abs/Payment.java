package abs;

public class Payment {
    private int unitTime;
    private double fund;
    private double percentage;

    public Payment(){

    }
public Payment(int ut, double fund, double percentage){
    this.unitTime = ut;
    this.fund = fund;
    this.percentage = percentage;
}
}
