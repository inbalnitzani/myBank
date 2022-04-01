package abs.exception;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.Loan;

import java.util.Collection;
import java.util.List;

public class CustomerException extends Exception{
  private List<String> names;
  private LoanDTO loan;
    public CustomerException(Collection<ClientDTO> customers, LoanDTO loan){
        this.loan = loan;
        for (ClientDTO curName:customers) {
            names.add(curName.getFullName());
        }
    }
    public String getLoansID(){
        return loan.getLoansID();
    }
    public String getLoansOwner(){
        return loan.getOwner();
    }
    public List<String> getNamesList(){
        return names;
    }

}
