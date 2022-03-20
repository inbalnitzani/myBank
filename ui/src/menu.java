import java.util.Collection;
import java.util.Scanner;
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
}


