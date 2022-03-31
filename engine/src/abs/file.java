package abs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class file {
    public boolean checkCategories(Collection<String> categories,Collection<Loan> loans){
       boolean valid;
        for (Loan loan:loans) {
            valid = false;
            String curCategory = loan.getCategory();
            for (String category:categories) {
                if (category.equals(curCategory))
                    valid = true;
            }
            if (valid == false)
                return false;
        }
        return true;
    }
    public boolean checkNames(Collection<Client> clients){
        Set<String> names = new HashSet<>();
        String name;
        for (Client client:clients) {
            name = client.getFullName();
           if (names.contains(name))
               return false;
           else {
               names.add(name);
           }
        }
        return true;
    }
    public boolean checkCustomer(Collection<Client> customers,Collection<Loan> loans){
        boolean valid;
        for (Loan loan:loans) {
            valid = false;
            String customer = loan.getOwnersName();
            for (Client curCustomer:customers) {
                if (curCustomer.getFullName().equals(customer))
                    valid = true;
            }
            if (valid == false)
                return false;
        }
        return true;    }
    public boolean checkPace(Collection<Loan> loans){
        for (Loan loan:loans) {
            if(loan.getTotalTU() % loan.getPace() != 0)
                return false;
        }
    }

}
