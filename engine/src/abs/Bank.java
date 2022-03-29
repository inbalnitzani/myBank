package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import java.util.*;

public class Bank implements BankInterface {

    private List<String> categories;
    private List<Loan> activeLoans;
    private List<Loan> inRiskLoans;
    //private Map<Integer, Loan> newLoans;
    //private Map<Integer, Loan> pendingLoans;
    private Map<String, Loan> waitingLoans;
    private List<Client> clients;
    private MatchLoans matchLoans;
    public static int worldTime = 1;
    public static int PENDING = 2;


    //CTOR
    public Bank() {
        clients = new ArrayList<Client>();
        activeLoans = new ArrayList<Loan>();
        inRiskLoans = new ArrayList<Loan>();
        //   newLoans = new HashMap<Integer, Loan>();
        //    pendingLoans = new HashMap<Integer, Loan>();
        waitingLoans = new HashMap<String, Loan>();
    }

    //GETTERS
    public List<ClientDTO> getClients() {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        /*
        Collection<Client> clientCollection=clients.values();
         for(Client client:clientCollection)
        {
            ClientDTO clientDto=new ClientDTO(client);
            clientDTO.add(clientDto);
        }
*/
        for (Client client : clients) {
            ClientDTO clientDto = new ClientDTO(client);
            clientDTO.add(clientDto);
        }
        return clientDTO;
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
        for (String category : categories) {
            categoriesDTO.add(new CategoryDTO(category));
        }
        return categoriesDTO;
    }

    public Map<String, Loan> getWaitingLoans() {
        return waitingLoans;
    }
    /*
    public Map<Integer, Loan> getNewLoans() {
        return newLoans;
    }

    public Map<Integer, Loan> getPendingLoansLoans() {
        return pendingLoans;
    }*/

    public void withdrawMoneyFromAccount(int clientIndex, int amountToWithdraw) {
        clients.get(clientIndex).WithdrawingMoney(amountToWithdraw);
        ///זה בדיוק כמו שדיברנו בשיעור האחרון - רק מעביר טיפול מגורם אחד לשני
        //בנוסף לא מקבלים client  אמיתי/
    }

    public void loanMoneyToAccount(int clientIndex, int amountToLoad) {
        clients.get(clientIndex).addMoneyToAccount(amountToLoad);
    }

    public int getCurrBalance(int indexOfClient) {
        ClientDTO clientDTO = new ClientDTO(clients.get(indexOfClient));
        return clientDTO.getCurrBalance();
    }

    public List<LoanDTO> findMatchLoans(int clientIndex, LoanTerms terms) {
        matchLoans = new MatchLoans(clients.get(clientIndex), terms);
        List<Loan> loans = new ArrayList<>();
        //loans = matchLoans.checkRelevantLoans(loans, newLoans);
        //loans = matchLoans.checkRelevantLoans(loans, pendingLoans);
        List<LoanDTO> loanDTOS = new ArrayList<LoanDTO>();
        for (Loan loan : loans) {
            loanDTOS.add(new LoanDTO(loan));
        }
        return loanDTOS;
    }

    public Loan changeLoanDtoToLoan(LoanDTO loanDTO) {
        return new Loan(loanDTO);
    }

    public void addInvestorToLoan(Loan loan, Client client, int amountToInvestPerLoan) {
        Status loanStatus = loan.addNewInvestor(client, amountToInvestPerLoan);
        client.setAsGiver(loan);
        client.setCurrBalance(amountToInvestPerLoan);
        if (loanStatus == Status.ACTIVE) {
            activeLoans.add(waitingLoans.remove(loan.getLoansID()));
        }
    }
    public void addInvestorToLoans(List<Loan> loans, Client client) {
        if (loans != null) {
            int currBalance = client.getCurrBalance();
            int numOfLoansToInvest = loans.size();
            int amountToInvestPerLoan = currBalance / numOfLoansToInvest;

            while (numOfLoansToInvest > 0) {
                Loan currLoan = loans.get(0);
                if (currLoan.getLeftAmountToInvest() >= amountToInvestPerLoan) {
                    for (Loan loan : loans) {
                        addInvestorToLoan(loan, client, amountToInvestPerLoan);
                    }
                } else {
                    addInvestorToLoan(currLoan, client, amountToInvestPerLoan);
                    currBalance = client.getCurrBalance();
                    numOfLoansToInvest--;
                    amountToInvestPerLoan = currBalance / numOfLoansToInvest;
                }
            }
        }
    }

    public List<Loan> createSortedListOfLoans(List<LoanDTO> loansDTOToInvest) {
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loansDTOToInvest) {
            loansToInvest.add(changeLoanDtoToLoan(loanDTO));
        }
        sortLoanListByLeftAmount(loansToInvest);
        return loansToInvest;
    }

    public void sortLoanListByLeftAmount(List<Loan> loansToInvest) {
        Collections.sort(loansToInvest, new Comparator<Loan>() {
            public int compare(Loan loan1, Loan loan2) {
                int sumLeftToInvestLoan1 = loan1.getOriginalAmount() - loan1.getAmountCollectedPending();
                int sumLeftToInvestLoan2 = loan2.getOriginalAmount() - loan2.getAmountCollectedPending();
                return sumLeftToInvestLoan1 - sumLeftToInvestLoan2;
            }
        });
    }

    public void startInlayProcess(List<LoanDTO> loansDTOToInvest, int clientIndex) {
        Client client = clients.get(clientIndex);
        List<Loan> loansToInvest = createSortedListOfLoans(loansDTOToInvest);
        addInvestorToLoans(loansToInvest, client);
    }
}
