package abc;

import abs.Bank;

public class Main {
    static int timeInWorld =1;
    public static void main(String[] args) {
        Bank myBank = new Bank();
        Menu menu = new Menu(myBank);

        menu.printMenu();
        menu.getUsersChoice();
//        System.out.println("hello please insert a number:");
//        Scanner sc= new Scanner(System.in);
//        int a=sc.nextInt();
//        System.out.println(a);
//        abs.firstTalk hi= new abs.firstTalk();
//       a= hi.func(a);
//System.out.println(a);
//
//abs.client mai = new abs.client("mai");


    }

}
