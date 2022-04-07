package bank;

import client.Client;
import loan.Loan;
import loan.LoanTerms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchLoans implements Serializable {
    private Client client;
    private LoanTerms loanTerms;
    private Map<String, Loan> matchingLoans;

    public MatchLoans(Client client, LoanTerms loanTerms) {
        this.client = client;
        this.loanTerms = loanTerms;
    }

    public void checkRelevantLoans(Map<String, Loan> matchLoans, Map<String, Loan> loansToCheck) {
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
        this.matchingLoans = matchLoans;

//        List<Loan>loans=loansToCheck.values().stream()
//                .filter(loan-> !(loan.getOwner().equals(client.getFullName())))
////                           .filter(loan -> loanTerms.getCategories().contains(loan.getCategory()))
//                .filter(loan -> loan.getInterestRate() >= loanTerms.getMinInterestForTimeUnit())
//                .filter(loan->loan.getTotalYazTime() >=loanTerms.getMinTotalYaz() && loanTerms.getMinTotalYaz()!=0)
//                .collect(Collectors.toList());
    }

    public int getAmountToInvest() {
        return loanTerms.maxAmount;
    }
}
