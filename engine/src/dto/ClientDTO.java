package dto;

import client.Client;
import loan.Loan;
import client.Movement;

import java.util.*;

public class ClientDTO {
    private String fullName;
    private int currBalance;
    private List<LoanDTO> asGiver;
    private List<LoanDTO> asBorrower;
    private Map<Integer, List<MovementDTO>> movements;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
        this.currBalance = client.getCurrBalance();
        this.asBorrower=createLoanDTOList(client.getLoanListAsBorrower());
        this.asGiver=createLoanDTOList(client.getLoanListAsGiver());
        setMovements(client.getMovements());
    }

    private void setMovements(Map<Integer,List<Movement>>movements) {
        this.movements = new HashMap<>();
        if (movements != null) {
            for (Collection<Movement> set : movements.values()) {
                addSetToMovements(set);
            }
        }
    }
    private void addSetToMovements(Collection<Movement> set) {
        int time=0,size = set.size();
        List<MovementDTO> movementDTOS = new ArrayList<>();
        for (Movement movement : set) {
            movementDTOS.add(new MovementDTO(movement.getAmountBeforeMovement(), movement.getAmount(), movement.getExecuteTime()));
            time=movement.getExecuteTime();
        }
        this.movements.put(time,movementDTOS);
    }
    private List<LoanDTO> createLoanDTOList(List<Loan> loans){
        List<LoanDTO> loanDTOS = new ArrayList<LoanDTO>();
        for (Loan loan : loans) {
            loanDTOS.add(new LoanDTO(loan));
        }
        return loanDTOS;
    }
    public String getFullName() {
        return fullName;
    }
    public List<MovementDTO> getMovementsByTime(int executeTime) {
        if (movements.containsKey(executeTime))
            return movements.get(executeTime);
        else
            return null;
    }
    public Map<Integer, List<MovementDTO>> getMovements() {
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
