package abc;

import abs.Bank;

public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank();
        Menu menu = new Menu(myBank);

        menu.printMenu();
        menu.getUsersChoice();
    }

}
