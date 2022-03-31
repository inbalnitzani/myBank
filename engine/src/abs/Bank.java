package abs;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.schemaClasses.AbsDescriptor;
import abs.schemaClasses.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Bank implements BankInterface {

    private Set<String> categories;//
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
        categories = new HashSet<String>();
    }

    public List<ClientDTO> createListClientDTO() {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        for (Client client : clients.values()) {
            ClientDTO clientDto = new ClientDTO(client);
            clientDTO.add(clientDto);
        }
        return clientDTO;
    }

    public List<LoanDTO> createListLoanDto(List<Loan> loans) {
        List<LoanDTO> loanDTOS = new ArrayList<LoanDTO>();
        for (Loan loan : loans) {
            loanDTOS.add(new LoanDTO(loan));
        }
        return loanDTOS;
    }

    public List<String> createListCategories() {
        List<String> categoriesDTO = new ArrayList<String>();
        for (String category : categories) {
            categoriesDTO.add(category);
        }
        return categoriesDTO;
    }

    public List<Loan> createListLoan(List<LoanDTO> loanDTOList) {
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loanDTOList) {
            loansToInvest.add(waitingLoans.get(loanDTO.getLoansID()));
        }
        return loansToInvest;
    }

    //GETTERS

    public List<ClientDTO> getClients() {
        return createListClientDTO();
    }

    public List<String> getCategories() {
        return createListCategories();
    }

    public int getCurrBalance(String clientName) {
        return clients.get(clientName).getCurrBalance();
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

    public Map<String, Loan> getWaitingLoans() {
        return waitingLoans;
    }

    //GETTERS

    public void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) {
        clients.get(clientName).WithdrawingMoney(amountToWithdraw);
    }

    public void loanMoneyToAccount(String clientName, int amountToLoad) {
        clients.get(clientName).addMoneyToAccount(amountToLoad);
    }

    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms) {
        matchLoans = new MatchLoans(clients.get(clientName), terms);
        List<Loan> loans = new ArrayList<>();
        loans = matchLoans.checkRelevantLoans(loans, waitingLoans);
        List<LoanDTO> loanDTOS = createListLoanDto(loans);
        return loanDTOS;
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
        List<Loan> loansToInvest = createListLoan(loansDTOToInvest);
        sortLoanListByLeftAmount(loansToInvest);
        addInvestorToLoans(loansToInvest, client);
    }

    public List<Loan> createListLoan(List<LoanDTO> loanDTOList) {
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loanDTOList) {
            loansToInvest.add(waitingLoans.get(loanDTO.getLoansID()));
        }
        return loansToInvest;
    }

    public boolean getXMLFile(String filePath) {
        boolean readFile = false;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            AbsDescriptor info = deserializeFrom(inputStream);
            readFile = true;
            ;

        } catch (JAXBException | FileNotFoundException e) {
        }
        return readFile;
    }


    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("abs.schemaClasses");
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(inputStream);
    }

    public List<String> createCategoryListFromLoanTermsDto(LoanTerms loanTermsDTO) {

        List<String> categories = new ArrayList<String>();
        for (String string : loanTermsDTO.categories) {
            //categories.add(this.categories.get(categoryDTO.getCategoryName()));
        }
        return categories;
    }
    public void setCategories(AbsCategories absCategories){
        List<String> categories = absCategories.getAbsCategory();
        for (String category:categories) {
            this.categories.add(category);
        }
    }
    public void setClients(AbsCustomers absCustomers){
        List<AbsCustomer> customerList = absCustomers.getAbsCustomer();
        for (AbsCustomer customer:customerList) {
            Client newClient = new Client(customer.getName(),customer.getAbsBalance());
            this.clients.put(customer.getName(),newClient);
        }
    }
    public void setLoans(AbsLoans absLoans){
        List<AbsLoan> loanList = absLoans.getAbsLoan();
        for (AbsLoan loan:loanList) {
            String id =loan.getId();
            Loan newLoan = new Loan(id,loan.getAbsOwner(),loan.getAbsIntristPerPayment(),loan.getAbsCapital(),loan.getAbsCategory());
            this.waitingLoans.put(id,newLoan);
        }
    }

}
