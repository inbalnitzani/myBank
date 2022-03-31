package abs;

//import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class LoanTerms {

    public int maxAmount;
    public Set<String> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;

    public LoanTerms() {

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
}



