package abs.DTO;

import abs.Payment;

public class PaymentDTO {

    private double fund;
    private double percentage;
    private double totalAmount;
    private int actualPaymentTime;

    public PaymentDTO(Payment payment) {
        fund = payment.getFund();
        percentage = payment.getPercentage();
        actualPaymentTime = payment.getPayingActualTime();
    }
    public double getFund() {
        return fund;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getActualPaymentTime() {
        return actualPaymentTime;
    }
}
