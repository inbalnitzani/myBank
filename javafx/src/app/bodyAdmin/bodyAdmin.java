package app.bodyAdmin;
import app.main.AppController;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.PayBackDTO;
import dto.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.MasterDetailPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class bodyAdmin {
    @FXML private VBox bodyAdmin;
    @FXML private HBox loadFileButton;
    @FXML private Button loadFile;
    @FXML private Button increaseYaz;
    @FXML private Label clientInfo;
    private TableView<LoanDTO> loans;
    private TableView<ClientDTO> clients;
    @FXML private MasterDetailPane loansDetail;
    @FXML private MasterDetailPane clientsDetail;
    private AppController mainController;

    public bodyAdmin(){
        loans=new TableView<>();
        clients=new TableView<>();
    }
    @FXML void increaseYaz(ActionEvent event) {
        mainController.increaseYaz();}
    @FXML void loadNewFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();
        if (mainController.getFile(path)) {
            showDataAdminScreen();
            mainController.setDataUser();
        }
    }
    public void updateLoansData() {
        loans.setItems(FXCollections.observableArrayList(mainController.getLoans()));
    }
    public void updateClientsData() {
        clients.setItems(FXCollections.observableArrayList(mainController.getClients()));
    }
    public void updateData() {
        updateLoansData();
        updateClientsData();
    }
    public void setClientInfo(){
        clientsDetail.setMasterNode(clients);
        clientsDetail.setDetailSide(Side.RIGHT);
        clientsDetail.setDetailNode(new Label("To see more information\nclick on the client."));
        clientsDetail.setShowDetailNode(true);
    }
    public void updateNoFileAdminScreen(){
        clientsDetail.setMasterNode(new Pane(new Label("No file in system!")));
        clientsDetail.setDetailNode(new Pane(new Label("No file in system!")));
        loansDetail.setMasterNode(new Pane(new Label("No file in system!")));
        loansDetail.setDetailNode(new Pane(new Label("No file in system!")));
    }
    public void setLoansInfo() {
        loansDetail.setMasterNode(loans);
        loansDetail.setDetailSide(Side.RIGHT);
        loansDetail.setDetailNode(new Label("To see more information\nclick on the loan."));
        loansDetail.setShowDetailNode(true);
    }
    public void showDataAdminScreen() {
        showLoanData();
        showClients();
    }
    public void showLoanData( ) {
        ObservableList<LoanDTO> loansData = FXCollections.observableArrayList();
        loansData.addAll(mainController.getLoans());

        TableColumn<LoanDTO, String> idCol = new TableColumn<>("ID loan");
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loans.setOnMouseClicked(event -> {
            LoanDTO loan =loans.getSelectionModel().getSelectedItem();
            if (loan != null){
                VBox vBox= createDetailNodeByLoanStatus(loan);
                loansDetail.setDetailNode(vBox);
            }
        });
        loans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loans.setItems(loansData);
        setLoansInfo();
    }
    public VBox createDetailNodeByLoanStatus(LoanDTO loan) {
        VBox vBox = new VBox();
        switch (loan.getStatus()) {
            case FINISHED:
                vBox = addFinishedData(vBox, loan);
                vBox = addPaymentData(vBox, loan);
            case PENDING:
                vBox = addPendingData(vBox, loan);
                break;
            case ACTIVE:
                vBox = addPayBacksData(vBox, loan);
                vBox = addPaymentData(vBox, loan);
                vBox = addActiveTimeData(vBox, loan);
                break;
            case RISK:
                vBox = addActiveTimeData(vBox, loan);
                vBox = addRiskData(vBox, loan);
                break;
            case NEW:
                vBox.getChildren().add(new Label("no data"));
                break;
        }
        return vBox;
    }
    public VBox addFinishedData(VBox vBox,LoanDTO loan) {
        Label firstPayment = new Label("First payment time: " + loan.getFirstPaymentTime());
        Label lastPayment = new Label("Last payment time: " + loan.getLastPaymentTime());
        vBox.getChildren().addAll(firstPayment, lastPayment);
        return vBox;
    }
    public VBox addRiskData(VBox vBox,LoanDTO loan){
        List<Integer> unpaidPayments=loan.getUnPaidPayment();
        Label label=new Label("Total of: "+unpaidPayments.size()+"unpaid payments at times:");
        String times="-";
        for(int time:unpaidPayments) {
            times = times.concat(String.valueOf(time));
            times = times.concat("-");
        }
        Label label2=new Label(times);
        Label label3=new Label("Missing  until now: "+ loan.getTotalAmountPerPayment());
        vBox.getChildren().addAll(label,label2,label3);
        return vBox;
    }
    public VBox createLoanData(List<LoanDTO> loans,VBox data) {
        if (loans.size() != 0) {
            int newLoan = 0, pendingLoan = 0, activeLoan = 0, riskLoan = 0;
            for (LoanDTO loanDTO : loans) {
                switch (loanDTO.getStatus()) {
                    case RISK:
                        riskLoan++;
                        break;
                    case ACTIVE:
                        activeLoan++;
                        break;
                    case PENDING:
                        pendingLoan++;
                        break;
                    case NEW:
                        newLoan++;
                        break;
                }
            }
            if (newLoan != 0)
                data.getChildren().add(new HBox(new Label("Total NEW loans: "), new Label(String.valueOf(newLoan))));
            if (pendingLoan != 0)
                data.getChildren().add(new HBox(new Label("Total PENDING loans: "), new Label(String.valueOf(pendingLoan))));
            if (activeLoan != 0)
                data.getChildren().add(new HBox(new Label("Total ACTIVE loans: "), new Label(String.valueOf(activeLoan))));
            if (riskLoan != 0)
                data.getChildren().add(new HBox(new Label("Total RISK loans: "), new Label(String.valueOf(riskLoan))));
        }
        else {
            data.getChildren().add(new Label("No loans right now."));
        }
        return data;
    }
    private void showClients() {
        TableColumn<ClientDTO, String> idNameCol = new TableColumn<>("Client name");
        TableColumn<ClientDTO, Integer> currBalanceCol = new TableColumn<>("Balance");
        TableColumn<ClientDTO, Integer> asGiverCol = new TableColumn<>("Total loans as giver");
        TableColumn<ClientDTO, Integer> asTakenCol = new TableColumn<>("Total loans as taken");

        idNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        currBalanceCol.setCellValueFactory(new PropertyValueFactory<>("currBalance"));
        asGiverCol.setCellValueFactory(new PropertyValueFactory<>("sumAsLender"));
        asTakenCol.setCellValueFactory(new PropertyValueFactory<>("sumAsBorrower"));

        clients.setOnMouseClicked(event -> {
            ClientDTO client =clients.getSelectionModel().getSelectedItem();
            if (client != null) {
                VBox data = new VBox();
                data.getChildren().add(new Label("Loans as borrower:"));
                createLoanData(client.getLoansAsBorrower(), data);
                data.getChildren().add(new Label("Loans as Lender:"));
                createLoanData(client.getLoansAsGiver(), data);
                clientsDetail.setDetailNode(data);
            }
        });

        clients.getColumns().addAll(idNameCol,currBalanceCol,asGiverCol,asTakenCol);
        clients.setItems(FXCollections.observableArrayList(mainController.getClients()));
        setClientInfo();
    }
    public VBox addPayBacksData(VBox data,LoanDTO loanDTO){
        data.getChildren().add(new Label("Lenders: "));
        List<PayBackDTO> payBackList = loanDTO.getPayBacks();
        int payBackSize = payBackList.size();
        for (int i = 0; i < payBackSize; i++) {
            PayBackDTO payBack = payBackList.get(i);
            data.getChildren().add(new Label((i+1)+". "+payBack.getGiversName() +": "+ payBack.getAmountInvested()));
        }
        data.getChildren().add(new Label(""));
        return data;
    }
    public VBox addActiveTimeData(VBox data, LoanDTO loan) {
        Label label=new Label("Active time is: "+loan.getActiveTime());
        Label label1=new Label("Next payment time: "+loan.getNextPaymentTime());
        data.getChildren().addAll(label,label1);
        return data;
    }
    public VBox addPendingData(VBox data,LoanDTO loan){
        data=addPayBacksData(data,loan);
        Label label=new Label("Total amount collected: "+loan.getAmountCollected());
        Label label1=new Label("Total left to become ACTIVE: "+(loan.getCapital()-loan.getAmountCollected()));
        data.getChildren().addAll(label,label1);
        return data;
    }
    public VBox addPaymentData(VBox data,LoanDTO loan) {
        List<PaymentDTO> paidPayments = loan.getPaidPayment();
        if (paidPayments.isEmpty()){
            data.getChildren().add(new Label("No money has been returned yet"));
        } else {
            data = createLabelsPaidPaymentData(data, paidPayments);
        }
        loan.calculateInfo();
        Label label = new Label("Total fund paid:" + loan.getFundPaid() + " -- Total interest paid:" + loan.getInterestPaid());
        Label label1 = new Label("Total fund left to pay:" + loan.getFundLeftToPay() + " -- Total interest left to pay:" + loan.getInterestLeftToPay());
        data.getChildren().addAll(label, label1);
        return data;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public VBox createLabelsPaidPaymentData(VBox data,List<PaymentDTO> paidPayments){
        int size=paidPayments.size();
        for (int i = 0; i < size; i++) {
            Label label = new Label("Payment " + (i + 1) + ":");
            Label label1 = new Label("Paying time: " + paidPayments.get(i).getActualPaidTime());
            Label label2 = new Label("Fund: " + paidPayments.get(i).getAmount());
            Label label3 = new Label("Interest: " + paidPayments.get(i).getInterestPart());
            Label label4 = new Label("Total: " + (paidPayments.get(i).getInterestPart() + paidPayments.get(i).getAmount()));
            data.getChildren().addAll(label, label1, label2, label3, label4);
        }
        return data;
    }
}