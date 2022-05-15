package exception;

import schema.AbsLoan;

public class PaceException extends FileException {
   private AbsLoan loan;
   public PaceException(AbsLoan loan){
       this.loan = loan;
   }

   @Override
    public String toString(){
       return "The loan " +'"'+ loan.getId()+'"' +" shows a rate of payments that is not fully divided by the total yaz of the loan" +'\n'+
               "The rate is :" +loan.getAbsPaysEveryYaz() + '\n' +
               "total yaz is: "+loan.getAbsTotalYazTime();

   }
}
