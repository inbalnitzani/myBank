package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.DTO.LoanTermsDTO;

import java.util.List;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<CategoryDTO> getCategories();

     void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) ;

     void loanMoneyToAccount(String clientName, int amountToLoad) ;

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTermsDTO terms);

     void startInlayProcess(List<LoanDTO> loansToInvest, String clientName) ;

     boolean getXMLFile(String filePath) ;
}
