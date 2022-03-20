import java.util.Map;
import java.util.Set;

public class client {
    private String fullName;
    private int currBalance;
    private Set<loan> asGiver;
    private Set<loan> asBorrower;
    private Map<Integer,Set<movement>> movements;

    public client(String name, int accountBalance) {
        fullName=name;
        currBalance=accountBalance;
    }

    protected void setName(String name){
        this.fullName = name;
    };

}
