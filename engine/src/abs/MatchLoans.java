package abs;

import java.util.List;
import java.util.Map;

public class MatchLoans {
    private Client client;
    private LoanTerms loanTerms;
    private List<Loan> matchLoans;

    public MatchLoans(Client client,LoanTerms loanTerms){
        this.client=client;
        this.loanTerms=loanTerms;
    }

    public List<Loan> checkRelevantLoans(List<Loan> matchLoans, Map<String,Loan> loansToCheck) {
        if (loansToCheck != null) {
            for (Loan loan : loansToCheck.values()) {
                if (loan.getOwner().equals(client)) {
                    continue;
                } else if (!loanTerms.getCategories().contains(loan.getCategory())) {
                    continue;
                } else if (loan.getInterestRate() < loanTerms.getMinInterestForTimeUnit()) {
                    continue;
                } else if (loan.getTotalTU() < loanTerms.getMinTimeForLoan()) {
                    continue;
                } else {
                    matchLoans.add(loan);
                }
            }
        }
        this.matchLoans = matchLoans;
        return matchLoans;
    }


}
