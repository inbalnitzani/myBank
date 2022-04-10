package dto;

import loan.PayBack;

public class PayBackDTO {

    private String giversName;
    private double amountInvested;

    public PayBackDTO(PayBack payBack) {
        giversName = payBack.getClientDTOGivers();
        amountInvested = payBack.getAmountInvested();
    }

    public String getGiversName() {
        return giversName;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

}
