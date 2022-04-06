package abs;
import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.exception.*;
import abs.schemaClasses.AbsDescriptor;
import abs.schemaClasses.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class Bank implements Serializable, BankInterface {

    private Set<String> categories;//
    private Map<String, Loan> activeLoans;
    private Map<String, Loan> waitingLoans;
    private Map<String, Client> clients;
    private MatchLoans matchLoans;

    //CTOR
    public Bank() {
        clients = new HashMap<String, Client>();
        activeLoans = new HashMap<String, Loan>();
        waitingLoans = new HashMap<String, Loan>();
        categories = new HashSet<String>();
    }

    public List<ClientDTO> createListClientDTO() {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        for (Client client : clients.values()) {
            clientDTO.add(new ClientDTO(client));
        }
        return clientDTO;
    }

    public List<LoanDTO> createListLoanDto(Collection<Loan> loans) {
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

    //GETTERS

    public List<LoanDTO> getLoansDTO() {
        return createListLoanDto(waitingLoans.values());
    }

    public List<ClientDTO> getClients() {
        return createListClientDTO();
    }

    public List<String> getCategories() {
        return createListCategories();
    }

    public int getCurrBalance(String clientName) {
        return clients.get(clientName).getCurrBalance();
    }

    public boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException {
        boolean readFile = false;
        InputStream inputStream = new FileInputStream(filePath);
        AbsDescriptor info = deserializeFrom(inputStream);
        File file = new File();
        file.checkFile(info.getAbsCategories().getAbsCategory(), info.getAbsLoans().getAbsLoan(), info.getAbsCustomers().getAbsCustomer(), filePath);
        convertToBank(info);
        readFile = true;
        return readFile;
    }

    public Map<String, Loan> getWaitingLoans() {
        return waitingLoans;
    }

    public void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) {
        clients.get(clientName).WithdrawingMoney(amountToWithdraw);
    }

    public void loanMoneyToAccount(String clientName, int amountToLoad) {
        clients.get(clientName).addMoneyToAccount(amountToLoad);
    }

    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms) {
        matchLoans = new MatchLoans(clients.get(clientName), terms);
        Map<String, Loan> loans = new HashMap<>();
        loans = matchLoans.checkRelevantLoans(loans, waitingLoans);
        List<LoanDTO> loanDTOS = createListLoanDto(loans.values());
        return loanDTOS;
    }

    public void addInvestorToLoan(Loan loan, Client client, int amountToInvestPerLoan) {
        Status loanStatus = loan.addNewInvestor(client, amountToInvestPerLoan);
        client.setAsGiver(loan);
        client.subtractCurrBalance(amountToInvestPerLoan);
        if (loanStatus == Status.ACTIVE) {
            activeLoans.put(loan.getLoansID(), waitingLoans.remove(loan.getLoansID()));
        }
    }

    public void saveStateToFile(String fileName) throws IOException {
        try (
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
                out.writeObject(this);
                out.flush();
            }
    }

    public int addInvestorToLoans(List<Loan> loans, Client client, int amountToInvest) {
        if (loans != null) {
            int sumLoans = loans.size(), amountPerLoan = amountToInvest / sumLoans;
            int firstPayment = amountPerLoan + amountToInvest % sumLoans;

            while (sumLoans > 0) {
                Loan currLoan = loans.get(0);
                int amountLeftCurrLoan = currLoan.getLeftAmountToInvest();
                if (amountLeftCurrLoan >= firstPayment) {
                    if (firstPayment != amountPerLoan) {
                        addInvestorToLoan(currLoan, client, firstPayment);
                        loans.remove(0);
                    }
                    for (Loan loan : loans) {
                        addInvestorToLoan(loan, client, amountPerLoan);
                    }
                    loans.clear();
                    sumLoans=0;
                    amountToInvest=0;
                } else {
                    addInvestorToLoan(currLoan, client, amountLeftCurrLoan);
                    loans.remove(0);
                    amountToInvest = amountToInvest - amountLeftCurrLoan;
                    sumLoans--;
                    if (sumLoans != 0) {
                        amountPerLoan = amountToInvest / sumLoans;
                        firstPayment = amountPerLoan + amountToInvest % sumLoans;
                    }
                }
            }

        }
        return amountToInvest;
    }

    public void sortListByLeftAmount(List<Loan> loansToInvest) {
        Collections.sort(loansToInvest, new Comparator<Loan>() {
            public int compare(Loan loan1, Loan loan2) {
                int sumLeftToInvestLoan1 = loan1.getCapital() - loan1.getAmountCollectedPending();
                int sumLeftToInvestLoan2 = loan2.getCapital() - loan2.getAmountCollectedPending();
                return sumLeftToInvestLoan1 - sumLeftToInvestLoan2;
            }
        });
    }

    public int startInlayProcess(List<LoanDTO> loansDTOToInvest, String clientName) {
        Client client = clients.get(clientName);
        List<Loan> loansToInvest = createListLoan(loansDTOToInvest);
        sortListByLeftAmount(loansToInvest);
        int amountLeft = addInvestorToLoans(loansToInvest, client, matchLoans.getAmountToInvest());
        return amountLeft;
    }

    public List<Loan> createListLoan(List<LoanDTO> loanDTOList) {
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loanDTOList) {
            loansToInvest.add(waitingLoans.get(loanDTO.getLoansID()));
        }
        return loansToInvest;
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("abs.schemaClasses");
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(inputStream);
    }

    public List<LoanDTO> getAllLoans() {
        Map<String, Loan> loans = new HashMap<>();
        for (Loan loan : waitingLoans.values())
            loans.put(loan.getLoansID(), loan);
        for (Loan loan : activeLoans.values())
            loans.put(loan.getLoansID(), loan);

        return createListLoanDto(loans.values());
    }

    public List<String> createCategoryListFromLoanTermsDto(LoanTerms loanTermsDTO) {

        List<String> categories = new ArrayList<String>();
        for (String string : loanTermsDTO.categories) {
            //categories.add(this.categories.get(categoryDTO.getCategoryName()));
        }
        return categories;
    }

    public void convertToBank(AbsDescriptor info) {
        setCategories(info.getAbsCategories());
        setClients(info.getAbsCustomers());
        setLoans(info.getAbsLoans());
    }

    public void setCategories(AbsCategories absCategories) {
        List<String> categories = absCategories.getAbsCategory();
        for (String category : categories) {
            this.categories.add(category);
        }
    }

    public void setClients(AbsCustomers absCustomers) {
        List<AbsCustomer> customerList = absCustomers.getAbsCustomer();
        for (AbsCustomer customer : customerList) {
            Client newClient = new Client(customer.getName(), customer.getAbsBalance());
            this.clients.put(customer.getName(), newClient);
        }
    }

    public void setLoans(AbsLoans absLoans) {
        List<AbsLoan> loanList = absLoans.getAbsLoan();
        for (AbsLoan loan : loanList) {
            String id = loan.getId();
            Loan newLoan = new Loan(id, loan.getAbsOwner(), loan.getAbsCapital(), loan.getAbsIntristPerPayment(), loan.getAbsCategory(), loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz());
            this.waitingLoans.put(id, newLoan);
        }
    }

    public void payBack(){

      Map<Integer,List<Payment>> payments = makePaymentsLists();
        for (List<Payment> listOfPayments:payments.values()) {
            for (Payment payment:listOfPayments) {
                Loan loan = activeLoans.get(payment.getLoanID());
                double totalAmount =payment.getAmount();
                if(clients.get(loan.getOwner()).getCurrBalance()<totalAmount) {
                   setInRisk(loan,totalAmount);
                }
                else{
                    setBackToActive(loan);
                    for (PayBack investor:loan.getPayBacks()) {
                        double amount = investor.getPercentage()*payment.getAmount();
                        Client client = clients.get(investor.getClientDTOGivers());
                        clients.get(loan.getOwner()).subtractCurrBalance(amount);
                        loan.setAmountPaidBack(amount);
                        client.addMovement(new Movement(client.getCurrBalance(),amount,Globals.worldTime));
                        client.addToCurrBalance(amount);

                    }
                    if (loan.getAmountPaidBack()==loan.getCapital()*((loan.getInterestRate()/100)+1)){
                        loan.setStatus(Status.FINISHED);
                    }
                }
            }
        }


    }

    public Map<Integer,List<Payment>> makePaymentsLists(){
        Map<Integer,List<Payment>> paymentsByYaz = new HashMap<>();

        for (Loan loan:activeLoans.values()) {
            Map<Integer,Payment> paymentMap = loan.getPayments();
            int activeTime = loan.getActiveTime();
            if(paymentMap.containsKey(Globals.worldTime)){
                if(!paymentsByYaz.containsKey(activeTime)){
                    paymentsByYaz.put(activeTime,new ArrayList<>());
                }
                paymentsByYaz.get(activeTime).add(paymentMap.get(Globals.worldTime));
            }
        }
        for (List <Payment> payments: paymentsByYaz.values()) {
            payments.sort(new PaymentsComparator());
        }
        return paymentsByYaz;
    }

    public void setInRisk(Loan loan,double totalAmount){
        loan.setStatus(Status.RISK);
        loan.getPayments().get(loan.getPace() + Globals.worldTime).addToAmount(totalAmount);
    }

    public void setBackToActive(Loan loan){
        if (loan.getStatus().equals(Status.RISK))
            loan.setStatus(Status.ACTIVE);
    }


}
