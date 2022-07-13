package dto;

import client.Client;
import client.Movement;
import loan.Loan;

import java.util.*;

public class ConvertDTO {

    public List<LoanDTO> createListLoanDto(Collection<Loan> loans) {
        List<LoanDTO> loanDTOS = new ArrayList<LoanDTO>();
        for (Loan loan : loans) {
            loanDTOS.add(new LoanDTO(loan));
        }
        return loanDTOS;
    }

    public List<ClientDTO> createListClientDTO(Collection<Client> clients) {
        List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
        for (Client client : clients) {
            clientDTO.add(new ClientDTO(client));
        }
        return clientDTO;
    }

    public List<String> createListCategories(Collection<String> categories) {
        List<String> categoriesDTO = new ArrayList<String>();
        for (String category : categories) {
            categoriesDTO.add(category);
        }
        return categoriesDTO;
    }

    public List<Loan> createListLoan(List<LoanDTO> loanDTOList, Map<String,Loan> waitingLoans) {
        List<Loan> loansToInvest = new ArrayList<Loan>();
        for (LoanDTO loanDTO : loanDTOList) {
            loansToInvest.add(waitingLoans.get(loanDTO.getLoansID()));
        }
        return loansToInvest;
    }


}
