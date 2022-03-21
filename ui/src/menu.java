import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

public class menu{
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

    public void getUsersChoise(bank myBank){
       Scanner sc= new Scanner(System.in);
        int usersChoise=sc.nextInt();
        switch (usersChoise) {
            case 1:
                System.out.println("1. read a file");
                break;
            case 2:
                System.out.println("2. get loans information");
                printLoansInfo(myBank.getLoans());
                break;
            case 3:
                System.out.println("3. get clients information");
                printClientsInfo(myBank);
                break;
            case 4:
                System.out.println("4. load money to account");
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

    public void printOneClientInfo(client client) {
        System.out.println(client.getFullName() + ":");
        System.out.println("Client account movements:");
        for (int i = 0; i < globals.worldTime; i++) {
            Set<movement> movements = client.getMovementsByTime(i);
            if (movements != null) {
                printMovementsInfo(movements);
            }
        }
        System.out.println("loans to get money:");
        System.out.println("loans give money");

    }
    public void printMovementsInfo(Set<movement> movements){
        System.out.println("Time: " + globals.worldTime + ":");
        for(movement movement:movements) {
            System.out.println("Account balance before movement: " + movement.getAmountBeforeMovement());
            System.out.println(movement.getKindOfExecute()+" " +movement.getAmount());
            System.out.println("Account balance after movement: " + movement.getAmountBeforeMovement());
        }

    }
    private void printLoansInfo(Collection <loan> loans) {
        for (loan curLoan:loans) {
           printSingleLoanInfo(curLoan);

        }
    }
    public void printSingleLoanInfo(loan curLoan){
        System.out.print("Loans ID:");
        System.out.println(curLoan.getLoansID());
        System.out.println(curLoan.getOwnersName());
        System.out.println(curLoan.getCategory());
        System.out.println(curLoan.getOriginalAmount());
        System.out.println(curLoan.getStatus());

    }
    public void printClientsInfo(bank myBank){
        for(client client:myBank.getClients()) {
            printOneClientInfo(client);
        }

    }
}


