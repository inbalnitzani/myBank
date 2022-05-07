package file;
import exception.*;
import schema.AbsCustomer;
import schema.AbsLoan;

import java.util.*;

public class File {

    public void checkFile(Collection<String> categories, Collection<AbsLoan> loans, Collection<AbsCustomer> clients, String fileName) throws CategoriesException, NamesException, CustomerException, XmlException, PaceException, NegativeBalanceException, NegativeLoanCapitalException, NegativeTimeException, InterestException, IdException {
        isXmlFile(fileName);
        checkCategories(categories,loans);
        checkCustomer(clients,loans);
        checkNames(clients);
        checkTime(loans);
        checkNumbers(loans,clients);
        checkInterest(loans);
        checkID(loans);
    }
    public void checkInterest(Collection<AbsLoan> loans) throws InterestException {
        for (AbsLoan loan:loans){
            int interest=loan.getAbsIntristPerPayment();
            if(interest>100||interest<0)
                throw new InterestException(interest, loan.getId());
        }
    }
    public void checkNumbers(Collection<AbsLoan> loans, Collection<AbsCustomer> clients ) throws NegativeBalanceException, NegativeLoanCapitalException {
        for (AbsCustomer client : clients) {
            if (client.getAbsBalance() < 0) {
                throw new NegativeBalanceException(client.getName(), client.getAbsBalance());
            }
        }
        for (AbsLoan loan:loans){
            if(loan.getAbsCapital()<0){
                throw new NegativeLoanCapitalException(loan.getId(), loan.getAbsCapital());
            }
        }
    }
    public void checkCategories(Collection<String> categories,Collection<AbsLoan> loans) throws CategoriesException {
       boolean valid;
        for (AbsLoan loan:loans) {
            valid = false;
            String curCategory = loan.getAbsCategory().trim();
            for (String category:categories) {
                if (category.equalsIgnoreCase(curCategory))
                    valid = true;
            }
            if (valid == false) {
                throw new CategoriesException(categories,loan);
            }
        }
    }
    public void checkNames(Collection<AbsCustomer> clients) throws NamesException {
        List<String> names = new ArrayList<>();
        for (AbsCustomer costumer : clients) {
            names.add(costumer.getName().trim());
        }
        for (AbsCustomer customer:clients) {
            int counter = 0;
            String curName = customer.getName();
            for (String name:names) {
                if (name.equalsIgnoreCase(curName.trim()))
                    counter++;
            }
            if (counter >1)
                throw new NamesException(curName);
        }
    }
    public void checkID(Collection<AbsLoan> loans) throws IdException {
        List<String> IDs = new ArrayList<>();
        for (AbsLoan loan : loans) {
            IDs.add(loan.getId().trim());
        }
        for (AbsLoan loan:loans) {
            int counter = 0;
            String curID = loan.getId();
            for (String id:IDs) {
                if (id.equalsIgnoreCase(curID.trim()))
                    counter++;
            }
            if (counter >1)
                throw new IdException(curID);
        }
    }
    public void checkCustomer(Collection<AbsCustomer> customers,Collection<AbsLoan> loans) throws CustomerException {
        boolean valid;
        for (AbsLoan loan : loans) {
            valid = false;
            String customer = loan.getAbsOwner().trim();
            for (AbsCustomer curCustomer : customers) {
                if (curCustomer.getName().equalsIgnoreCase(customer))
                    valid = true;
            }
            if (valid == false)
                throw new CustomerException(customers,loan);
        }
    }
    public void checkTime(Collection<AbsLoan> loans) throws PaceException, NegativeTimeException {
        for (AbsLoan loan:loans) {
           if((loan.getAbsTotalYazTime() % loan.getAbsPaysEveryYaz()) != 0)
            throw new PaceException(loan);
           if (loan.getAbsPaysEveryYaz()<0 ||loan.getAbsTotalYazTime()<0)
               throw new NegativeTimeException(loan.getId());
        }
    }
    public void isXmlFile(String fileName) throws XmlException {
        if(!fileName.endsWith(".xml"))
            throw new XmlException(fileName);
    }
}
