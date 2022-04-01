package abs.exception;

import abs.DTO.LoanDTO;

public class PaceException extends Exception {
   private LoanDTO loan;
   public PaceException(LoanDTO loan){
       this.loan = loan;
   }
   public String getLoansID(){
       return loan.getLoansID();
   }
   public int getLoansPace(){
       return loan.getPace();
   }
   public int getLoansTotalYaz(){
       return loan.getTotalYazTime();
   }

}
