import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("hello please insert a number:");
        Scanner sc= new Scanner(System.in);
        int a=sc.nextInt();
        System.out.println(a);
        firstTalk hi= new firstTalk();
       a= hi.func(a);
System.out.println(a);

//////////////////////////////////////////////
        giver giv = new giver();
        giv.setName("givgiv");
        taker take = new taker();
        take.setName("toktok");
        loan myLoan = new loan(take,10,2);



    }

}
