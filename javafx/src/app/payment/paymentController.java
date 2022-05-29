package app.payment;

import app.bodyUser.bodyUser;
import bank.Global;

import dto.ClientDTO;
import dto.LoanDTO;
import dto.PaymentDTO;
import exception.NotEnoughMoney;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Status;

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
    @FXML private Label payAllLabel;
    @FXML private ListView<String> notificationList;
    @FXML private Label totalAmount;
    @FXML private Label paidMassage;
    @FXML private TextField amountToPay;
    @FXML private Label amountError;

    @FXML void acceptButtonListener(ActionEvent event) {
        LoanDTO loan = loans.get(choosePayment.getValue());
        try {
            if (payAllCheckBox.isSelected()) {
                bodyUser.mainController.payAllBack(choosePayment.getValue());

            } else if (loan.getStatus().equals(Status.RISK)) {
                payRiskLoan(loan);
            } else {
                bodyUser.mainController.payBackNextPayment(choosePayment.getValue(),loan.getNextPaymentAmount(),loan.getNextPaymentTime());
            }
            bodyUser.updateClientInfo();
            paidMassage.setText("The payment was successfully made");
            acceptButton.setDisable(true);
            amountToPay.setDisable(true);
            amountToPay.clear();
        }
        catch (NotEnoughMoney e){
            payAllLabel.setText("NOTICE: you do not have enough money ");
        }
        catch (Exception e){
            amountError.setText("Please Enter a positive number that is no larger then the total amount of the next payment.");
            amountToPay.clear();
        }

    }
    @FXML void clientChosePayment(ActionEvent event) {
        if(choosePayment.getValue()!= null) {
            paidMassage.setText("");
            LoanDTO loan = loans.get(choosePayment.getValue());
            double totalToCompleteLoan = loan.getTotalMoneyForPayingBack() - loan.getAmountPaidBack();
            double totalPayment = loan.getNextPaymentAmount();
            totalAmount.setText("next payment is a total of: " + totalPayment);
            payAllLabel.setText("the amount left to pay all back at once is:" + totalToCompleteLoan);
            payAllCheckBox.setDisable(false);
            amountToPay.setDisable(true);
            int nextPaymentTime =loan.getNextPaymentTime();
            if(loan.getStatus().equals(Status.RISK))
            {
                acceptButton.setDisable(false);
                amountToPay.setDisable(false);
            }
            else if( nextPaymentTime== Global.worldTime&&!loan.getPayments().get(nextPaymentTime).isPaid())
                acceptButton.setDisable(false);
            else acceptButton.setDisable(true);
        }
    }
    @FXML void payAllListener(ActionEvent event) {
        if (payAllCheckBox.isSelected())
            acceptButton.setDisable(false);
    }
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }
    public void payRiskLoan(LoanDTO loanDTO) throws Exception {
        double amount = Double.parseDouble(amountToPay.getText());
        if (amount <= 0 || amount > loanDTO.getNextPaymentAmount()) {
            throw new Exception();
        }
        amountError.setText("");
        if (amount == loanDTO.getNextPaymentAmount()) {
            bodyUser.mainController.payBackNextPayment(choosePayment.getValue(), loanDTO.getNextPaymentAmount(), loanDTO.getNextPaymentTime());
        } else {
            bodyUser.mainController.payApartOfDebt(loanDTO.getId(),amount);
        }
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
        for (int yaz = 1; yaz<= time; yaz++) {
            for (LoanDTO loan:loansList) {
                Map<Integer,PaymentDTO> paymentsByYaz = loan.getPayments();
                PaymentDTO paymentDTO = paymentsByYaz.get(yaz);
                if (paymentDTO!=null )
                    if(!paymentDTO.getPaidAPartOfDebt() || (paymentDTO.isPayAll())){
                        notificationList.getItems().add("Yaz: "+yaz+"\nIt is time to pay back for "+'"'+loan.getId()+'"' +
                                "\na total of: "+(paymentDTO.getOriginalAmount()));
                }
            }
        }

    }

    public void showPaymentsControl(){
        choosePayment.getItems().clear();
        payAllLabel.setText("");
        totalAmount.setText("");
        paidMassage.setText("");
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
    public void setPaymentsDataForNewFile(){
        setLoanerLoans();
    }
    public void setLoanerLoans(){
        loanerLoans.getColumns().clear();
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

    }
    public void showLonersLoans() {
        loanerLoans.setItems(FXCollections.observableArrayList(loansList));
    }
    public void updateClientUser(){
        setClient(bodyUser.getClientDTO());
        loansList=client.getLoansAsBorrower();
        showData();
    }
}