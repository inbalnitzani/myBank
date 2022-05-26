package dto;

import loan.Payment;

public class PaymentDTO {

    private double fund;
    private double interestPart;
    private double amount;
    private int actualPaidTime;

    public PaymentDTO(Payment payment) {
        amount = payment.getAmount();
        fund = amount/(1.0+payment.getPercentage());
        interestPart=amount-fund;
        actualPaidTime=payment.getActualPaidTime();
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
}
