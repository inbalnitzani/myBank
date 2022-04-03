package abs;

public class PayBack {

    private Client givesALoan;
    private int originalAmount;
public PayBack(Client client, int originalAmount) {
    this.givesALoan = client;
    this.originalAmount = originalAmount;
}
    public int getOriginalAmount() {
        return originalAmount;
    }

    public Client getClientDTOGivers() {
        return givesALoan;
    }
    public void setGivesALoan(Client client){
        givesALoan=client;
    }

    public void setOriginalAmount(int amount){
        originalAmount=amount;
    }
}
