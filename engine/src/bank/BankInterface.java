package bank;

import dto.ClientDTO;
import dto.LoanDTO;
import exception.*;
import loan.LoanTerms;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BankInterface {

     List<ClientDTO> getClients() ;

     List<String> getCategories();

     void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) ;

     List<LoanDTO> getAllLoans();

     void loadMoney(String clientName, int amountToLoad) ;

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     int startInlayProcess(List<LoanDTO> loansToInvest, String clientName) ;

     void saveStateToFile(String fileName) throws IOException;

     boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException;
     void startMoneyTransfers();
}
