package homePage;

import clientsList.clientsListRefresher;
import dto.ClientDTO;
import dto.LoanDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mainScreenAdmin.mainScreenAdminController;
import javafx.scene.control.Label;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class adminHomePageController {

    private mainScreenAdminController mainController;
    @FXML private TableView<ClientDTO> clientsInfoTable;
    @FXML TableColumn<ClientDTO, String> idNameCol;
    @FXML TableColumn<ClientDTO, Integer> currBalanceCol;
    @FXML private TableView<LoanDTO> loans;
    @FXML private Label hello;

    private Timer timer;
    private TimerTask listRefresher;


    public void setHello(String name) {
        hello.setText("Hello " + name);
    }

    public void startListRefresher() {
        listRefresher = new clientsListRefresher(this::showClients,this::showLoanData);
        timer = new Timer();
        timer.schedule(listRefresher, 0, 2000);
    }
    public void setMainController(mainScreenAdminController mainController) {
        this.mainController = mainController;
    }

    public void showClients(List<ClientDTO> clients) {
        Platform.runLater(() -> {
            clientsInfoTable.getColumns().clear();

            idNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            currBalanceCol.setCellValueFactory(new PropertyValueFactory<>("currBalance"));

            clientsInfoTable.getColumns().addAll(idNameCol, currBalanceCol);
            clientsInfoTable.setItems(FXCollections.observableArrayList(clients));
            // clientsDetail.setMasterNode(clientsInfoTable);
            //clientsDetail.setDetailSide(Side.RIGHT);


        });
    }


    public void showLoanData(List<LoanDTO> loansList) {
        Platform.runLater(() -> {
            loans.getColumns().clear();
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

        /*loans.setOnMouseClicked(event -> {
            LoanDTO loan =loans.getSelectionModel().getSelectedItem();
            if (loan != null){
                VBox vBox= createDetailNodeByLoanStatus(loan);
                loansDetail.setDetailNode(new ScrollPane(vBox));
            }
        });*/
            loans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
            loans.setItems(FXCollections.observableArrayList(loansList));
            //setLoansInfo();
        });
    }
/*
    public void setLoansInfo() {
        loansDetail.setMasterNode(loans);
        loansDetail.setDetailSide(Side.RIGHT);
        loansDetail.setDetailNode(new Label("To see more information\nclick on the loan."));
        loansDetail.setShowDetailNode(true);
    }*/




}
