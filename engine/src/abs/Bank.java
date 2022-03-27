package abs;

import java.util.*;

public class Bank {

    public enum loanStatus {NEW, PENDING, ACTIVE, RISK}

    private List<String> categories;
    private TreeMap<Integer, TreeMap<Integer, Loan>> activeLoans;
    private TreeMap<Integer, TreeMap<Integer, Loan>> inRiskLoans;
    private List<Loan> newLoans;
    private List<Loan> pendingLoans;
    private List<Client> clients;
    public static int worldTime = 1;

    //CTOR
    public Bank() {
        clients = new ArrayList<Client>();
        activeLoans = new TreeMap<Integer, TreeMap<Integer, Loan>>();
        inRiskLoans = new TreeMap<Integer, TreeMap<Integer, Loan>>();
        newLoans = new ArrayList<Loan>();
        pendingLoans = new ArrayList<Loan>();
    }


    //GETTERS
    public List<Client> getClients() {
        return this.clients;
    }
public List<String> getCategories()
{
    return categories;
}

}
