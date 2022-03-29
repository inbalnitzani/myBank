package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;

import java.util.*;

public class Bank implements BankInterface {

    private List<String> categories;
    private TreeMap<Integer, TreeMap<Integer, Loan>> activeLoans;
    private TreeMap<Integer, TreeMap<Integer, Loan>> inRiskLoans;
    private Map<Integer,Loan> newLoans;
    private Map<Integer,Loan> pendingLoans;
    private Map<String ,Client> clients;
    private MatchLoans matchLoans;
    public static int worldTime = 1;
    public static int PENDING = 2;


    //CTOR
    public Bank()  {
        clients = new HashMap<String,Client>();
        activeLoans = new TreeMap<Integer, TreeMap<Integer, Loan>>();
        inRiskLoans = new TreeMap<Integer, TreeMap<Integer, Loan>>();
        newLoans = new HashMap<Integer,Loan>();
        pendingLoans = new HashMap<Integer,Loan>();
    }

    //GETTERS
    public List<ClientDTO> getClients() {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        Collection<Client> clientCollection=clients.values();

        for(Client client:clientCollection)
        {
            ClientDTO clientDto=new ClientDTO(client);
            clientDTO.add(clientDto);
        }

        return clientDTO;
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categoriesDTO=new ArrayList<CategoryDTO>();
        for (String category:categories)
        {
            categoriesDTO.add(new CategoryDTO(category));
        }
        return categoriesDTO;
    }

    public Map<Integer, Loan> getNewLoans() {
        return newLoans;
    }

    public Map<Integer, Loan> getPendingLoansLoans() {
        return pendingLoans;
    }

    public void withdrawMoneyFromAccount(int clientIndex, int amountToWithdraw)
    {
        clients.get(clientIndex).WithdrawingMoney(amountToWithdraw);
        ///זה בדיוק כמו שדיברנו בשיעור האחרון - רק מעביר טיפול מגורם אחד לשני
        //בנוסף לא מקבלים client  אמיתי/
    }
    public void loanMoneyToAccount(int clientIndex, int amountToLoad)
    {
        clients.get(clientIndex).addMoneyToAccount(amountToLoad);
    }
    public int getCurrBalance(int indexOfClient){
        ClientDTO clientDTO=new ClientDTO(clients.get(indexOfClient));
        return clientDTO.getCurrBalance();
    }

    public List<LoanDTO> findMatchLoans(int clientIndex, LoanTerms terms) {
        matchLoans = new MatchLoans(clients.get(clientIndex), terms);
        List<Loan> loans = new ArrayList<>();
        //loans = matchLoans.checkRelevantLoans(loans, newLoans);
        //loans = matchLoans.checkRelevantLoans(loans, pendingLoans);
        List<LoanDTO> loanDTOS=new ArrayList<LoanDTO>();
        for(Loan loan:loans)
        {
            loanDTOS.add(new LoanDTO(loan));
        }
        return loanDTOS;
    }

    public void addInvestorsToLoans(List<LoanDTO> loansToInvest, int clientIndex) {

        /*
        for (LoanDTO loanDTO : loansToInvest) {
            if (loanDTO.getStatus() == PENDING) {
                newLoans.get(loanDTO.getLoansID()).addOneInvestorToLoan();
            } else {
                pendingLoans.get(loanDTO.getLoansID()).addOneInvestorToLoan();
            }
        }*/
    }




}
