package abs;

import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;

public class LoanTerms {

        public int maxAmount;
        public Set<String> categories;
        public int minInterestForTimeUnit;
        public int minTimeForLoan;

        public LoanTerms(Set<String> categories) {
            this.categories = categories;
        }

        public void setMaxAmount(int amount) {
            maxAmount = amount;
        }

        public void setCategories(@NotNull Set<String> categories) {
            this.categories=categories;
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

        public int getMinTimeForLoan() {
            return minTimeForLoan;
        }
    }



