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
    private Map<String,LoanDTO> loans;
    private List<LoanDTO> loansList;
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
        loans = new HashMap<>();
       loansList= client.getLoansAsBorrower();
        for (LoanDTO loan:loansList) {
            loans.put(loan.getId(),loan);
        }
        showLonersLoans();
        showNotifications();
        showPaymentsControl();

    }
    public void showNotifications(){
        notificationList.getItems().clear();
        int time = bodyUser.mainController.getTime();
        for (int yaz = 0; yaz<= time; yaz++) {
            for (LoanDTO loan:loansList) {
                Map<Integer,PaymentDTO> paymentsByYaz = loan.getPayments();
                if (paymentsByYaz.containsKey(yaz+1)&&!paymentsByYaz.get(yaz+1).isPaid())
                if (paymentsByYaz.containsKey(yaz)&&!paymentsByYaz.get(yaz).isPaid())
                    notificationList.getItems().add("Yaz: "+yaz+
                            "\nIt is time to pay back for "+'"'+loan.getId()+'"' +"\na total of: "+paymentsByYaz.get(yaz+1).getAmount());
            }
        }

    }

    public void showPaymentsControl(){
        choosePayment.getItems().clear();
        List<LoanDTO> active =loansList.stream().filter(loanDTO -> loanDTO.getStatus()==Status.ACTIVE)
                .collect(Collectors.toList());
        List<LoanDTO> inRisk = loansList.stream().filter(loanDTO -> loanDTO.getStatus()==Status.RISK)
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
        loansData.addAll(loansList);

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
        if(choosePayment.getValue()!= null) {
            LoanDTO loan = loans.get(choosePayment.getValue());
            double total = loan.getTotalMoneyForPayingBack() - loan.getAmountPaidBack();
            totalAmount.setText("next payment is a total of: " + loan.getNextPaymentAmount());
            payAllLable.setText("the amount left to pay all back at once is:" + total);
            payAllCheckBox.setDisable(false);
            acceptButton.setDisable(false);
        }
    }

    @FXML
    void acceptButtonListener(ActionEvent event) {
        LoanDTO loan = loans.get(choosePayment.getValue());

        try {

            if (payAllCheckBox.isSelected()) {
                bodyUser.mainController.payAllBack(choosePayment.getValue());
            }
             else {
            bodyUser.mainController.payBackNextPayment(choosePayment.getValue(),loan.getNextPaymentAmount(),loan.getNextPaymentTime());
            }
             bodyUser.updateClientInfo();
        }
        catch (NotEnoughMoney e){
            payAllLable.setText("NOTICE: you do not have enough money ");
        }


    }

    public void updateClientUser(){
      //  accountBalanceProp.set(bodyUser.getClientBalance());
        setClient(bodyUser.getClientDTO());
        showData();
    }
}