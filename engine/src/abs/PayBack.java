package abs;

public class PayBack {

    private Client givesALoan;
<<<<<<< HEAD
    private double percentage;
    private double originalAmount;
public PayBack(Client client, double originalAmount,int investorAmount) {
    this.givesALoan = client;
    this.percentage = investorAmount/originalAmount;
    this.originalAmount = originalAmount;
}
=======
    private int originalAmount;

    public PayBack(Client client, int originalAmount) {
        this.givesALoan = client;
        this.originalAmount = originalAmount;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

>>>>>>> main
    public Client getClientDTOGivers() {
        return givesALoan;
    }

    public void setGivesALoan(Client client) {
        givesALoan = client;
    }
<<<<<<< HEAD
    public double getPercentage(){
    return percentage;
=======

    public void setOriginalAmount(int amount) {
        originalAmount = amount;
>>>>>>> main
    }
    public double getOriginalAmount(){
    return originalAmount;
    }


}
