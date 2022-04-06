package abs.DTO;

import abs.PayBack;

public class PayBackDTO {

    private String giversName;
    private double percentage;
    private double originalAmount;

    public PayBackDTO(PayBack payBack) {
        originalAmount = payBack.getOriginalAmount();
        percentage=payBack.getPercentage();
        giversName = payBack.getClientDTOGivers();
    }

    public String getGiversName() {
        return giversName;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getPercentage() {
        return percentage;
    }
}
