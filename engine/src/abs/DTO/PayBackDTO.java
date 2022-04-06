package abs.DTO;

import abs.PayBack;

public class PayBackDTO {

    private String giversName;
    private double originalAmount;

    public PayBackDTO(PayBack payBack) {
        originalAmount = payBack.getOriginalAmount();
        giversName = payBack.getClientDTOGivers();
    }

    public void setGiversName(ClientDTO client) {
        giversName = client.getFullName();
    }

    public void setOriginalAmount(int amount) {
        originalAmount = amount;
    }

    public String getGiversName() {
        return giversName;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }
}
