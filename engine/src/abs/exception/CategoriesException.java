package abs.exception;

import abs.DTO.LoanDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoriesException extends FileException{
   private List <String> categories = new ArrayList<>();
   private LoanDTO loan;
        public CategoriesException(Collection <String> categories, LoanDTO loan){
            for (String category:categories) {
                this.categories.add(category);
            }
            this.loan = loan;
        }
        /*
        public String getLoansID(){
            return loan.getLoansID();
        }
        public String getLoansCategory(){
            return loan.getCategory();
        }
        public List<String> getCategories(){
            return categories;
        }
         */
        @Override
        public String toString() {

            return "The loan :" + loan.getLoansID() + '\n' +
                    "contains a category that is not included in the list of categories: " + loan.getCategory() + '\n' +
                    "These are the optional categories:" + '\n' + categories;
        }

}
