package abs;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.exception.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<String> getCategories();

     void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) ;

     List<LoanDTO> getAllLoans();

     void loanMoneyToAccount(String clientName, int amountToLoad) ;

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     int startInlayProcess(List<LoanDTO> loansToInvest, String clientName) ;

     boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException;
     void payBack();
}
