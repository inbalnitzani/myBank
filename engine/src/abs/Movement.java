package abs;

public class Movement {
    public enum kindOfMovement {INSERT_MONEY, WITHDRAW};
    public static final int INSERT_MONEY = 1;
    public static final int PAY_MONEY = -1;

    private int executeTime;
    private double amountBeforeMovement;
    private double amountAfterMovement;
    private kindOfMovement kindOfExecute;
    //ctor
    public Movement(double currBalance, double amountChange, int time) {
        executeTime = time;
        amountBeforeMovement = currBalance;
        amountAfterMovement = amountBeforeMovement + amountChange;
        if (amountChange < 0)
            kindOfExecute = kindOfMovement.WITHDRAW;
        else
            kindOfExecute = kindOfMovement.INSERT_MONEY;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public double getAmountBeforeMovement() {
        return amountBeforeMovement;
    }

    public double getAmountAfterMovement() {
        return amountAfterMovement;
    }

    public double getAmount(){return amountAfterMovement-amountBeforeMovement;}

    public String getKindOfExecute() {
        if (kindOfExecute == kindOfMovement.INSERT_MONEY)
            return "Insert Money";
        else
            return "Pay Money";
    }



}
