package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.LoanTermsDTO;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class LoanTerms {

    public int maxAmount;
    public List<Category> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;

    public LoanTerms(LoanTermsDTO loanTermsDTO,List<Category>categoryList) {
        this.maxAmount=loanTermsDTO.maxAmount;
        this.minTimeForLoan=loanTermsDTO.minTimeForLoan;
        this.minInterestForTimeUnit=loanTermsDTO.minInterestForTimeUnit;
        this.categories=categoryList;
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

    public int getMinInterestForTimeUnit() {
        return minInterestForTimeUnit;
    }

    public int getMinTimeForLoan() {
        return minTimeForLoan;
    }

    public List<Category> getCategories() {
        return categories;
    }
}

