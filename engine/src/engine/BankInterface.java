package engine;

import dto.*;
import exception.*;
import loan.Loan;
import loan.LoanTerms;
import loan.Payment;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BankInterface {

     int getWorldTime();

     List<ClientDTO> getClients();

     List<String> getCategories();

     double withdrawMoneyFromAccount(String clientName, double amountToWithdraw) throws NotEnoughMoney;

     List<LoanDTO> getAllLoans();

     void listLoanForSale(String loanName,String client);

     List<LoanDTO> getLoansForSale(String name);

     void makeSale(String loan, String buyer) throws NotEnoughMoney;

     double loadMoney(String clientName, double amountToLoad);

     Map<Integer, List<MovementDTO>> getMovementsByClientName(String client);

     List<LoanDTO> getLenderLoansByName(String clientName);

     List<LoanDTO> getBorrowerLoansByName(String clientName);

     double getCurrBalance(String clientName);

     List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms);

     int startInlayProcess(List<LoanDTO> loansToInvest, String clientName);

     void saveStateToFile(String fileName) throws IOException;

     boolean getXMLFile(String filePath) throws Exception;

     void promoteTime();

     ClientDTO getClientByName(String name);

     void payAllBack(String loanID) throws NotEnoughMoney;

     void payBack(Loan loan, double totalAmount, Payment payment) throws NotEnoughMoney;

     void payBackNextPayment(String loanID, double totalAmount, int yaz) throws NotEnoughMoney;

     void payApartOfDebt(String loanID, double amount) throws NotEnoughMoney;

     void addNewUserToBank(String name);

     void addNewXMLFile(String filePath, String clientName) throws Exception;

     boolean checkLoanNameExist(String loanName);

     void addNewLoan(String id, String owner, int amount, int rate, String categoryName, int totalYazTime, int pace);

     Boolean getRewind();

      void setRewind(Boolean value);

      void saveStateToMap();

      Map<Integer,stateDTO> getAdminStates();

      int getVersion();
      void setLookingBack(Integer val);

     Integer getLookingBack();
     clientStateDTO getClientForRewind(String name, Integer time);
}