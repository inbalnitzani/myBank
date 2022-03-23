package abc;

import abs.*;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import static abc.Menu.operationType.LOADTOACCOUNT;

public class Menu {
    private Bank bank;
    private TaskManager manager;
    private boolean fileRead = false;
    private boolean goal;

    public enum operationType {WITHDRAW,LOADTOACCOUNT};
    Scanner sc = new Scanner(System.in);

    public Menu(Bank bank) {
        this.bank = bank;
    }

    public void printMenu() {
        System.out.println("choose a number");
        System.out.println("1. read a file");
        System.out.println("2. get loans information");
        System.out.println("3. get clients information");
        System.out.println("4. load money to account");
        System.out.println("5. Withdraw money to account");
        System.out.println("6. Activate inlay");
        System.out.println("7. promote timeline and make payments");
        System.out.println("8. Exit");
    }

    public void getUsersChoice() {

        int usersChoice = validationCheck();
        switch (usersChoice) {
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
                pullMoneyFromAccount();
                break;
            case 5:
                System.out.println("5. Withdraw money to account");
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

    public void printClientsNames() {
        int clientIndex = 1;
        for (Client client : bank.getClients()) {
            System.out.println(clientIndex + ". " + client.getFullName());
        }
    }
public int chooseAmountByBalance(int currBalance){
    boolean validInput=false;
        int amount=0;
        while (!validInput){
            amount=scanAmountFromUser();
            if(amount<=currBalance)
            {
                validInput=true;
            }
            else{
                System.out.println("The maximum amount you can withdraw is "+currBalance);
            }

        }
}
    public void pullMoneyFromAccount() {
        Client clientToLoadMoney = getClient();
        System.out.println("Enter the amount you want to withdraw from your account");
        int amountToWithdraw = chooseAmountByBalance(clientToLoadMoney.getCurrBalance());
        clientToLoadMoney.WithdrawingMoney(amountToWithdraw);
    }

    public void loadMoneyToAccount() {

        Client clientToLoadMoney = getClient();
        System.out.println("Enter the amount you want to charge your account");
        int amountToCharge = scanAmountFromUser();
        manager.loadMoneyToAccount(clientToLoadMoney, amountToCharge);
    }

    public int scanAmountFromUser() {
        boolean validInput = false;
        int amountToCharge = 0;
        while (!validInput) {
            amountToCharge = sc.nextInt();
            if (checkAmount(amountToCharge))
                validInput = true;
            else {
                System.out.println("Invalid input! Please enter a positive integerEnter!");
            }
        }
        return amountToCharge;
    }

    public Client getClientInstance() {
        boolean validInput = false;
        Client client = null;
        int numberOfClient = -1, numOfClient = bank.getClients().size();
        while (!validInput) {
            try {
                numberOfClient = sc.nextInt();
                if (checkClientNumber(numberOfClient)) {
                    client = bank.getClients().get(numberOfClient - 1);
                    validInput = true;
                } else {
                    System.out.println("Invalid Input!! Please enter a number between 1 to " + numOfClient);
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!! Please enter a number between 1 to " + numOfClient);
            }
        }
        return client;
    }

    public Client getClient() {
        System.out.println("Please choose a client from the next list:");
        System.out.println("(Enter the number of the client)");
        printClientsNames();
        return getClientInstance();
    }

    public boolean checkAmount(int number) {
      return (number > 0);
    }

    public boolean checkClientNumber(int number) {
        return number > 0 && number < bank.getClients().size();

    }

    public void printOneClientInfo(Client client) {
        System.out.println(client.getFullName() + ":");
        System.out.println("Client account movements:");
        for (int i = 0; i < Globals.worldTime; i++) {
            Set<Movement> movements = client.getMovementsByTime(i);
            if (movements != null) {
                printMovementsInfo(movements);
            }
        }
        System.out.println("loans to get money:");
        for (Loan loan : client.getLoanSet("asGiver")) {
            printSingleLoanInfo(loan);
        }
        System.out.println("loans give money");
        for (Loan loan : client.getLoanSet("asBorrower")) {
            printSingleLoanInfo(loan);
        }

    }

    public void printMovementsInfo(Set<Movement> movements) {
        System.out.println("Time: " + Globals.worldTime + ":");
        for (Movement movement : movements) {
            System.out.println("Account balance before abs.movement: " + movement.getAmountBeforeMovement());
            System.out.println(movement.getKindOfExecute() + " " + movement.getAmount());
            System.out.println("Account balance after abs.movement: " + movement.getAmountBeforeMovement());
        }

    }

    private void printLoansInfo(Collection<Loan> loans) {
        for (Loan curLoan : loans) {
            printSingleLoanInfo(curLoan);
        }
    }

    public void printSingleLoanInfo(Loan curLoan) {
        System.out.println("Loans ID: " + curLoan.getLoansID());
        System.out.println("Owner: " + curLoan.getOwnersName());
        System.out.println("Category: " + curLoan.getCategory());
        System.out.println("Amount: " + curLoan.getOriginalAmount() + "Original total time units: " + curLoan.getTotalTU());
        System.out.println("Interest: " + curLoan.getInterestRate() + "Rate: " + curLoan.getPace());
        Loan.Status status = curLoan.getStatus();
        System.out.println("Status: " + status);

        switch (status) {
            case RISK:
                System.out.println("status: in risk");
                break;
            case ACTIVE:
                System.out.println("status: active");
                //curLoan.getGivers();
                // need to print each lender's name and invesment
                System.out.println("abs.loan was activated at :" + curLoan.getStartingTimeUnit());
                System.out.println("next abs.payment is at :" + curLoan.getNextPayment());

                break;
            case PENDING:
                System.out.println("status: pending");

                //curLoan.getGivers();
                // need to print each lender's name and invesment
                System.out.println("current amount is :" + curLoan.getAmountCollected());
                System.out.println("amount left to activate abs.loan :" + (curLoan.getOriginalAmount() - curLoan.getAmountCollected()));
                break;
            case FINISHED:
                System.out.println("status: finished");

                break;
        }
    }

    public void printClientsInfo() {
        for (Client client : bank.getClients()) {
            printOneClientInfo(client);
        }

    }

    public int validationCheck() {
        boolean valid = false;
        int usersChoice = 0;
        while (!valid) {
            boolean skipSecondIf = false;
            try {
                usersChoice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                skipSecondIf = true;
            }

            if (usersChoice >= 2 && usersChoice <= 8 && !fileRead) {
                System.out.println("Invalid input, Please scan a file first.");
                System.out.println("Choose again!");
                usersChoice = 0;//init choice back to 0
            } else if ((usersChoice < 1 || usersChoice > 8) && (skipSecondIf == false)) {
                System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                usersChoice = 0;//init choice back to 0
            } else if (skipSecondIf == false)
                valid = true;

        }

        return usersChoice;

    }

}