package abs;

import abs.DTO.CategoryDTO;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoanTerms {

    public int maxAmount;
    public List<Category> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;

    public LoanTerms(){
        categories=new ArrayList<Category>();
    }

    public void setMaxAmount(int amount){
        maxAmount=amount;
    }
    public void setCategories(@NotNull Set<CategoryDTO> categories)
    {
        for (CategoryDTO category:categories) {
            this.categories.add(new Category(category.getCategoryName()));
        }
    }
    public void setMinInterestForTimeUnit(int minInterestForTimeUnit){
        this.minInterestForTimeUnit =minInterestForTimeUnit;
    }
    public void setMinTimeForLoan(int minTimeForLoan)
    {
        this.minTimeForLoan=minTimeForLoan;
    }
    public List<Category> getCategories(){return categories;}
    public int getMinInterestForTimeUnit(){return minInterestForTimeUnit;}
    public int getMinTimeForLoan(){return minTimeForLoan;}
}

