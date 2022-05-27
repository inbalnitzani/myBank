package loan;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanTerms implements Serializable {

    public int maxAmount;
    public Set<String> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;
    public int maxLoansForOwner;
  //  public int minTimeForLoan;


    public void setMaxLoansForOwner(int maxLoansForOwner) {
        this.maxLoansForOwner = maxLoansForOwner;
    }

    public int getMaxLoansForOwner() {
        return maxLoansForOwner;
    }

    public LoanTerms() {
        categories=new HashSet<>();
    }

    public void setMaxAmount(int amount) {
        maxAmount = amount;
    }

    public void setMinInterestForTimeUnit(int minInterestForTimeUnit) {
        this.minInterestForTimeUnit = minInterestForTimeUnit;
    }

    public void setMinTimeForLoan(int minTimeForLoan) {
        this.minTimeForLoan = minTimeForLoan;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public int getMinInterestForTimeUnit() {
        return minInterestForTimeUnit;
    }

    public int getMinTotalYaz() {
        return minTimeForLoan;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}



