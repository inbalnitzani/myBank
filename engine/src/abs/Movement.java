package abs;

public class Movement {
    public enum kindOfMovement {INSERT_MONEY, PAY_MONEY};
    public static final int INSERT_MONEY = 1;
    public static final int PAY_MONEY = -1;

    private int executeTime;
    private int amountBeforeMovement;
    private int amountAfterMovement;
    private kindOfMovement kindOfExecute;
    //ctor
    public Movement(int currBalance, int amountChange, int time) {
        executeTime = time;
        amountBeforeMovement = currBalance;
        amountAfterMovement = amountBeforeMovement + amountChange;
        if (amountChange < 0)
            kindOfExecute = kindOfMovement.PAY_MONEY;
        else
            kindOfExecute = kindOfMovement.INSERT_MONEY;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public int getAmountBeforeMovement() {
        return amountBeforeMovement;
    }

    public int getAmountAfterMovement() {
        return amountAfterMovement;
    }

    public int getAmount(){return amountAfterMovement-amountBeforeMovement;}

    public String getKindOfExecute() {
        if (kindOfExecute == kindOfMovement.INSERT_MONEY)
            return "Insert Money";
        else
            return "Pay Money";
    }



}
