package file;
import exception.*;
import schema.AbsCustomer;
import schema.AbsLoan;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class File {

    public void checkFile(Collection<String> categories, Collection<AbsLoan> loans, Collection<AbsCustomer> clients, String fileName) throws CategoriesException, NamesException, CustomerException, XmlException, PaceException {
        isXmlFile(fileName);
        checkCategories(categories,loans);
        checkCustomer(clients,loans);
        checkNames(clients);
        checkPace(loans);

    }
    public void checkCategories(Collection<String> categories,Collection<AbsLoan> loans) throws CategoriesException {
       boolean valid;
        for (AbsLoan loan:loans) {
            valid = false;
            String curCategory = loan.getAbsCategory();
            for (String category:categories) {
                if (category.equals(curCategory))
                    valid = true;
            }
            if (valid == false) {
                throw new CategoriesException(categories,loan);
            }
        }
    }
    public void checkNames(Collection<AbsCustomer> clients) throws NamesException {
        Set<String> names = new HashSet<>();
        String name;
        for (AbsCustomer client:clients) {
            name = client.getName();
           if (names.contains(name))
               throw new NamesException(name);
           else {
               names.add(name);
           }
        }
    }
    public void checkCustomer(Collection<AbsCustomer> customers,Collection<AbsLoan> loans) throws CustomerException {
        boolean valid;
        for (AbsLoan loan : loans) {
            valid = false;
            String customer = loan.getAbsOwner();
            for (AbsCustomer curCustomer : customers) {
                if (curCustomer.getName().equals(customer))
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
