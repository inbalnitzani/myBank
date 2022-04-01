package abs.exception;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.Loan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerException extends FileException{
  private List<String> names = new ArrayList<>();
  private LoanDTO loan;
    public CustomerException(Collection<ClientDTO> customers, LoanDTO loan){
        this.loan = loan;
        for (ClientDTO curName:customers) {
            names.add(curName.getFullName());
        }
    }/*
    public String getLoansID(){
        return loan.getLoansID();
    }
    public String getLoansOwner(){
        return loan.getOwner();
    }
    public List<String> getNamesList(){
        return names;
    }
    */
    @Override
    public String toString(){
        return "The loan " +'"'+ loan.getLoansID()+'"' + " contains an owner that is not included in the list of customers:" + '\n' +
        "The owner is" + loan.getOwner() + '\n' +
        "These are the names in our system:" + names;
    }

}
