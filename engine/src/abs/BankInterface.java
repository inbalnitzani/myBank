package abs;

import java.util.List;
import java.util.Map;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<CategoryDTO> getCategories();

     Map<Integer, Loan> getNewLoans() ;

     Map<Integer, Loan> getPendingLoansLoans() ;

     void withdrawMoneyFromAccount(int clientIndex, int amountToWithdraw) ;

     void loanMoneyToAccount(int clientIndex, int amountToLoad) ;

     int getCurrBalance(int clientIndex);

     List<LoanDTO> findMatchLoans(int clientIndex, LoanTerms terms);

     void addInvestorsToLoans(List<LoanDTO> loansToInvest,int clientIndex) ;
}
