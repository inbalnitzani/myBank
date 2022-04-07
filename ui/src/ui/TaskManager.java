package ui;

import dto.ClientDTO;
import dto.LoanDTO;
import bank.Bank;
import bank.BankInterface;
import bank.Globals;
import exception.FileException;
import loan.LoanTerms;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class TaskManager {
    private BankInterface bank = new Bank();
    private final Menu menu = new Menu();
    private boolean fileInSystem = false;
    private int currentAction;
    static final int EXIT_SYSTEM = 9;

    public TaskManager() {
    }

    public void manageSystem() throws IOException, ClassNotFoundException {
        boolean stillInSystem = true;
        while (stillInSystem) {
            while (currentAction != EXIT_SYSTEM) {
                menu.printMenu();
                currentAction = menu.getUserChoice(fileInSystem);
                actToUserChoice();
            }
            System.out.println("Thank you, goodbye..");
            stillInSystem = false;
        }
    }

    public void actToUserChoice() throws IOException, ClassNotFoundException {
        switch (currentAction) {
            case 1:
                getBankData();
                break;
            case 2:
                menu.printLoansInfo(bank.getAllLoans());
                break;
            case 3:
                menu.printClientInfo(bank.getClients());
                break;
            case 4:
                loadMoneyToAccount();
                break;
            case 5:
                withdrawMoney();
                break;
            case 6:
                startOfInlay();
                break;
            case 7:
                promoteTimeline();
                break;
            case 8:
                saveStateToFile();
                break;
            case 9:
                if (!menu.verifyExit())
                    currentAction = 0;
                break;
        }
    }

    public void saveStateToFile() {
        try {
            System.out.println("Please insert a name for the file:");
            bank.saveStateToFile(menu.getFileNameFromUser());
            System.out.println("File created successfully.");
        } catch (Exception e) {
            System.out.println("An unknown problem occurred. The system state is not saved to the file. Please try again");
        }
    }

    public void promoteTimeline() {
        System.out.println("Yaz time was changed from: " + Globals.worldTime);
        Globals.changeWorldTimeByOne();
        System.out.println("To: " + Globals.worldTime);
        bank.startMoneyTransfers();
    }

    public void getBankData() {
        int way = menu.chooseWayToGetDataInfo();
        if (way == 1) {
            getXMLFile();
        } else {
            System.out.println("Please insert the file name:");
            readBankDataFromFile(menu.getFileNameFromUser());
        }
    }

    public void getXMLFile() {
        String fileName = null;
        boolean tryLoadFile = true, succeed = false;
        while (tryLoadFile) {
            System.out.println("Please insert a full path name of your XML");
            fileName = menu.getFileNameFromUser();
            try {
                succeed = bank.getXMLFile(fileName);
            } catch (FileException e) {
                System.out.println(e);
            } catch (JAXBException | FileNotFoundException e) {
                System.out.println("file does not exist.");
            }
            if (succeed) {
                System.out.println("File read successfully");
                tryLoadFile = false;
                fileInSystem = true;
                Globals.setWorldTime(1);
            } else {
                if (!menu.checkTryAgain(fileName))
                    tryLoadFile = false;
            }
        }
    }

    public void startOfInlay() {
        String clientName = getClientNameForAction();
        LoanTerms currentLoanTerms = new LoanTerms();
        getLoanProperties(currentLoanTerms, bank.getCurrBalance(clientName));
        List<LoanDTO> loans = bank.findMatchLoans(clientName, currentLoanTerms);
        loans = menu.chooseLoansToInvest(loans);
        if (!loans.isEmpty()) {
            int amountLeft = bank.startInlayProcess(loans, clientName);
            menu.updateUserInvest(currentLoanTerms.getMaxAmount(), amountLeft);
        }
    }

    public void readBankDataFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            bank = (Bank) in.readObject();
            System.out.println("File read successfully\n");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(fileName + " does not exist. Operation failed.\n");
        }
    }

    public void getLoanProperties(LoanTerms loanTerms, int clientBalance) {
        System.out.println("Please enter the amount you want to invest.");
        System.out.println("Pay attention - you can't invest more than " + clientBalance + ".");
        loanTerms.setMaxAmount(menu.chooseAmountByBalance(clientBalance));
        loanTerms.setCategories(menu.chooseCategory(bank.getCategories()));
        loanTerms.setMinInterestForTimeUnit(menu.getMinInterest());
        loanTerms.setMinTimeForLoan(menu.getMinTimeForLoan());
    }

    public void loadMoneyToAccount() {
        String clientName = getClientNameForAction();
        System.out.println("Enter the amount you want to charge your account");
        int amountToCharge = menu.scanAmountFromUser();
        bank.loadMoney(clientName, amountToCharge);
    }

    public void withdrawMoney() {
        String clientName = getClientNameForAction();
        int maxAmountToWithdraw = bank.getCurrBalance(clientName);
        System.out.println("Enter the amount you want to withdraw from your account.");
        System.out.println("Pay attention - can't withdraw more than " + maxAmountToWithdraw+".");
        int amountToWithdraw = menu.chooseAmountByBalance(maxAmountToWithdraw);
        bank.withdrawMoneyFromAccount(clientName, amountToWithdraw);
    }

    public String getClientNameForAction() {
        System.out.println("Please choose a client from the next list:");
        System.out.println("(Enter the number of the client)");
        List<ClientDTO> clients = bank.getClients();
        menu.printClientsNames(clients, false);
        int clientNumber = menu.getClientNumber(clients.size());
        return clients.get(clientNumber).getFullName();
    }

}
