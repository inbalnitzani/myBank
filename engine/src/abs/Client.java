package abs;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private String fullName;
    private int currBalance;
    private List<Loan> asGiver;
    private List<Loan> asBorrower;
    private Map<Integer, Set<Movement>> movements;

    public Client(String name, int accountBalance) {
        fullName = name;
        currBalance = accountBalance;
        asGiver = new ArrayList<>();
        asBorrower = new ArrayList<>();
        movements=new HashMap<>();
    }

    protected void setName(String name) {
        this.fullName = name;
    }

    public void addMovement(Movement oneMove) {
        int time = oneMove.getExecuteTime();
        if (movements.containsKey(time)) {
            movements.get(time).add(oneMove);
        } else {
            Set<Movement> moves = new HashSet<Movement>();
            moves.add(oneMove);
            movements.put(time, moves);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Movement> getMovementsByTime(int executeTime) {
        if (movements.containsKey(executeTime))
            return movements.get(executeTime);
        else
            return null;
    }

    public int getCurrBalance() {
        return currBalance;
    }

    public List<Loan> getLoanListAsGiver() {
        return asGiver;
    }

    public List<Loan> getLoanListAsBorrower() {
        return asBorrower;
    }

    public void addMoneyToAccount(int moneyToAdd) {
        Movement movement = new Movement(currBalance, moneyToAdd, Globals.worldTime);
        addMovement(movement);
        currBalance += moneyToAdd;
    }

    public int WithdrawingMoney(int sumToPull) {
        Movement movement = new Movement(currBalance, -sumToPull, Globals.worldTime);
        addMovement(movement);
        currBalance -= sumToPull;
        return sumToPull;
    }

    public Map<Integer, Set<Movement>> getMovements() {
        return movements;
    }

    public void setAsGiver(Loan loan) {
        asGiver.add(loan);
    }

    public void subtractCurrBalance(double amountToDeducted) {
        currBalance -= amountToDeducted;
    }
    public void addToCurrBalance(double amountToAdd) {
        currBalance += amountToAdd;
    }

}
