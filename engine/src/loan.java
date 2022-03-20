import java.util.List;

public class loan {
    private taker loanTaker;
    private List <giver> givers;
    private List <payment> payments;
    private double originalSum;
    private int status;
    private int pace;
    private int interestRate;
    private int category;

    loan(taker newTaker, int sum,int rate){
        this.loanTaker = newTaker;
        this.originalSum = sum;
        this.interestRate = rate;

    }

   private String getTakersName(){
        return loanTaker.getFullName();
    };
    private int getStatus(){
        return status;
    }
   private int getCategory(){
        return status;
    }
   public void printLoansInfo(){
        System.out.println("loan's info:");
        System.out.println("loan's owner is:");
        System.out.println(this.getTakersName());
        System.out.println("loan's status is:");
        System.out.println(this.getStatus());
    }



}
