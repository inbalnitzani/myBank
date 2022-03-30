package abc;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.*;
import abs.LoanTerms;

import java.util.List;

public class TaskManager {
    private final BankInterface bank = new Bank();
    private final Menu menu = new Menu();
    private boolean fileInSystem = false;
    private LoanTerms currentLoan;
    private int currentAction;

    public TaskManager() {
        currentLoan = new LoanTerms();
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
                System.out.println("2. get loans information");
                //    printLoansInfo(bank.getLoans());
                break;
            case 3:
                System.out.println("3. get clients information");
                //menu.();
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
                startOfInlay();
                break;
            case 7:
                System.out.println("7. promote timeline and make payments");
                break;
            case 8:
                if (!menu.verifyExit())
                    currentAction = 0;
                break;
        }
    }

    public void getXMLFile() {
        String fileName = null;
        boolean tryLoadFile = true, succeed = false;
        while (tryLoadFile) {
            fileName = menu.getFileFullNamePath();
            succeed = bank.getXMLFile(fileName);
            if (succeed)
                System.out.println("File read successfully");
            else {
                if (!menu.checkTryAgain(fileName))
                    tryLoadFile = false;
            }
        }
    }

    public void startOfInlay() {
        String clientName = getClientNameForAction();
        getLoanProperties(bank.getCurrBalance(clientName));
        List<LoanDTO> loans = bank.findMatchLoans(clientName, currentLoan);
        loans = menu.chooseLoansToInvest(loans);
        if (!loans.isEmpty()) {
            bank.startInlayProcess(loans, clientName);
        }
    }

    public void getLoanProperties(int clientBalance) {
        System.out.println("Please enter the amount you want to invest.");
        System.out.println("Pay attention - you can't invest more than " + clientBalance + ".");
        currentLoan.setMaxAmount(menu.chooseAmountByBalance(clientBalance));
        currentLoan.setCategories(menu.chooseCategory(bank.getCategories()));
        currentLoan.setMinInterestForTimeUnit(menu.getMinInterest());
        currentLoan.setMinTimeForLoan(menu.getMinTimeForLoan());
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
