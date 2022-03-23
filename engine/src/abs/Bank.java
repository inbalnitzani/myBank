package abs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bank {
    //DATA MEMBERS
    private Set <Loan> loans;
   private Set <Client> clients;

    //CTOR
    public Bank(){
       clients = new HashSet<Client>();
      //  loans = new HashSet<Loan>();
    }

    //GETTERS
    public Collection<Client> getClients(){
        return this.clients;
    }

    public Collection<Loan> getLoans(){
        return this.loans;
    }

}