package file;
import exception.*;
import loan.Loan;
import schema.AbsCustomer;
import schema.AbsLoan;

import java.util.*;

public class File {

    public void checkFile(Collection<String> categories, Collection<AbsLoan> loans, Collection<AbsCustomer> clients, String fileName) throws CategoriesException, NamesException, CustomerException, XmlException, PaceException, IdException {
        isXmlFile(fileName);
        checkCategories(categories,loans);
        checkCustomer(clients,loans);
        checkNames(clients);
        checkPace(loans);
        checkID(loans);

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


    public void checkCategories(Collection<String> categories,Collection<AbsLoan> loans) throws CategoriesException {
       boolean valid;
        for (AbsLoan loan:loans) {
            valid = false;
            String curCategory = loan.getAbsCategory();
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
    public void checkPace(Collection<AbsLoan> loans) throws PaceException {
        for (AbsLoan loan:loans) {
           if((loan.getAbsTotalYazTime() % loan.getAbsPaysEveryYaz()) != 0)
            throw new PaceException(loan);
        }
    }
    public void isXmlFile(String fileName) throws XmlException {
        if(!fileName.endsWith(".xml"))
            throw new XmlException(fileName);
    }
}
