package utils;

import client.Movement;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import dto.clientStateDTO;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class infoForClient {
    private List<String> categories;
    private Double balance;
    private int yaz;
    private Map<Integer,List<MovementDTO>> movements;
    private List<LoanDTO> loanLender;
    private List<LoanDTO> loanLoner;
    private int version;
    private Integer lookingBack;

    public infoForClient(List<String> categories, Double balance, int yaz, Map<Integer,List<MovementDTO>> movements, List<LoanDTO> loanLender, List<LoanDTO> loanLoner, int version, Integer lookingBack){
        this.balance=balance;
        this.categories=categories;
        this.yaz=yaz;
        this.movements=movements;
        this.loanLender=loanLender;
        this.loanLoner=loanLoner;
        this.version=version;
        this.lookingBack = lookingBack;
    }

    public int getVersion() {
        return version;
    }

    public Map<Integer, List<MovementDTO>> getMovements() {
        return movements;
    }

    public Double getBalance() {
        return balance;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getYaz() {
        return yaz;
    }

    public List<LoanDTO> getLoanLender() {
        return loanLender;
    }

    public List<LoanDTO> getLoanLoner() {
        return loanLoner;
    }

    public Integer getLookingBack(){ return lookingBack; }
}
