package abs.DTO;

import abs.Client;
import abs.Loan;
import abs.Movement;

import java.util.Map;
import java.util.Set;

public class ClientDTO {
    private String fullName;
    private int currBalance;
    private Set<Loan> asGiver;
    private Set<Loan> asBorrower;
    private Map<Integer, Set<Movement>> movements;

    public ClientDTO(Client client){
        this.fullName= client.getFullName();
        this.currBalance=client.getCurrBalance();
        this.asBorrower=client.getLoanSetAsBorrower();
        this.asGiver=client.getLoanSetAsGiver();
        this.movements=client.getMovements();
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

}
