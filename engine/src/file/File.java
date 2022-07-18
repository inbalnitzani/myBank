package file;
import exception.*;
import loan.Loan;
import schema.AbsCustomer;
import schema.AbsLoan;

import java.util.*;

public class File {
    public void checkFile(Collection<String> categories, Collection<AbsLoan> loans, String fileName,Map<String, Loan> waitingLoans, Map<String, Loan> activeLoans) throws Exception {
        isXmlFile(fileName);
        checkCategories(categories, loans);
        checkTime(loans);
        checkNumbers(loans);
        checkInterest(loans);
        checkID(loans,waitingLoans,activeLoans);
    }

    public void checkInterest(Collection<AbsLoan> loans) throws InterestException {
        for (AbsLoan loan : loans) {
            int interest = loan.getAbsIntristPerPayment();
            if (interest > 100 || interest < 0)
                throw new InterestException(interest, loan.getId());
        }
    }

    public void checkNumbers(Collection<AbsLoan> loans) throws NegativeLoanCapitalException {
        for (AbsLoan loan : loans) {
            if (loan.getAbsCapital() < 0) {
                throw new NegativeLoanCapitalException(loan.getId(), loan.getAbsCapital());
            }
        }
    }

    public void checkCategories(Collection<String> categories, Collection<AbsLoan> loans) throws CategoriesException {
        boolean valid;
        for (AbsLoan loan : loans) {
            valid = false;
            String curCategory = loan.getAbsCategory().trim();
            for (String category : categories) {
                if (category.equalsIgnoreCase(curCategory))
                    valid = true;
            }
            if (valid == false) {
                throw new CategoriesException(categories, loan);
            }
        }
    }

    public void checkID(Collection<AbsLoan> loans, Map<String, Loan> waitingLoans, Map<String, Loan> activeLoans) throws Exception {
        List<String> IDs = new ArrayList<>();
        for (AbsLoan loan : loans) {
            String id= loan.getId().trim();
            Loan loan1 =waitingLoans.get(id);
            Loan loan2 = activeLoans.get(id);
            if(loan1!=null || loan2!=null)
                throw new Exception("Loan id is already exist!");
            else {
                IDs.add(id);
            }
        }
        for (AbsLoan loan : loans) {
            int counter = 0;
            String curID = loan.getId();
            for (String id : IDs) {
                if (id.equalsIgnoreCase(curID.trim()))
                    counter++;
            }
            if (counter > 1)
                throw new IdException(curID);
        }
    }

    public void checkTime(Collection<AbsLoan> loans) throws PaceException, NegativeTimeException {
        for (AbsLoan loan : loans) {
            if ((loan.getAbsTotalYazTime() % loan.getAbsPaysEveryYaz()) != 0)
                throw new PaceException(loan);
            if (loan.getAbsPaysEveryYaz() < 0 || loan.getAbsTotalYazTime() < 0)
                throw new NegativeTimeException(loan.getId());
        }
    }

    public void isXmlFile(String fileName) throws XmlException {
        if (!fileName.endsWith(".xml"))
            throw new XmlException(fileName);
    }
}