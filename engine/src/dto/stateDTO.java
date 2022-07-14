package dto;

import client.Client;
import loan.Loan;

import java.util.Map;
import java.util.Set;

public class stateDTO {
    private Set<String> categories;
    private Map<String, Loan> activeLoans;
    private Map<String, Loan> waitingLoans;
    private Map<String, Client> clients;
    private int time;


    public stateDTO(Set<String> categories,Map<String,Loan> activeLoans,
                    Map<String, Loan> waitingLoans,Map<String, Client> clients,int time){
        this.categories = categories;
        this.activeLoans = activeLoans;
        this.clients = clients;
        this.waitingLoans = waitingLoans;
        this.time = time;
    }


}
