package engine;
import dto.ClientDTO;
import dto.ConvertDTO;
import dto.LoanDTO;
import client.Client;
import exception.*;
import exception.CategoriesException;
import exception.CustomerException;
import exception.IdException;
import exception.InterestException;
import exception.NamesException;
import exception.NegativeBalanceException;
import exception.NegativeLoanCapitalException;
import exception.NegativeTimeException;
import exception.NotEnoughMoney;
import exception.PaceException;
import exception.XmlException;
import loan.*;
import loan.Loan;
import loan.LoanTerms;
import loan.PayBack;
import loan.Payment;
import loan.Status;
import schema.AbsCategories;
import schema.AbsCustomer;
import schema.AbsCustomers;
import schema.AbsDescriptor;
import file.File;
import schema.*;
import schema.AbsLoan;
import schema.AbsLoans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class Bank implements Serializable, engine.BankInterface {

    private Set<String> categories;
    private Map<String, Loan> activeLoans;
    private Map<String, Loan> waitingLoans;
    private Map<String, Client> clients;
    private engine.MatchLoans matchLoans;
    private int time = 1;

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

    public double getCurrBalance(String clientName) {
        return clients.get(clientName).getCurrBalance();
    }

    public boolean getXMLFile(String filePath) throws CategoriesException, JAXBException, FileNotFoundException, NamesException, CustomerException, XmlException, PaceException, NegativeBalanceException, NegativeLoanCapitalException, NegativeTimeException, InterestException, IdException {
        boolean readFile = false;
        InputStream inputStream = new FileInputStream(filePath);
        AbsDescriptor info = deserializeFrom(inputStream);
        File file = new File();
        file.checkFile(info.getAbsCategories().getAbsCategory(), info.getAbsLoans().getAbsLoan(), info.getAbsCustomers().getAbsCustomer(), filePath);
        convertToBank(info);
        time = 1;
        engine.Global.setWorldTime(1);
        readFile = true;
        return readFile;
    }

    public void withdrawMoneyFromAccount(String clientName, double amountToWithdraw) throws NotEnoughMoney {
        clients.get(clientName).withdrawingMoney(amountToWithdraw);
    }

    public void loadMoney(String clientName, int amountToLoad) {
        clients.get(clientName).addMoneyToAccount(amountToLoad);
    }

    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms) {
        matchLoans = new engine.MatchLoans(clients.get(clientName), terms);
        Map<String, Loan> loans = new HashMap<>();
        matchLoans.checkRelevantLoans(loans, waitingLoans, clients);
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
            clients.get(loan.getOwner()).addMoneyToAccount(loan.getCapital());
        }
    }

    public int getWorldTime() {
        return time;
    }

    public void saveStateToFile(String fileName) throws IOException {
        time = engine.Global.worldTime;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.flush();
        }
    }

    public int addInvestorToLoans(List<Loan> loans, Client client, int amountToInvest, int ownershipAttention) {
        int sumLoans = loans.size(), firstPayment = (amountToInvest / sumLoans) + (amountToInvest % sumLoans);
        while (sumLoans > 0) {
            Loan currLoan = loans.get(0);
            int amountLeftCurrLoan = currLoan.getLeftAmountToInvest();
            if (amountLeftCurrLoan < firstPayment) {
                firstPayment = amountLeftCurrLoan;
            }
            if (ownershipAttention > 0) {
                double maxPartOfLoanDouble = (ownershipAttention / 100.0) * (currLoan.getCapital());
                int maxPartOfLoan = ((int) maxPartOfLoanDouble);
                if (maxPartOfLoan < firstPayment) {
                    firstPayment = maxPartOfLoan;
                }
            }
            addInvestorToLoan(currLoan, client, firstPayment);
            loans.remove(0);
            amountToInvest = amountToInvest - firstPayment;
            sumLoans--;
            if (sumLoans != 0) {
                firstPayment = (amountToInvest / sumLoans) + amountToInvest % sumLoans;
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
        int maxOwnershipAttention = matchLoans.getMaxOwnershipPrecent();
        return addInvestorToLoans(loansToInvest, client, matchLoans.getAmountToInvest(), maxOwnershipAttention);
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("engineBank/schema");
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
        if (!this.categories.isEmpty())
            this.categories.clear();
        for (String category : categories) {
            this.categories.add(stringConvertor(category));
        }
    }

    public void setClients(AbsCustomers absCustomers) {
        List<AbsCustomer> customerList = absCustomers.getAbsCustomer();
        if (!this.clients.isEmpty()) {
            this.clients.clear();
        }
        for (AbsCustomer customer : customerList) {
            Client newClient = new Client(stringConvertor(customer.getName()), customer.getAbsBalance());
            this.clients.put(stringConvertor(customer.getName()), newClient);
        }
    }

    public String stringConvertor(String str) {
        return str.trim().toLowerCase();
    }

    public void setLoans(AbsLoans absLoans) {
        List<AbsLoan> loanList = absLoans.getAbsLoan();
        if (!this.waitingLoans.isEmpty()) {
            this.waitingLoans.clear();
            this.activeLoans.clear();
        }
        for (AbsLoan loan : loanList) {
            String owner = stringConvertor(loan.getAbsOwner());
            String id = stringConvertor(loan.getId());
            Loan newLoan = new Loan(id, owner, loan.getAbsCapital(), loan.getAbsIntristPerPayment(), stringConvertor(loan.getAbsCategory()), loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz());
            this.waitingLoans.put(id, newLoan);
            clients.get(owner).addLoanToBorrowerList(newLoan);
        }
    }

    public void paymentProcess(List<Payment> paymentList) throws NotEnoughMoney {
        for (Payment payment : paymentList) {
            Loan loan = activeLoans.get(payment.getLoanID());
            double totalAmountLeftToPay = payment.getOriginalAmount()-payment.getAmount();
            Client owner = clients.get(loan.getOwner());
            if (owner.getCurrBalance() < totalAmountLeftToPay) {
                setInRisk(loan, totalAmountLeftToPay);
            } else {
                payBack(loan, totalAmountLeftToPay, payment);
            }
        }
    }

    public void payBackToInvestor(PayBack investor, double totalAmount) {
        double amount = investor.getPercentage() * totalAmount;
        Client borrower = clients.get(investor.getClientDTOGivers());
        borrower.addMoneyToAccount(amount);
    }
//hello
    public void payBackNextPayment(String loanID, double totalAmount, int yaz) throws NotEnoughMoney {
        Loan loan = activeLoans.get(loanID);
        Payment payment = loan.getPayments().get(yaz);
        payBack(loan, totalAmount, payment);
    }

    public void payBack(Loan loan, double totalAmount, Payment payment) throws NotEnoughMoney {
        Client client = clients.get(loan.getOwner());
        if (client.getCurrBalance() >= totalAmount) {
            client.withdrawingMoney(totalAmount);
            loan.setAmountPaidBack(totalAmount);
            payment.setActualPaidTime(engine.Global.worldTime);
            payment.setAmount(totalAmount);
            for (PayBack investor : loan.getPayBacks()) {
                payBackToInvestor(investor, totalAmount);
            }
            if (loan.getAmountPaidBack() == loan.totalAmountToPayBack()) {
                loan.setActualLastPaymentTime(engine.Global.worldTime);
                loan.setStatus(Status.FINISHED);
            } else {
                loan.setStatus(Status.ACTIVE);
            }
        } else throw new NotEnoughMoney(totalAmount);
    }

    public void promoteTime() {
        List<Payment> payments = makePaymentsLists();
        for (Payment payment : payments) {
      double originalAmount = payment.getOriginalAmount();
            double paidAmount = payment.getAmount();
            double amountLeft = originalAmount-paidAmount;
            setInRisk(activeLoans.get(payment.getLoanID()), amountLeft);
        }
        engine.Global.changeWorldTimeByOne();
        time++;
    }

    public List<Payment> makePaymentsLists() {
        List<Payment> paymentsList = new ArrayList<>();
        for (Loan loan : activeLoans.values()) {
            Payment payment = loan.getPayments().get(engine.Global.worldTime);
          //  if(payment.isNewPayment())
            if (payment != null) {
                if (!payment.isPaid() ||payment.getPaidAPartOfDebt())
                    paymentsList.add(payment);
            }
        }
        return paymentsList;
    }

    public void setInRisk(Loan loan, double totalAmount) {
        loan.setRiskTime();
        int nextTimePay = loan.getPace() + engine.Global.worldTime;
        Map<Integer, Payment> paymentList = loan.getPayments();
        if (paymentList.containsKey(nextTimePay))
            paymentList.get(nextTimePay).addToOriginalAmount(totalAmount);
        else {
            paymentList.put(nextTimePay, new Payment(loan.getLoansID(), totalAmount, loan.getInterestRate()));
        }
    }

    public ClientDTO getClientByName(String name) {
        return new ClientDTO(clients.get(name));
    }

    public void payAllBack(String loanID) throws NotEnoughMoney {
        Loan loan = activeLoans.get(loanID);
        double totalAmount = loan.totalAmountToPayBack() - loan.getAmountPaidBack();
        Client client = clients.get(loan.getOwner());
        if (client.getCurrBalance() >= totalAmount) {
            client.withdrawingMoney(totalAmount);
            loan.setAmountPaidBack(totalAmount);
            Payment payment = loan.getPayments().get(engine.Global.worldTime);
            int index = 0, pace = loan.getPace(), lastPaymentTime = loan.getLastPaymentTime();
            if (payment != null) {
                payment.setPayAll(true);
                payment.setAmount(totalAmount);
                index = engine.Global.worldTime + pace;
                payment.setActualPaidTime(engine.Global.worldTime);
                loan.setActualLastPaymentTime(engine.Global.worldTime);
            } else {
                payment =  loan.getNextPayment();
                payment.setActualPaidTime(engine.Global.worldTime);
                payment.setAmount(totalAmount);
                index = loan.getNextPaymentTime() + pace;
            }
            for (int i = index; i <= lastPaymentTime; i += pace) {
                loan.getPayments().remove(i);
            }
            for (PayBack investor : loan.getPayBacks()) {
                payBackToInvestor(investor, totalAmount);
            }
            loan.setStatus(Status.FINISHED);
        } else throw new NotEnoughMoney(totalAmount);
    }

    public void payApartOfDebt(String loanID, double amount) throws NotEnoughMoney {
        Loan loan = activeLoans.get(loanID);
        Payment payment = loan.getPayments().get(engine.Global.worldTime);
        if(payment == null){
            //add a new payment
            Payment paymentToAdd = new Payment(loanID,amount,loan.getInterestRate());
            loan.getPayments().put(engine.Global.worldTime, paymentToAdd);
            paymentToAdd.setActualPaidTime(engine.Global.worldTime);
            paymentToAdd.setAmount(amount);
            paymentToAdd.setPaidAPartOfDebt(true);
            paymentToAdd.setNewPayment(true);



            payment = loan.getPayments().get(loan.getNextPaymentTime());
            payment.setOriginalAmount(payment.getOriginalAmount()-amount);
        }
        else {
            payment.setRiskPayTime(engine.Global.worldTime);
            payment.setAmount(amount);
            payment.setPaidAPartOfDebt(true);
        }

        loan.setAmountPaidBack(amount);
        clients.get(loan.getOwner()).withdrawingMoney(amount);
        for (PayBack investor : loan.getPayBacks()) {
            payBackToInvestor(investor, amount);
        }

       // loan.setActualLastPaymentTime(Global.worldTime);

    }

    public void addNewUserToBank(String name){
        clients.put(name,new Client(name,0));
    }

    public void addNewXMLFile(String filePath) throws FileNotFoundException, JAXBException, NamesException, NegativeLoanCapitalException, CustomerException, PaceException, NegativeTimeException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        boolean readFile = false;
        InputStream inputStream = new FileInputStream(filePath);
        AbsDescriptor info = deserializeFrom(inputStream);
        File file = new File();
        file.checkFile(info.getAbsCategories().getAbsCategory(), info.getAbsLoans().getAbsLoan(), info.getAbsCustomers().getAbsCustomer(), filePath);
//        convertToBank(info);
//        time = 1;
//        engine.Global.setWorldTime(1);
//        readFile = true;
//        return readFile;
    }
}