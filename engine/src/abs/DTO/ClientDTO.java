package abs.DTO;

import abs.Client;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientDTO {
    private String fullName;
    private int currBalance;
    private List<LoanDTO> asGiver;
    private List<LoanDTO> asBorrower;
    private Map<Integer, Set<MovementDTO>> movements;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
        this.currBalance = client.getCurrBalance();
        //this.asBorrower=client.getLoanSetAsBorrower();
        // this.asGiver=client.getLoanSetAsGiver();
        //    this.movements = client.getMovements();
    }

    public String getFullName() {
        return fullName;
    }

    public Set<MovementDTO> getMovementsByTime(int executeTime) {
        if (movements.containsKey(executeTime))
            return movements.get(executeTime);
        else
            return null;
    }

    public Map<Integer, Set<MovementDTO>> getMovements() {
        return movements;
    }

    public int getCurrBalance() {
        return currBalance;
    }

    public List<LoanDTO> getLoansAsGiver() {
        return asGiver;
    }

    public List<LoanDTO> getLoansAsBorrower() {
        return asBorrower;
    }

}
