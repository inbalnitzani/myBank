package dto;

import loan.PayBack;

public class PayBackDTO {

    private String giversName;
    private double percentage;
private double amountInvested;
    public PayBackDTO(PayBack payBack) {
        percentage=payBack.getPercentage();
        giversName = payBack.getClientDTOGivers();
        amountInvested=payBack.getAmountInvested();
    }

    public String getGiversName() {
        return giversName;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public double getPercentage() {
        return percentage;
    }
}
