package loan;

import client.Client;

import java.io.Serializable;

public class PayBack implements Serializable {

    private String givesALoan;
    private double percentage;
    private double anountInvested;

    public PayBack(Client client, double originalAmount, double investorAmount) {
        this.givesALoan = client.getFullName();
        this.percentage = investorAmount / originalAmount;
        this.anountInvested = investorAmount;
    }

    public double getAmountInvested() {
        return anountInvested;
    }

    public String getClientDTOGivers() {
        return givesALoan;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setAmounts(double amountToAdd, int originalLoanAmount) {
        anountInvested += amountToAdd;
        percentage = anountInvested / originalLoanAmount;
    }
}
