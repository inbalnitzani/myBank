package dto;

import java.util.List;
import java.util.Map;

public class infoForAdminDTO {
    List<ClientDTO> clientsInfo;
    List<LoanDTO> loansInfo;

    public infoForAdminDTO(List<ClientDTO> clients, List<LoanDTO> loans){
        clientsInfo=clients;
        loansInfo=loans;
    }

    public List<LoanDTO> getLoansInfo() {
        return loansInfo;
    }

    public List <ClientDTO> getClientsInfo() {
        return clientsInfo;
    }
}
