package app.information;

import app.bodyUser.bodyUser;
import dto.*;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import exception.NotEnoughMoney;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class informationController {

    @FXML private TableView<LoanDTO> loansAsLoner;
    @FXML private TableView<LoanDTO> loansAsLender;
    @FXML private TableView<MovementDTO> transactionTable;
    @FXML private TextField amount;
    @FXML private Button chargeButton;
    @FXML private Button withdrawButton;
    @FXML private Label balance;
    @FXML private Label amountErrorLabel;
    private bodyUser bodyUser;
    private ClientDTO user;

    @FXML void chargeListener(ActionEvent event) {
        try {
            double toAdd = Double.parseDouble(amount.getText());
            if (toAdd <= 0) {
                amountErrorLabel.setText("Please enter a positive number!");
            } else {
                bodyUser.chargeAccount(toAdd);
                bodyUser.updateClientInfo();
                amountErrorLabel.setText("Account charged successfully!");
                showTransactions();
            }
        } catch (Exception e) {
            amountErrorLabel.setText("Please enter a positive number!");
        } finally {
            amount.clear();
        }
    }
    @FXML void withdrawListener(ActionEvent event) {
        try {
            double toWithdraw = Double.parseDouble(amount.getText());
            if(toWithdraw<=0){
                amountErrorLabel.setText("Please Enter a positive number.");
            } else {
                bodyUser.withdrawFromAccount(user.getFullName(), toWithdraw);
                bodyUser.updateClientInfo();
                amountErrorLabel.setText("Withdraw from account successfully.");
                showTransactions();
            }
        } catch (NotEnoughMoney exception) {
            amountErrorLabel.setText("NOTICE: you do not have enough money in your account");
        } catch (Exception e){
            amountErrorLabel.setText("Please Enter a positive number.");
        } finally {
            amount.clear();
        }
    }
    @FXML void amountListener(ActionEvent event) {


    }
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;

    }
    public void updateUserViewer(ClientDTO user){
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
        ObservableList<MovementDTO> transactionData = FXCollections.observableArrayList();
        List<List<MovementDTO>> movements = new ArrayList<>(user.getMovements().values());
        for (List<MovementDTO> moveList : movements) {
            for (MovementDTO movement : moveList) {
                transactionData.add(movement);
            }
        }
        transactionTable.setItems(transactionData);
    }
    public void showLonersLoans(Collection<LoanDTO> loans) {
        loansAsLoner.setItems(FXCollections.observableArrayList(loans));
    }
    public void showLoansAsLender(Collection<LoanDTO> loans) {
        loansAsLender.setItems(FXCollections.observableArrayList(loans));
    }
    public void updateClientUser(){
        updateUserViewer(bodyUser.getClientDTO());
        showData();
    }
    public void setInformationDataForNewFile(){
        setLoansLonerTables();
        setLoansLenderTables();
        setTransactionTable();
    }
    public void setLoansLonerTables()  {
        loansAsLoner.getColumns().clear();
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

        loansAsLoner.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
    }
    public void setLoansLenderTables() {
        loansAsLender.getColumns().clear();
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
    }
    public void setTransactionTable(){
    transactionTable.getColumns().clear();

    TableColumn<MovementDTO, String> amountCol = new TableColumn<>("Amount");
    TableColumn<MovementDTO, Integer> balanceBeforeCol = new TableColumn<>("Balance before");
    TableColumn<MovementDTO, Integer> balanceAfterCol = new TableColumn<>("Balance after");
    TableColumn<MovementDTO, Integer> yazCol = new TableColumn<>("Yaz");

    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    balanceBeforeCol.setCellValueFactory(new PropertyValueFactory<>("amountBeforeMovement"));
    balanceAfterCol.setCellValueFactory(new PropertyValueFactory<>("amountAfterMovement"));
    yazCol.setCellValueFactory(new PropertyValueFactory<>("executeTime"));
    transactionTable.getColumns().addAll(amountCol, balanceBeforeCol, balanceAfterCol, yazCol);
}
}