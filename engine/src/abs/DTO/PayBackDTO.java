package abs.DTO;

import abs.PayBack;

public class PayBackDTO {

    private ClientDTO givesALoan;
    private int originalAmount;

    public PayBackDTO(PayBack payBack) {
        originalAmount = payBack.getOriginalAmount();
        givesALoan = new ClientDTO(payBack.getClientDTOGivers());
    }

    public void setGivesALoan(ClientDTO client) {
        givesALoan = client;
    }

    public void setOriginalAmount(int amount) {
        originalAmount = amount;
    }

    public ClientDTO getGivesALoan() {
        return givesALoan;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }
}
