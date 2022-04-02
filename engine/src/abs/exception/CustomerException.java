package abs.exception;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.Loan;
import abs.schemaClasses.AbsCustomer;
import abs.schemaClasses.AbsLoan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerException extends FileException{
  private List<String> names = new ArrayList<>();
  private AbsLoan loan;
    public CustomerException(Collection<AbsCustomer> customers, AbsLoan loan){
        this.loan = loan;
        for (AbsCustomer curName:customers) {
            names.add(curName.getName());
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
        return "The loan " +'"'+ loan.getId()+'"' + " contains an owner that is not included in the list of customers:" + '\n' +
        "The owner is" + loan.getAbsOwner() + '\n' +
        "These are the names in our system:" + names;
    }

}
