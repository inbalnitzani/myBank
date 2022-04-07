package dto;

import loan.Payment;

public class PaymentDTO {

    private double fund;
    private double percentage;
    private double amount;
    private String id;
    private boolean paid=false;

    public PaymentDTO(Payment payment) {
        amount = payment.getAmount();
        percentage = payment.getPercentage();
        fund = payment.getFund();
        id = payment.getLoanID();
    }

    public boolean isPaid() {
        return paid;
    }

    public double getFund() {
        return fund;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getAmount() {
        return amount;
    }
}
