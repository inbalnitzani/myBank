package dto;
import client.Client;
import client.Movement;

import java.util.*;

public class ClientDTO {
    private String fullName;
    private int currBalance;
    private List<LoanDTO> asLender;
    private List<LoanDTO> asBorrower;
    private int sumAsLender, sumAsBorrower ;
    private Map<Integer, List<MovementDTO>> movements;
    private List<String> notifications;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
        this.currBalance = client.getCurrBalance();
        this.asBorrower = new ConvertDTO().createListLoanDto(client.getLoanListAsBorrower());
        this.sumAsBorrower=asBorrower.size();
        this.asLender = new ConvertDTO().createListLoanDto(client.getLoanListAsGiver());
        this.sumAsLender=asLender.size();
        setMovements(client.getMovements());
        notifications=client.getNotifications();
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    private void setMovements(Map<Integer, List<Movement>> movements) {
        this.movements = new HashMap<>();
        if (movements != null) {
            for (Collection<Movement> set : movements.values()) {
                addSetToMovements(set);
            }
        }
    }

    private void addSetToMovements(Collection<Movement> set) {
        int time = 0, size = set.size();
        List<MovementDTO> movementDTOS = new ArrayList<>();
        for (Movement movement : set) {
            movementDTOS.add(new MovementDTO(movement.getAmountBeforeMovement(), movement.getAmount(), movement.getExecuteTime()));
            time = movement.getExecuteTime();
        }
        this.movements.put(time, movementDTOS);
    }

    public int getSumAsBorrower() {
        return sumAsBorrower;
    }

    public int getSumAsLender() {
        return sumAsLender;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<Integer, List<MovementDTO>> getMovements() {
        return movements;
    }

    public int getCurrBalance() {
        return currBalance;
    }

    public List<LoanDTO> getLoansAsGiver() {
        return asLender;
    }

    public List<LoanDTO> getLoansAsBorrower() {
        return asBorrower;
    }

}
