package utils;

import client.Movement;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;

import java.util.List;
import java.util.Map;

public class infoForClient {
    private List<String> categories;
    private Double balance;
    private int yaz;
    private Map<Integer,List<MovementDTO>> movements;

    public infoForClient(List<String> categories, Double balance, int yaz,Map<Integer,List<MovementDTO>> movements){
        this.balance=balance;
        this.categories=categories;
        this.yaz=yaz;
        this.movements=movements;
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
}
