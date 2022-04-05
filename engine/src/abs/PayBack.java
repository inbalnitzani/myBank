package abs;

public class PayBack {

    private Client givesALoan;
    private double percentage;
    private double originalAmount;

    public PayBack(Client client, double originalAmount,int investorAmount) {
        this.givesALoan = client;
        this.percentage = investorAmount / originalAmount;
        this.originalAmount = originalAmount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public Client getClientDTOGivers() {
        return givesALoan;
    }

    public double getPercentage() {
        return percentage;

    }



}
