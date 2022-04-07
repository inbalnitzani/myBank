package client;

import dto.MovementDTO;

import java.io.Serializable;

public class Movement implements Serializable {
    static final int INSERT_MONEY = 1;
    static final int WITHDRAW = 2;
    private int executeTime;
    private double amountBeforeMovement;
    private double amountAfterMovement;
    private int kindOfExecute;

    //ctor
    public Movement(double currBalance, double amountChange, int time) {
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

    public double getAmount() {
        return amountAfterMovement - amountBeforeMovement;
    }

}
