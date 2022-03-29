package abc;

import abs.Bank;
import abs.Client;
import abs.DTO.LoanDTO;
import abs.Loan;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Bank bank=new Bank();
        Loan y=new Loan(new Client("mai",200),200,50);
        LoanDTO x=new LoanDTO(y);
        Loan mai=bank.changeLoanDtoToLoan(x);
        manager.manageSystem();
    }
}
