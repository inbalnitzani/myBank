package abs;

import java.util.*;

public class Bank {

    private Set<Loan> loans;
    private List<Client> clients;

    //CTOR
    public Bank() {
        clients = new ArrayList<Client>();
        //  loans = new HashSet<Loan>();
    }

    //GETTERS
    public List<Client> getClients() {
        return this.clients;
    }

    public Set<Loan> getLoans() {
        return this.loans;
    }


}
