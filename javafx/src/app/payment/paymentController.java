package app.payment;

import app.bodyUser.bodyUser;
import dto.ClientDTO;
import dto.LoanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Status;

import java.util.Collection;
import java.util.List;

public class paymentController {
private bodyUser bodyUser;
private ClientDTO client;
@FXML private TableView<LoanDTO> loanerLoans;
@FXML private ChoiceBox<String> choosePayment;

@FXML private ListView<String> notificationList;
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
    public void showData(){
        List<LoanDTO> loans = client.getLoansAsBorrower();
        showLonersLoans(loans);
        showNotifications();
        showPaymentsControl(loans);
    }
    public void showNotifications(){
        notificationList.getItems().add("hiiii");
    }
    public void showPaymentsControl(Collection<LoanDTO> loans){
        for (LoanDTO loan:loans)
            if(loan.getStatus().equals(Status.ACTIVE))
              choosePayment.getItems().add(loan.getLoansID());
    }

    public void showLonersLoans(Collection<LoanDTO> loans) {

        loanerLoans.getColumns().clear();
        ObservableList<LoanDTO> loansData = FXCollections.observableArrayList();
        loansData.addAll(loans);

        TableColumn<LoanDTO, String> idCol = new TableColumn<>("ID loan");
        TableColumn<LoanDTO, String> categoryCol = new TableColumn<>("Category");
        TableColumn<LoanDTO, Integer> capitalCol = new TableColumn<>("Capital");
        TableColumn<LoanDTO, Integer> totalTimeCol = new TableColumn<>("Total time");
        TableColumn<LoanDTO, Integer> interestCol = new TableColumn<>("Interest");
        TableColumn<LoanDTO, Integer> paceCol = new TableColumn<>("Payment pace");
        TableColumn<LoanDTO, String> statusCol = new TableColumn<>("Status");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));
        totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalYazTime"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        loanerLoans.getColumns().addAll(idCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loanerLoans.setItems(loansData);

    }
}
