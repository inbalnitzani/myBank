package abs;

import java.io.Serializable;

public class PayBack implements Serializable {

    private String givesALoan;
    private double percentage;
    private double originalAmount;

    public PayBack(Client client, double originalAmount,int investorAmount) {
        this.givesALoan = client.getFullName();
        this.percentage = investorAmount / originalAmount;
        this.originalAmount = investorAmount;
    }

//    public PayBack(Client client, int originalAmount) {
//        this.givesALoan = client.getFullName();
//        this.originalAmount = originalAmount;
//    }
    public double getOriginalAmount() {
        return originalAmount;
    }

    public String getClientDTOGivers() {
        return givesALoan;
    }

    public double getPercentage() {
        return percentage;
    }

}
