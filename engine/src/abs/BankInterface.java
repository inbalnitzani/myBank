package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;

import java.util.List;
import java.util.Set;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<CategoryDTO> getCategories();
/*
     Map<Integer, Loan> getNewLoans() ;

     Map<Integer, Loan> getPendingLoansLoans() ;
*/
     void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) ;

     void loanMoneyToAccount(String clientName, int amountToLoad) ;

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     void startInlayProcess(List<LoanDTO> loansToInvest, String clientName) ;
}
