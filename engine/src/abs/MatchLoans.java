package abs;

import java.util.Map;

public class MatchLoans {
    private Client client;
    private LoanTerms loanTerms;
    private Map<String, Loan> matchLoans;

    public MatchLoans(Client client, LoanTerms loanTerms) {
        this.client = client;
        this.loanTerms = loanTerms;
    }

    public Map<String, Loan> checkRelevantLoans(Map<String, Loan> matchLoans, Map<String, Loan> loansToCheck) {
        if (loansToCheck != null) {
            for (Loan loan : loansToCheck.values()) {
                if (loan.getOwner().equals(client.getFullName())) {
                    continue;
                }else if (!loanTerms.getCategories().contains(loan.getCategory())) {
                    continue;
                } else if (loan.getInterestRate() < loanTerms.getMinInterestForTimeUnit()) {
                    continue;
                } else if (loan.getTotalYazTime() < loanTerms.getMinTotalYaz() && loanTerms.getMinTotalYaz()!=0) {
                    continue;
                } else {
                    matchLoans.put(loan.getLoansID(), loan);
                }
            }
        }
        this.matchLoans = matchLoans;
        return matchLoans;
    }

    public int getAmountToInvest() {
        return loanTerms.maxAmount;
    }
}
