package abc;

import abs.*;
import abs.schemaClasses.AbsDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;


public class Menu {

    Scanner scanner = new Scanner(System.in);

    public Menu() {}

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

public boolean FileNotExist(){
    System.out.println("Error! File does not exist.");
    System.out.println("If you want to go back to menu - press 1");
    System.out.println("If you want to try load file again - press 2");
    boolean validInput=false, loadAgain=false;
    int userChoice=0;
    while (!validInput){
        try{
            userChoice=scanner.nextInt();
            if(userChoice==1){
                loadAgain=false;
                validInput=true;
            }
            else if(userChoice==2){
                loadAgain=true;
                validInput=true;
            }
        } catch (Exception e){
            scanner.next();
        }
        finally {
            if(!validInput){
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    return loadAgain;
}
    public boolean verifyExit() {
        System.out.println("You just arrived! Are you sure you want to exit??");
        System.out.println("1.NO - I want to stay!!");
        System.out.println("2.YES - Exit.");
        int userChoice = 0;
        boolean wantToStay=true;
        boolean validInput = false;
        while (!validInput) {
            try {
                if (userChoice == 2)
                {
                    validInput=true;
                    wantToStay=false;
                }
                userChoice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
            }
            if (userChoice != 1) {
                System.out.println("Please choose 1 for stay or 2 for exit!");
            } else {
                wantToStay=true;
                validInput=true;
            }
        }
        return wantToStay;
    }


    public void printClientsNames(Collection<Client> clients) {
        int clientIndex = 1;
        for (Client client : clients) {
            System.out.println(clientIndex + ". " + client.getFullName());
            clientIndex++;
        }
    }

    public int chooseAmountByBalance(int currBalance) {
        boolean validInput = false;
        int amount = 0;
        while (!validInput) {
            amount = scanAmountFromUser();
            if (amount <= currBalance) {
                validInput = true;
            } else {
                System.out.println("The maximum amount you can withdraw is " + currBalance +".");
                System.out.println("Please try again");
            }
        }
        return amount;
    }

      public int scanAmountFromUser() {
        boolean validInput = false;
        int amountToCharge = 0;
        while (!validInput) {
            try {
                amountToCharge = scanner.nextInt();
                if (amountToCharge > 0) {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                scanner.next();
            }
            if (!validInput) {
                System.out.println("Invalid input! Please enter a positive integer!");
            }
        }
        return amountToCharge;
    }

    public int getClientNumber(int maxClients) {
        boolean validInput = false;
        int numberOfClient = 0;
        while (!validInput) {
            try {
                numberOfClient = scanner.nextInt();
                if (numberOfClient>0&&numberOfClient<maxClients){
                    validInput = true;
                }
            } catch (Exception e) {
                scanner.next();
            }
            finally {
                if (!validInput)
                {
                    System.out.println("Invalid Input!! Please enter a number between 1 to " + maxClients);
                }
            }
        }
        return (numberOfClient-1);
    }

    public boolean checkAmount(int number) {
        return (number > 0);
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
        /*
        for (Client client : bank.getClients()) {
            printOneClientInfo(client);
        }
*/
    }

    public int getUserChoice(boolean fileInSystem) {
        boolean validInput= false;
        int usersChoice = 0;
        while (!validInput) {
            try {
                usersChoice = scanner.nextInt();
                if (usersChoice < 1 || usersChoice > 8) {
                    System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                }
                else if (usersChoice != 1 && !fileInSystem) {
                    System.out.println("Invalid input, There is no file scanned. Please choose again.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Please enter an integer between 1 - 8.");
                scanner.next();
            }
        }
        return usersChoice;
    }
}