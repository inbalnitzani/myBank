package abs.DTO;

import abs.Movement;

public class MovementDTO {
    public enum kindOfMovement {INSERT_MONEY, PAY_MONEY};
    public static final int INSERT_MONEY = 1;
    public static final int PAY_MONEY = -1;

    private int executeTime;
    private double amountBeforeMovement;
    private double amountAfterMovement;
    private Movement.kindOfMovement kindOfExecute;
    //ctor
    public MovementDTO(double currBalance, double amountChange, int time) {
        executeTime = time;
        amountBeforeMovement = currBalance;
        amountAfterMovement = amountBeforeMovement + amountChange;
        if (amountChange < 0)
            kindOfExecute = Movement.kindOfMovement.WITHDRAW;
        else
            kindOfExecute = Movement.kindOfMovement.INSERT_MONEY;
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
        if (kindOfExecute == Movement.kindOfMovement.INSERT_MONEY)
            return "Insert Money";
        else
            return "Pay Money";
    }

}
