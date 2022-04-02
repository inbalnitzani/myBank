package abs;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.exception.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class File {

    public void checkFile(Collection<String> categories, Collection<LoanDTO> loans, Collection<ClientDTO> clients,String fileName) throws CategoriesException, NamesException, CustomerException, XmlException, PaceException {
        isXmlFile(fileName);
        checkCategories(categories,loans);
        checkCustomer(clients,loans);
        checkNames(clients);
        checkPace(loans);

    }

    public void checkCategories(Collection<String> categories,Collection<LoanDTO> loans) throws CategoriesException {
       boolean valid;
        for (LoanDTO loan:loans) {
            valid = false;
            String curCategory = loan.getCategory();
            for (String category:categories) {
                if (category.equals(curCategory))
                    valid = true;
            }
            if (valid == false) {
                throw new CategoriesException(categories,loan);
            }
        }
    }
    public void checkNames(Collection<ClientDTO> clients) throws NamesException {
        Set<String> names = new HashSet<>();
        String name;
        for (ClientDTO client:clients) {
            name = client.getFullName();
           if (names.contains(name))
               throw new NamesException(name);
           else {
               names.add(name);
           }
        }
    }
    public void checkCustomer(Collection<ClientDTO> customers,Collection<LoanDTO> loans) throws CustomerException {
        boolean valid;
        for (LoanDTO loan : loans) {
            valid = false;
            String customer = loan.getOwner();
            for (ClientDTO curCustomer : customers) {
                if (curCustomer.getFullName().equals(customer))
                    valid = true;
            }
            if (valid == false)
                throw new CustomerException(customers,loan);
        }
    }
    public void checkPace(Collection<LoanDTO> loans) throws PaceException {
        for (LoanDTO loan:loans) {
           if(loan.getTotalYazTime() % loan.getPace() != 0)
            throw new PaceException(loan);
        }
    }
    public void isXmlFile(String fileName) throws XmlException {
        if(!fileName.endsWith(".xml"))
            throw new XmlException(fileName);

    }
}
