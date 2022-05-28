package dto;

import loan.Payment;

public class PaymentDTO {

    private double fund;
    private double interestPart;
    private double amount,originalAmaount;
    private int actualPaidTime;
    private boolean paidAPartOfDebt;

    public PaymentDTO(Payment payment) {
        amount = payment.getAmount();
        fund = amount/(1.0+payment.getPercentage());
        interestPart=amount-fund;
        actualPaidTime=payment.getActualPaidTime();
        originalAmaount = payment.getOriginalAmaount();
        paidAPartOfDebt = payment.getPaidAPartOfDebt();
    }

    public double getOriginalAmaount() {
        return originalAmaount;
    }

    public int getActualPaidTime() {
        return actualPaidTime;
    }

    public double getFund() {
        return fund;
    }

    public double getAmount() {
        return amount;
    }

    public double getInterestPart() {
        return interestPart;
    }
    public boolean isPaid(){
        if(actualPaidTime!= 0) return true;
         else return false;
    }

    public boolean getPaidAPartOfDebt() {
        return paidAPartOfDebt;
    }
}
