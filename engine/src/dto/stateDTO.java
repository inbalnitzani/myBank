package dto;

import java.util.List;


public class stateDTO {
    private List <LoanDTO> loans;
    private List<ClientDTO> clients;


    public stateDTO(List<LoanDTO> loans,List<ClientDTO> clients){
        this.loans = loans;
        this.clients = clients;
    }

    public List<ClientDTO> getClients() {
        return clients;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }
}
