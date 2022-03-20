import static java.lang.Math.abs;

public class movement {
    public enum kindOfMovement {INSERT_MONEY, PAY_MONEY}

    ;
    public static final int INSERT_MONEY = 1;
    public static final int PAY_MONEY = -1;

    private int executeTime;
    private int amountBeforeMovement;
    private int kindOfExecute;
    private int amountAfterMovement;

    //ctor
    public movement(int currBalance, int amountChange, int time) {
        executeTime = time;
        amountBeforeMovement = currBalance;
        amountAfterMovement = amountBeforeMovement + amountChange;
        if (amountChange < 0)
            kindOfExecute = PAY_MONEY;
        else
            kindOfExecute = INSERT_MONEY;
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
        if (kindOfExecute == INSERT_MONEY)
            return "Insert Money";
        else
            return "Pay Money";
    }
}
