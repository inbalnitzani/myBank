package abs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client {
    private String fullName;
    private int currBalance;
    private Set<Loan> asGiver;
    private Set<Loan> asBorrower;
    private Map<Integer, Set<Movement>> movements;

    public Client(String name, int accountBalance) {
        fullName = name;
        currBalance = accountBalance;
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

    public int getCurrBalance(){return currBalance;}

    public Set<Loan> getLoanSetAsGiver() {
            return asGiver;
    }

    public Set<Loan> getLoanSetAsBorrower() {
        return asBorrower;
    }
    public void addMoneyToAccount(int moneyToAdd) {
        Movement movement = new Movement(currBalance, moneyToAdd, 1/*worldTime*/);
        addMovement(movement);
        currBalance += moneyToAdd;
    }

    public int WithdrawingMoney(int sumToPull) {
        currBalance -= sumToPull;
        return sumToPull;
    }

    public  Map<Integer, Set<Movement>> getMovements(){return movements;}
}