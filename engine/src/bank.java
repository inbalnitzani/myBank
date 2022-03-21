import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class bank {
    //DATA MEMBERS
   private Set <client> clients;
    private Set <loan> loans;

    //CTOR
    bank(){
        clients = new HashSet<>(clients);
        loans = new HashSet(loans);
    }

    //GETTERS
    public Collection<client> getClients(){
        return this.clients;
    }

    public Collection<loan> getLoans(){
        return this.loans;
    }

}
