package abs.exception;

import abs.DTO.LoanDTO;

public class PaceException extends FileException {
   private LoanDTO loan;
   public PaceException(LoanDTO loan){
       this.loan = loan;
   }
   /*
   public String getLoansID(){
       return loan.getLoansID();
   }
   public int getLoansPace(){
       return loan.getPace();
   }
   public int getLoansTotalYaz(){
       return loan.getTotalYazTime();
   }
*/
   @Override
    public String toString(){
       return "The loan " +'"'+ loan.getLoansID()+'"' +" shows a rate of payments that is not fully divided by the total yaz of the loan" +'\n'+
               "The rate is :" +loan.getPace() + '\n' +
               "total yaz is: "+loan.getTotalYazTime();

   }
}
