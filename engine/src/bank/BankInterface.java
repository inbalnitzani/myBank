package bank;

import dto.ClientDTO;
import dto.LoanDTO;
import exception.*;
import loan.Loan;
import loan.LoanTerms;
import loan.Payment;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BankInterface {

     int getWorldTime();

     List<ClientDTO> getClients();

     List<String> getCategories();

     void withdrawMoneyFromAccount(String clientName, double amountToWithdraw) throws NotEnoughMoney;

     List<LoanDTO> getAllLoans();

     void loadMoney(String clientName, double amountToLoad);

     int getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     int startInlayProcess(List<LoanDTO> loansToInvest, String clientName);

     void saveStateToFile(String fileName) throws IOException;

     boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException, NegativeBalanceException, NegativeLoanCapitalException, NegativeTimeException, InterestException, IdException;

     void promoteTime();

     ClientDTO getClientByName(String name);
    // void payBack(Loan loan, double totalAmount, Payment payment);

     void payAllBack(String loanID) throws NotEnoughMoney;

     void payBack(String loanID, double totalAmount) throws NotEnoughMoney;
}
