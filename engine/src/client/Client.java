package client;

import bank.Global;
import loan.Loan;
import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private String fullName;
    private int currBalance;
    private List<Loan> asLender;
    private List<Loan> asBorrower;
    private Map<Integer, List<Movement>> movements;

    public Client(String name, int accountBalance) {
        fullName = name;
        currBalance = accountBalance;
        asLender = new ArrayList<>();
        asBorrower = new ArrayList<>();
        movements = new HashMap<>();
    }

    public void addLoanToBorrowerList(Loan loan) {
        asBorrower.add(loan);
    }

    public void addMovement(Movement oneMove) {
        int time = oneMove.getExecuteTime();
        if (movements.containsKey(time)) {
            movements.get(time).add(oneMove);
        } else {
            List<Movement> moves = new ArrayList<>();
            moves.add(oneMove);
            movements.put(time, moves);
        }
    }

    public void addLoanToInvestor(Loan loan, int amountInvested, boolean isAlreadyInvolve) {
        if (!isAlreadyInvolve) {
            asLender.add(loan);
        }
        addMovement(new Movement(currBalance, -amountInvested, Global.worldTime));
        currBalance = currBalance - amountInvested;
    }

    public String getFullName() {
        return fullName;
    }

    public int getCurrBalance() {
        return currBalance;
    }

    public List<Loan> getLoanListAsGiver() {
        return asLender;
    }

    public List<Loan> getLoanListAsBorrower() {
        return asBorrower;
    }

    public void addMoneyToAccount(double moneyToAdd) {
        Movement movement = new Movement(currBalance, moneyToAdd, Global.worldTime);
        addMovement(movement);
        currBalance += moneyToAdd;
    }

    public void withdrawingMoney(double sumToPull) {
        addMovement(new Movement(currBalance, -sumToPull, Global.worldTime));
        currBalance -= sumToPull;
    }

    public Map<Integer, List<Movement>> getMovements() {
        return movements;
    }

}
