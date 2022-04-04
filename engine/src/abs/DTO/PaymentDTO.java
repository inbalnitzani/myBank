package abs.DTO;

import abs.Payment;

public class PaymentDTO {

    private double fund;
    private double precentage;
    private int actualPaymentTime;

    public PaymentDTO(Payment payment) {
        fund = payment.getFund();
        precentage = payment.getPercentage();
        actualPaymentTime = payment.getPayingActualTime();
    }
    public double getFund() {
        return fund;
    }

    public double getPrecentage() {
        return precentage;
    }

    public int getActualPaymentTime() {
        return actualPaymentTime;
    }
}
