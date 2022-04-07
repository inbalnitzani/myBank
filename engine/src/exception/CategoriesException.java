package exception;

import schema.AbsLoan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoriesException extends FileException{
   private List <String> categories = new ArrayList<>();
   private AbsLoan loan;
        public CategoriesException(Collection <String> categories, AbsLoan loan){
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

            return "The loan :" + loan.getId() + '\n' +
                    "contains a category that is not included in the list of categories: " + loan.getAbsCategory() + '\n' +
                    "These are the optional categories:" + '\n' + categories;
        }

}
