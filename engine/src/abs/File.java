package abs;

import abs.DTO.ClientDTO;
import abs.DTO.LoanDTO;
import abs.schemaClasses.AbsDescriptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class File {

    public boolean checkFile(Collection<String> categories, Collection<LoanDTO> loans, Collection<ClientDTO> clients){
        return (checkCategories(categories,loans)&&checkCustomer(clients,loans)&&checkNames(clients)&&checkPace(loans));
    }

    public boolean checkCategories(Collection<String> categories,Collection<LoanDTO> loans){
       boolean valid;
        for (LoanDTO loan:loans) {
            valid = false;
            String curCategory = loan.getCategory();
            for (String category:categories) {
                if (category.equals(curCategory))
                    valid = true;
            }
            if (valid == false) {
             //   throw
                return false;
            }
        }
        return true;
    }
    public boolean checkNames(Collection<ClientDTO> clients){
        Set<String> names = new HashSet<>();
        String name;
        for (ClientDTO client:clients) {
            name = client.getFullName();
           if (names.contains(name))
               return false;
           else {
               names.add(name);
           }
        }
        return true;
    }
    public boolean checkCustomer(Collection<ClientDTO> customers,Collection<LoanDTO> loans){
        boolean valid;
        for (LoanDTO loan:loans) {
            valid = false;
            String customer = loan.getOwner();
            for (ClientDTO curCustomer:customers) {
                if (curCustomer.getFullName().equals(customer))
                    valid = true;
            }
            if (valid == false)
                return false;
        }
        return true;    }
    public boolean checkPace(Collection<LoanDTO> loans){
        for (LoanDTO loan:loans) {
            if(loan.getTotalYazTime() % loan.getPace() != 0)
                return false;
        }
        return true;
    }
    public boolean isXmlFile(String fileName){
        int index = fileName.indexOf(".");
        return fileName.substring(index + 1).equals("xml");
    }
}
