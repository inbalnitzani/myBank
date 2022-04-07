package dto;

import loan.Payment;

public class PaymentDTO {

    private double fund;
    private double interestPart;
    private double amount;
    private String id;
    private int actualPaidTime;

    public PaymentDTO(Payment payment) {
        amount = payment.getAmount();
        interestPart=amount-fund;
        fund = amount/(1+payment.getPercentage());
        id = payment.getLoanID();
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
}