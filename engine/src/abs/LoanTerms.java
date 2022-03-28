package abs;

import java.util.List;

public class LoanTerms {

    public int maxAmount;
    public List<String> categories;
    public int minInterestForTimeUnit;
    public int minTimeForLoan;

    public LoanTerms(){}

    public void setMaxAmount(int amount){
        maxAmount=amount;
    }
    public void setCategories(List<String> categories)
    {
        this.categories=categories;
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

