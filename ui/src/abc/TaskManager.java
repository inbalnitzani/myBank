package abc;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.*;
import abs.LoanTerms;
import abs.exception.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;


public class TaskManager {
    private final BankInterface bank = new Bank();
    private final Menu menu = new Menu();
    private boolean fileInSystem = false;
    private int currentAction;

    public TaskManager() {
    }

    public void manageSystem() {
        final int EXIT_SYSTEM = 8;
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

    public void actToUserChoice() {
        switch (currentAction) {
            case 1:
                getXMLFile();
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
                if (!menu.verifyExit())
                    currentAction = 0;
                break;
        }
    }

    public void promoteTimeline(){
        System.out.println("Yaz time was changed from: " + Globals.worldTime);
        Globals.changeWorldTimeByOne();
        System.out.println("To: " +Globals.worldTime);


    }
    public void getXMLFile() {
        String fileName = null;
        boolean tryLoadFile = true, succeed = false;
        while (tryLoadFile) {
            fileName = menu.getFileFullNamePath();
            try {
                succeed = bank.getXMLFile(fileName);
            } catch (FileException e) {
                System.out.println(e);
            } catch (JAXBException | FileNotFoundException e) {
                System.out.println("file does not exist.");
            } if (succeed) {
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
        LoanTerms currentLoanTerms=new LoanTerms();
        getLoanProperties(currentLoanTerms,bank.getCurrBalance(clientName));
        List<LoanDTO> loans = bank.findMatchLoans(clientName, currentLoanTerms);
        loans = menu.chooseLoansToInvest(loans);
        if (!loans.isEmpty()) {
            int amountLeft = bank.startInlayProcess(loans, clientName);
            menu.updateUserInvest(currentLoanTerms.getMaxAmount(), amountLeft);
        }
    }

    public void getLoanProperties(LoanTerms loanTerms,int clientBalance) {
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
        bank.loanMoneyToAccount(clientName, amountToCharge);
    }

    public void withdrawMoney() {
        String clientName = getClientNameForAction();
        System.out.println("Enter the amount you want to withdraw from your account");
        int amountToWithdraw = menu.chooseAmountByBalance(bank.getCurrBalance(clientName));
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
