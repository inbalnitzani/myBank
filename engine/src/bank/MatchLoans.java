package bank;

import client.Client;
import loan.Loan;
import loan.LoanTerms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchLoans implements Serializable {
    private Client client;
    private LoanTerms loanTerms;
    private Map<String, Loan> matchingLoans;

    public MatchLoans(Client client, LoanTerms loanTerms) {
        this.client = client;
        this.loanTerms = loanTerms;
    }

    public void checkRelevantLoans(Map<String, Loan> matchLoans, Map<String, Loan> loansToCheck, Map<String,Client> clientList) {
        if (loansToCheck != null) {
            for (Loan loan : loansToCheck.values()) {
                if (loan.getOwner().equals(client.getFullName())) {
                    continue;
                } else if (!loanTerms.getCategories().contains(loan.getCategory())) {
                    continue;
                } else if (loan.getInterestRate() < loanTerms.getMinInterestForTimeUnit()) {
                    continue;
                } else if (loan.getTotalYazTime() < loanTerms.getMinTotalYaz() && loanTerms.getMinTotalYaz() != 0) {
                    continue;
                } else {
                    matchLoans.put(loan.getLoansID(), loan);
                }
            }
        }

        if (loanTerms.getMaxLoansForOwner()!=0) {
            filterLoansByMaxLoansForOwner(matchLoans,clientList);
        }
        this.matchingLoans = matchLoans;
    }

    public void filterLoansByMaxLoansForOwner(Map<String,Loan> matchLoans, Map<String,Client>clientList) {
        List<String> loansToRemove = new ArrayList<>();
        for (Loan loan : matchLoans.values()) {
            Client owner = clientList.get(loan.getOwner());
            if (owner.getLoanListAsBorrower().size() > loanTerms.getMaxLoansForOwner()) {
                loansToRemove.add(loan.getLoansID());
            }
        }
        for (String loanId : loansToRemove) {
            matchLoans.remove(loanId);
        }
    }

    public int getAmountToInvest() {
        return loanTerms.maxAmount;
    }
}
