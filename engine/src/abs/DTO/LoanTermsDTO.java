package abs.DTO;

import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;

public class LoanTermsDTO {

        public int maxAmount;
        public Set<CategoryDTO> categories;
        public int minInterestForTimeUnit;
        public int minTimeForLoan;

        public LoanTermsDTO() {
            categories = new HashSet<CategoryDTO>();
        }

        public void setMaxAmount(int amount) {
            maxAmount = amount;
        }

        public void setCategories(@NotNull Set<CategoryDTO> categories) {
            this.categories=categories;
        }

        public void setMinInterestForTimeUnit(int minInterestForTimeUnit) {
            this.minInterestForTimeUnit = minInterestForTimeUnit;
        }

        public void setMinTimeForLoan(int minTimeForLoan) {
            this.minTimeForLoan = minTimeForLoan;
        }

        public Set<CategoryDTO> getCategories() {
            return categories;
        }

        public int getMinInterestForTimeUnit() {
            return minInterestForTimeUnit;
        }

        public int getMinTimeForLoan() {
            return minTimeForLoan;
        }
    }



