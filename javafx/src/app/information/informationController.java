package app.information;

import app.bodyUser.bodyUser;
import bank.Global;
import dto.*;
import exception.NotEnoughMoney;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loan.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class informationController {

    @FXML TableView<LoanDTO> loansAsLoner;
    @FXML TableView<LoanDTO> loansAsLender;
    @FXML private TextField amount;
    @FXML private Button chargeButton;
    @FXML private Button withdrawButton;
    @FXML private Label balance;
    @FXML private Label notEnoughtMoney;
    @FXML private TableView<MovementDTO> transactionTable;
    private bodyUser bodyUser;
    private ClientDTO user;

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;

    }
    @FXML void amountListener(ActionEvent event) {


    }
    public void setUser(ClientDTO user){
        this.user = user;
        balance.setText("Your current balance is: "+ user.getCurrBalance());
    }
    public void showData() {
        balance.setText("Your current balance is: "+ user.getCurrBalance());
        showLonersLoans(user.getLoansAsBorrower());
        showLoansAsLender(user.getLoansAsGiver());
        showTransactions();
    }
    public void showTransactions() {

        transactionTable.getColumns().clear();
        ObservableList<MovementDTO> transactionData = FXCollections.observableArrayList();

        List<List<MovementDTO>> movements = new ArrayList<>(user.getMovements().values());

        for (List<MovementDTO> moveList : movements) {
            for (MovementDTO movement : moveList) {
                transactionData.add(movement);
            }
        }
        TableColumn<MovementDTO, String> amountCol = new TableColumn<>("Amount");
        TableColumn<MovementDTO, Integer> balanceBeforeCol = new TableColumn<>("Balance before");
        TableColumn<MovementDTO, Integer> balanceAfterCol = new TableColumn<>("Balance after");
        TableColumn<MovementDTO, Integer> yazCol = new TableColumn<>("Yaz");

        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        balanceBeforeCol.setCellValueFactory(new PropertyValueFactory<>("amountBeforeMovement"));
        balanceAfterCol.setCellValueFactory(new PropertyValueFactory<>("amountAfterMovement"));
        yazCol.setCellValueFactory(new PropertyValueFactory<>("executeTime"));
        transactionTable.getColumns().addAll(amountCol, balanceBeforeCol, balanceAfterCol, yazCol);
        transactionTable.setItems(transactionData);
    }
    public void showLonersLoans(Collection<LoanDTO> loans) {


        loansAsLoner.getColumns().clear();
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusInfo"));

        loansAsLoner.getColumns().addAll(idCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLoner.setItems(loansData);

    }
    @FXML void chargeListener(ActionEvent event) {
        double toAdd = Double.parseDouble(amount.getText());
        bodyUser.chargeAcount(toAdd);
        amount.clear();
        setUser(bodyUser.mainController.getClientByName(user.getFullName()));

        showTransactions();
        ////////
        bodyUser.updateClientInfo();
    }
    @FXML void withdrawListener(ActionEvent event) {
        try {
            double toWithdraw = Double.parseDouble(amount.getText());
            bodyUser.withdrawFromAcount(user.getFullName(), toWithdraw);
            amount.clear();
            setUser(bodyUser.mainController.getClientByName(user.getFullName()));
            showTransactions();
            bodyUser.updateClientInfo();
        } catch (NotEnoughMoney exception) {
            notEnoughtMoney.setText("NOTICE: you do not have enough money in your account");
        }
    }
    public void showLoansAsLender(Collection<LoanDTO> loans) {

        loansAsLender.getColumns().clear();

        ObservableList<LoanDTO> loansData = FXCollections.observableArrayList();
        loansData.addAll(loans);

        TableColumn<LoanDTO, String> idCol = new TableColumn<>("ID ");
        TableColumn<LoanDTO, String> ownerNameCol = new TableColumn<>("Owner");
        TableColumn<LoanDTO, String> categoryCol = new TableColumn<>("Category");
        TableColumn<LoanDTO, Integer> capitalCol = new TableColumn<>("Capital");
        TableColumn<LoanDTO, Integer> totalTimeCol = new TableColumn<>("Total time");
        TableColumn<LoanDTO, Integer> interestCol = new TableColumn<>("Interest");
        TableColumn<LoanDTO, Integer> paceCol = new TableColumn<>("Payment pace");
        TableColumn<LoanDTO, String> statusCol = new TableColumn<>("Status");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ownerNameCol.setCellValueFactory(new PropertyValueFactory<>("owner"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));
        totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalYazTime"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusInfo"));


        loansAsLender.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLender.setItems(loansData);

    }

    public VBox addPayBacksData(VBox data,LoanDTO loanDTO){
        for (int i = 0; i > loanDTO.getPayBacks().size(); i++) {
            PayBackDTO payBackDTO = loanDTO.getPayBacks().get(i);
            Label label = new Label(payBackDTO.getGiversName() + payBackDTO.getAmountInvested());
            data.getChildren().add(label);
        }
        return data;
    }
    public void updateClientUser(){
     //   accountBalanceProp.set(bodyUser.getClientBalance());
        setUser(bodyUser.getClientDTO());
        showData();

    }



}