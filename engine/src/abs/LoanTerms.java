package abs;

import abs.DTO.CategoryDTO;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoanTerms {

    public int maxAmount;
    public List<String> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;

    public LoanTerms(){}

    public void setMaxAmount(int amount){
        categories=new ArrayList<String>();
        maxAmount=amount;
    }
    public void setCategories(@NotNull Set<CategoryDTO> categories)
    {
        for (CategoryDTO category:categories) {
            this.categories.add(category.getCategoryName());
        }
    }
    public void setMinInterestForTimeUnit(int minInterestForTimeUnit){
        this.minInterestForTimeUnit =minInterestForTimeUnit;
    }
    public void setMinTimeForLoan(int minTimeForLoan)
    {
        this.minTimeForLoan=minTimeForLoan;
    }
    public List<String> getCategories(){return categories;}
    public int getMinInterestForTimeUnit(){return minInterestForTimeUnit;}
    public int getMinTimeForLoan(){return minTimeForLoan;}
}

