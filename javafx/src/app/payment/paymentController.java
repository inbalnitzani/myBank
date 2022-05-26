package app.payment;

import app.bodyUser.bodyUser;
import client.Movement;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import dto.PaymentDTO;
import exception.NotEnoughMoney;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Loan;
import loan.Payment;
import loan.Status;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class paymentController {
    private bodyUser bodyUser;
    private ClientDTO client;
    private List<LoanDTO> loans;
    @FXML private TableView<LoanDTO> loanerLoans;
    @FXML private ComboBox<String> choosePayment;
    @FXML private CheckBox payAllCheckBox;
    @FXML private Button acceptButton;
    @FXML private Label payAllLable;
    @FXML private ListView<String> notificationList;
    @FXML private Label totalAmount;

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
    public void showData(){
        loans = client.getLoansAsBorrower();
        showLonersLoans();
        showNotifications();
        showPaymentsControl();

    }
    public void showNotifications(){
        notificationList.getItems().clear();
        int time = bodyUser.mainController.getTime();
        for (int yaz = 0; yaz<= time; yaz++) {
            for (LoanDTO loan:loans) {
                Map<Integer,PaymentDTO> paymentsByYaz = loan.getPayments();
                if (paymentsByYaz.containsKey(yaz))
                    notificationList.getItems().add("Yaz: "+yaz+
                            "\nIt is time to pay back for "+'"'+loan.getId()+'"' +"\na total of: "+paymentsByYaz.get(yaz).getAmount());
            }
        }

    }

    public void showPaymentsControl(){
        choosePayment.getItems().clear();
        List<LoanDTO> active =loans.stream().filter(loanDTO -> loanDTO.getStatus()==Status.ACTIVE)
                .collect(Collectors.toList());
        List<LoanDTO> inRisk = loans.stream().filter(loanDTO -> loanDTO.getStatus()==Status.RISK)
                .collect(Collectors.toList());
        if(active.isEmpty()&&inRisk.isEmpty()){
            payAllCheckBox.setDisable(true);
            acceptButton.setDisable(true);
            choosePayment.setDisable(true);}
        else {
            choosePayment.setDisable(false);

            for (LoanDTO loan:active)
                if(loan.getStatus().equals(Status.ACTIVE))
                    choosePayment.getItems().add(loan.getLoansID());
            for (LoanDTO loan:inRisk) {
                choosePayment.getItems().add(loan.getLoansID());
            }
        }
    }

    public void showLonersLoans() {

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

    @FXML
    void clientChosePayment(ActionEvent event) {
        totalAmount.setText("next payment is a total of: " );
        payAllLable.setText("the amount left to pay all back at once is:");
        payAllCheckBox.setDisable(false);
        acceptButton.setDisable(false);
    }

    @FXML
    void acceptButtonListener(ActionEvent event) {
        try {

            if (payAllCheckBox.isSelected()) {
                bodyUser.mainController.payAllBack(choosePayment.getValue());
            }
            // else {
            //bodyUser.mainController.payBack(choosePayment.getValue(),);
            //   }
            //pay back next payment
            //}
        }
        catch (NotEnoughMoney e){
            payAllLable.setText("Your current balance is:________________________________ ");
        }


    }

}