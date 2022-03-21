import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class client {
    private String fullName;
    private int currBalance;
    private Set<loan> asGiver;
    private Set<loan> asBorrower;
    private Map<Integer, Set<movement>> movements;

    public client(String name, int accountBalance) {
        fullName = name;
        currBalance = accountBalance;
    }
    protected void setName(String name) {
        this.fullName = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void addMovement(movement oneMove) {
        int time = oneMove.getExecuteTime();
        if (movements.containsKey(time)) {
            movements.get(time).add(oneMove);
        } else {
            Set<movement> moves = new HashSet<movement>();
            moves.add(oneMove);
            movements.put(time, moves);
        }
    }

    public Set<movement> getMovementsByTime(int executeTime) {
        if (movements.containsKey(executeTime))
            return movements.get(executeTime);
        else
            return null;
    }

    //
    public Set<loan> getLoanSet(String kindOfLoan) {
        if (kindOfLoan.equals("asGiver"))
            return asGiver;
        else
            return asBorrower;
    }

    public void addMoneyToAccount(int moneyToAdd){
        //create movement
        movement movement=new movement(currBalance,moneyToAdd,1/*worldTime*/);
        addMovement(movement);
        currBalance+=moneyToAdd;
    }

    public int WithdrawingMoney(int sumToPull) {
        if (sumToPull <= currBalance) {
            currBalance -= sumToPull;
            return sumToPull;
        } else return 0;

    }
}
