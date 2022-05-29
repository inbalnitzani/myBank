package dto;

import loan.Payment;

public class PaymentDTO {

    private double fund;
    private double interestPart;
    private double amount, originalAmount;
    private int actualPaidTime;
    private boolean paidAPartOfDebt;
    private int riskPayTime;

    public PaymentDTO(Payment payment) {
        amount = payment.getAmount();
        fund = amount/(1+payment.getPercentage()/100.0);
        interestPart=amount-fund;
        actualPaidTime=payment.getActualPaidTime();
        originalAmount = payment.getOriginalAmount();
        paidAPartOfDebt = payment.getPaidAPartOfDebt();
        this.riskPayTime=payment.getRiskPayTime();
    }

    public void setRiskPayTime(int riskPayTime) {
        this.riskPayTime = riskPayTime;
    }
    public int getRiskPayTime() {
        return riskPayTime;
    }
    public double getOriginalAmount() {
        return originalAmount;
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
