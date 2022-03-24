package abc;

import abs.Bank;
import abs.Client;
import abs.Loan;

import java.util.Collection;

public class TaskManager {
    private final Bank bank = new Bank();
    private Menu menu = new Menu();
    int currentAction = 0;

    public TaskManager() {
    }

    public void actToUserChoice() {
        switch (currentAction) {
            case 1:
                System.out.println("1. read a file");
                break;
            case 2:
                System.out.println("2. get loans information");
                printLoansInfo(bank.getLoans());
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

    private void printLoansInfo(Collection<Loan> loans) {
        for (Loan currLoan : loans) {
            menu.printSingleLoanInfo(currLoan);
        }
    }

    //checked
    public void manageSystem() {
        final int EXIT_SYSTEM = 8;
        boolean stillInSystem = true;
        while (stillInSystem) {
            while (currentAction != EXIT_SYSTEM) {
                menu.printMenu();
                currentAction = menu.getUserChoice();
                actToUserChoice();
            }
            stillInSystem = menu.verifyExit();
        }
    }

    public void loadMoneyToAccount() {
        Client client = getClient();
        System.out.println("Enter the amount you want to charge your account");
        int amountToCharge = menu.scanAmountFromUser();
        client.addMoneyToAccount(amountToCharge);
    }

    public void withdrawMoney() {
        Client client = getClient();
        System.out.println("Enter the amount you want to withdraw from your account");
        int amountToWithdraw = menu.chooseAmountByBalance(client.getCurrBalance());
        client.WithdrawingMoney(amountToWithdraw);
    }

    public Client getClient() {
        System.out.println("Please choose a client from the next list:");
        System.out.println("(Enter the number of the client)");
        menu.printClientsNames(bank.getClients());
        int numberOfClient = menu.getClientNumber(bank.getClients().size());
        return bank.getClients().get(numberOfClient);
    }


}
