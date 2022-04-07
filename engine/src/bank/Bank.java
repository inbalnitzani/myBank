package bank;
import dto.ClientDTO;
import dto.ConvertDTO;
import dto.LoanDTO;
import client.Client;
import exception.*;
import loan.*;
import schema.AbsDescriptor;
import file.File;
import schema.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class Bank implements Serializable, BankInterface {

    private Set<String> categories;
    private Map<String, Loan> activeLoans;
    private Map<String, Loan> waitingLoans;
    private Map<String, Client> clients;
    private MatchLoans matchLoans;

    public Bank() {
        clients = new HashMap<String, Client>();
        activeLoans = new HashMap<String, Loan>();
        waitingLoans = new HashMap<String, Loan>();
        categories = new HashSet<String>();
    }

    public List<ClientDTO> getClients() {
        return new ConvertDTO().createListClientDTO(clients.values());
    }

    public List<String> getCategories() {
        return new ConvertDTO().createListCategories(categories);
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

    public void withdrawMoneyFromAccount(String clientName, int amountToWithdraw) {
        clients.get(clientName).withdrawingMoney(amountToWithdraw);
    }

    public void loadMoney(String clientName, int amountToLoad) {
        clients.get(clientName).addMoneyToAccount(amountToLoad);
    }

    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms) {
        matchLoans = new MatchLoans(clients.get(clientName), terms);
        Map<String, Loan> loans = new HashMap<>();
        matchLoans.checkRelevantLoans(loans, waitingLoans);
        List<LoanDTO> loanDTOS = new ConvertDTO().createListLoanDto(loans.values());
        return loanDTOS;
    }

    public void addInvestorToLoan(Loan loan, Client client, int amountToInvestPerLoan) {
        PayBack payBack = loan.checkClientAlreadyInvested(client);
        Status loanStatus = loan.addNewInvestorToLoan(client, amountToInvestPerLoan, payBack);
        boolean isAlreadyInvolve = false;
        if (payBack != null)
            isAlreadyInvolve = true;
        client.addLoanToInvestor(loan, amountToInvestPerLoan, isAlreadyInvolve);
        if (loanStatus == Status.ACTIVE) {
            activeLoans.put(loan.getLoansID(), waitingLoans.remove(loan.getLoansID()));
        }
    }

    public void saveStateToFile(String fileName) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.flush();
        }
    }

    public int addInvestorToLoans(List<Loan> loans, Client client, int amountToInvest) {
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
                sumLoans = 0;
                amountToInvest = 0;
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
        List<Loan> loansToInvest = new ConvertDTO().createListLoan(loansDTOToInvest, waitingLoans);
        sortListByLeftAmount(loansToInvest);
        int amountLeft = addInvestorToLoans(loansToInvest, client, matchLoans.getAmountToInvest());
        return amountLeft;
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("schema");
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(inputStream);
    }

    public List<LoanDTO> getAllLoans() {
        Map<String, Loan> loans = new HashMap<>();
        for (Loan loan : waitingLoans.values())
            loans.put(loan.getLoansID(), loan);
        for (Loan loan : activeLoans.values())
            loans.put(loan.getLoansID(), loan);
        return new ConvertDTO().createListLoanDto(loans.values());
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

    public void paymentProcess(List<Payment> paymentList) {
        for (Payment payment : paymentList) {
            Loan loan = activeLoans.get(payment.getLoanID());
            double totalAmount = payment.getAmount();
            Client owner = clients.get(loan.getOwner());
            if (owner.getCurrBalance() < totalAmount) {
                setInRisk(loan, totalAmount);
            } else {
                payBack(loan, totalAmount, payment);
            }
        }
    }

    public void payBackToInvestor(PayBack investor, double totalAmount) {
        double amount = investor.getPercentage() * totalAmount;
        Client borrower = clients.get(investor.getClientDTOGivers());
        borrower.addMoneyToAccount(amount);
    }

    public void payBack(Loan loan, double totalAmount, Payment payment) {
        clients.get(loan.getOwner()).withdrawingMoney(totalAmount);
        loan.setAmountPaidBack(totalAmount);
        payment.setActualPaidTime(Global.worldTime);
        //payment.setPaid(true);
        for (PayBack investor : loan.getPayBacks()) {
            payBackToInvestor(investor, totalAmount);
        }
        if (loan.getAmountPaidBack() == loan.totalAmountToPayBack()) {
            loan.setStatus(Status.FINISHED);
        } else {
            loan.setStatus(Status.ACTIVE);
        }
    }

    public void startMoneyTransfers() {
        TreeMap<Integer, List<Payment>> payments = makePaymentsLists();
        while (!payments.isEmpty()) {
            int currKey = payments.firstKey();
            paymentProcess(payments.get(currKey));
            payments.remove(currKey);
        }
    }

    public TreeMap<Integer, List<Payment>> makePaymentsLists() {
        TreeMap<Integer, List<Payment>> paymentsByYaz = new TreeMap<>();

        for (Loan loan : activeLoans.values()) {
            Map<Integer, Payment> paymentMap = loan.getPayments();
            if (paymentMap.containsKey(Global.worldTime)) {
                int activeTime = loan.getActiveTime();
                if (!paymentsByYaz.containsKey(activeTime)) {
                    paymentsByYaz.put(activeTime, new ArrayList<>());
                }
                paymentsByYaz.get(activeTime).add(paymentMap.get(Global.worldTime));
            }
        }
        for (List<Payment> payments : paymentsByYaz.values()) {
            payments.sort(new PaymentsComparator());
        }
        return paymentsByYaz;
    }

    public void setInRisk(Loan loan, double totalAmount) {
        loan.setStatus(Status.RISK);
        int nextTimePay=loan.getPace() + Global.worldTime;
        Map<Integer,Payment> paymentList=loan.getPayments();
        if (paymentList.containsKey(nextTimePay))
            paymentList.get(nextTimePay).addToAmount(totalAmount);
        else {
            paymentList.put(nextTimePay,new Payment(loan.getLoansID(),(totalAmount/(1+(loan.getInterestRate())/100.0)),loan.getInterestRate()/100.0));
        }
    }


}
