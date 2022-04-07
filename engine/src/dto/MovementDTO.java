package dto;

import client.Movement;

public class MovementDTO {
    public static final int INSERT_MONEY = 1;
    public static final int WITHDRAW = -1;

    private int executeTime;
    private double amountBeforeMovement;
    private double amountAfterMovement;
    private int kindOfExecute;
    //ctor
    public MovementDTO(double currBalance, double amountChange, int time) {
        executeTime = time;
        amountBeforeMovement = currBalance;
        amountAfterMovement = amountBeforeMovement + amountChange;
        if (amountChange < 0)
            kindOfExecute = WITHDRAW;
        else
            kindOfExecute = INSERT_MONEY;
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
        String transfer="transfer: ";
        if (kindOfExecute == INSERT_MONEY)
            return transfer+"+";
        else
            return transfer;
    }

}
