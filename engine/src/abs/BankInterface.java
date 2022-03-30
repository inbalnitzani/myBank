package abs;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;

import java.util.List;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<String> getCategories();

     void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) ;

     void loanMoneyToAccount(String clientName, int amountToLoad) ;

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     void startInlayProcess(List<LoanDTO> loansToInvest, String clientName) ;

     boolean getXMLFile(String filePath) ;
}
