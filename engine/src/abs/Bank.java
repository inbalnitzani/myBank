package abs;

import abs.DTO.CategoryDTO;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.DTO.LoanTermsDTO;
import abs.schemaClasses.AbsDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Bank implements BankInterface {

    private Map<String,Category> categories;
    private List<Loan> activeLoans;
    private List<Loan> inRiskLoans;
    private Map<String, Loan> waitingLoans;
    private Map<String, Client> clients;
    private MatchLoans matchLoans;
    public static int worldTime = 1;
    public static int PENDING = 2;

    //CTOR
    public Bank() {
        clients = new HashMap<String, Client>();
        activeLoans = new ArrayList<Loan>();
        inRiskLoans = new ArrayList<Loan>();
        waitingLoans = new HashMap<String, Loan>();
        categories = new HashMap<String,Category>();

        Client client = new Client("Menash", 5000);
        Client client1 = new Client("Avrum", 1000);
        Client client2 = new Client("Tikva", 10000);
        Client client3 = new Client("Shosh", 20000);
        clients.put(client.getFullName(), client);
        clients.put(client1.getFullName(), client1);
        clients.put(client2.getFullName(), client2);
        clients.put(client3.getFullName(), client3);

        Loan loan = new Loan(client1, 2500, 15, "Investment");
        Loan loan1 = new Loan(client, 3000, 3, "Setup a business");
        waitingLoans.put("bar mitzva", loan);
        waitingLoans.put("build a room", loan1);
        categories.put("Setup a business",new Category("Setup a business"));
        categories.put("Investment",new Category("Investment"));
    }

    //GETTERS
    public List<ClientDTO> getClients() {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        for (Client client : clients.values()) {
            ClientDTO clientDto = new ClientDTO(client);
            clientDTO.add(clientDto);
        }
        return clientDTO;
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
        for (Category category : categories.values()) {
            categoriesDTO.add(new CategoryDTO(category.getCategoryName()));
        }
        return categoriesDTO;
    }

    public Map<String, Loan> getWaitingLoans() {
        return waitingLoans;
    }

    public void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) {
        clients.get(clientName).WithdrawingMoney(amountToWithdraw);
        ///זה בדיוק כמו שדיברנו בשיעור האחרון - רק מעביר טיפול מגורם אחד לשני
        //בנוסף לא מקבלים client  אמיתי/
    }

    public void loanMoneyToAccount(String clientName, int amountToLoad) {
        clients.get(clientName).addMoneyToAccount(amountToLoad);
    }

    public int getCurrBalance(String clientName) {
        ClientDTO clientDTO = new ClientDTO(clients.get(clientName));
        return clientDTO.getCurrBalance();
    }

    public List<Category> createCategoryListFromLoanTermsDto(LoanTermsDTO loanTermsDTO){

        List<Category> categories=new ArrayList<Category>();
        for (CategoryDTO categoryDTO: loanTermsDTO.categories)
        {
            categories.add(this.categories.get(categoryDTO.getCategoryName()));
        }
        return categories;
    }

    public List<LoanDTO> findMatchLoans(String clientName, LoanTermsDTO terms) {
        LoanTerms loanTerms=new LoanTerms(terms,createCategoryListFromLoanTermsDto(terms));
        matchLoans = new MatchLoans(clients.get(clientName), loanTerms);
        List<Loan> loans = new ArrayList<>();
        loans = matchLoans.checkRelevantLoans(loans, waitingLoans);
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

    public void startInlayProcess(List<LoanDTO> loansDTOToInvest, String clientName) {
        Client client = clients.get(clientName);
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loansDTOToInvest) {
            loansToInvest.add(waitingLoans.get(loanDTO.getLoansID()));
        }
        sortLoanListByLeftAmount(loansToInvest);
        addInvestorToLoans(loansToInvest, client);
    }

    public boolean getXMLFile(String filePath) {
        boolean readFile = false;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            AbsDescriptor info = deserializeFrom(inputStream);
            readFile = true;
        } catch (JAXBException | FileNotFoundException e) {
        }
        return readFile;
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("abs.schemaClasses");
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(inputStream);
    }

}
