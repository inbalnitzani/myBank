import java.util.Scanner;

public class main {
    static int timeInWorld =1;
    public static void main(String[] args) {
        menu menu = new menu();
        bank myBank = new bank();

        menu.printMenu();
        menu.getUsersChoise(myBank);
//        System.out.println("hello please insert a number:");
//        Scanner sc= new Scanner(System.in);
//        int a=sc.nextInt();
//        System.out.println(a);
//        firstTalk hi= new firstTalk();
//       a= hi.func(a);
//System.out.println(a);
//
//client mai = new client("mai");


    }

}
