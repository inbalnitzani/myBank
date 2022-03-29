package abc;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.*;
import abs.schemaClasses.AbsDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class TaskManager {
    private final BankInterface bank = new Bank();
    private final Menu menu = new Menu();
    private boolean fileInSystem = false;
    private LoanTerms currentLoan;

    public TaskManager() {
    }

    public void actToUserChoice() {
        int currentAction = 0;
        switch (currentAction) {
            case 1:
                System.out.println("1. read a file");
                getXMLFile();
                break;
            case 2:
                System.out.println("2. get loans information");
                //    printLoansInfo(bank.getLoans());
                break;
            case 3:
                System.out.println("3. get clients information");
                loadMoneyToAccount();
                break;
            case 4:
                System.out.println("4. load money to account");
                loadMoneyToAccount();
                break;
            case 5:
                System.out.println("5. Withdraw money to account");
                withdrawMoney();
                break;
            case 6:
                System.out.println("6. Activate inlay");
                break;
            case 7:
                System.out.println("7. promote timeline and make payments");
                break;
            case 8:
                System.out.println("8. Exit");
                break;
        }
    }

    public void startOfInlay() {
        int clientIndex = getClientIndexForAction();
        getLoanProperties(bank.getCurrBalance(clientIndex));
        List<LoanDTO> loans = bank.findMatchLoans(clientIndex, currentLoan);
        loans = menu.chooseLoansToInvest(loans);
        bank.addInvestorsToLoans(loans,clientIndex);

    }

    public void getLoanProperties(int clientBalance) {
        System.out.println("Please enter the amount you want to invest.");
        System.out.println("Pay attention - you can't invest more than " + clientBalance + ".");
        currentLoan.setMaxAmount(menu.chooseAmountByBalance(clientBalance));
        currentLoan.setCategories(menu.chooseCategory(bank.getCategories()));
        currentLoan.setMinInterestForTimeUnit(menu.getMinInterest());
        currentLoan.setMinTimeForLoan(menu.getMinTimeForLoan());
    }

    public void getXMLFile() {
        boolean validInput = false, loadFile = true;
        while (!validInput) {
            try {
                InputStream inputStream = new FileInputStream(new File("engine/src/abs/ex1small.xml"));
                AbsDescriptor info = deserializeFrom(inputStream);
                validInput = true;
                fileInSystem = true;
                System.out.println("File read successfully");
            } catch (JAXBException | FileNotFoundException e) {
                loadFile = menu.FileNotExist();
            }
            if (!loadFile) {
                validInput = true;
            }
        }
        return;
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("abs.schemaClasses");
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(inputStream);
    }

    //checked
    public void manageSystem() {
        final int EXIT_SYSTEM = 8;
        int currentAction=0;
        boolean stillInSystem = true;
        while (stillInSystem) {
            while (currentAction != EXIT_SYSTEM) {
                menu.printMenu();
                currentAction = menu.getUserChoice(fileInSystem);
                actToUserChoice();
            }
            stillInSystem = menu.verifyExit();
        }
    }

    public void loadMoneyToAccount() {
        int clientIndex = getClientIndexForAction();
        System.out.println("Enter the amount you want to charge your account");
        int amountToCharge = menu.scanAmountFromUser();
      ///////////////////////////////////////////////////////////////////////////////////////////////
        bank.loanMoneyToAccount(clientIndex,amountToCharge);
        //client.addMoneyToAccount(amountToCharge);
    }

    public void withdrawMoney() {
        int clientIndex = getClientIndexForAction();
        System.out.println("Enter the amount you want to withdraw from your account");
        int amountToWithdraw = menu.chooseAmountByBalance(bank.getCurrBalance(clientIndex));
        bank.withdrawMoneyFromAccount(clientIndex,amountToWithdraw);
    }

    public int getClientIndexForAction() {
        System.out.println("Please choose a client from the next list:");
        System.out.println("(Enter the number of the client)");
        List<ClientDTO> clients=bank.getClients();
        menu.printClientsNames(clients, false);
        return menu.getClientNumber(clients.size());
    }


}
