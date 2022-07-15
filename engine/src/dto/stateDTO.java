package dto;

import java.util.List;


public class stateDTO {
    private List <LoanDTO> loans;
    private List<ClientDTO> clients;
    private int time;


    public stateDTO(List<LoanDTO> loans,List<ClientDTO> clients,int time){
        this.loans = loans;
        this.clients = clients;
        this.time = time;
    }

    public List<ClientDTO> getClients() {
        return clients;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }
}
