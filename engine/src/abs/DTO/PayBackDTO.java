package abs.DTO;

public class PayBackDTO {

    private ClientDTO givesALoan;
    private int originalAmount;

    public void setGivesALoan(ClientDTO client){
        givesALoan=client;
    }
    public void setOriginalAmount(int amount){
        originalAmount=amount;
    }

    public ClientDTO getGivesALoan(){return givesALoan;}
    public int getOriginalAmount(){return originalAmount;}
}
