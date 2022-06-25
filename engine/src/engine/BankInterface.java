package engine;

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

     void loadMoney(String clientName, int amountToLoad);

     double getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     int startInlayProcess(List<LoanDTO> loansToInvest, String clientName);

     void saveStateToFile(String fileName) throws IOException;

     boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException, NegativeBalanceException, NegativeLoanCapitalException, NegativeTimeException, InterestException, IdException;

     void promoteTime();

     ClientDTO getClientByName(String name);

     void payAllBack(String loanID) throws NotEnoughMoney;
     void payBack(Loan loan, double totalAmount, Payment payment) throws NotEnoughMoney;
     void payBackNextPayment(String loanID, double totalAmount, int yaz) throws NotEnoughMoney;
     void payApartOfDebt(String loanID, double amount) throws NotEnoughMoney;

}