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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loan.PayBack;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class bodyAdmin {

    @FXML private VBox bodyAdmin;
    @FXML private HBox loadFileButton;
    @FXML private Button loadFile;
    @FXML private Button increaseYaz;
    @FXML private Label clientInfo;
    @FXML private TableView<LoanDTO> loans;
    @FXML private TableView<ClientDTO> clients;
    private AppController mainController;

    @FXML void increaseYaz(ActionEvent event) {mainController.increaseYaz();}
    @FXML void loadNewFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();
        mainController.getFile(path);
        showData();
    }
    public void showData() {
        showLoanData();
        showClientData();
    }
    public void showLoanData() { loans.getColumns().clear();
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
            Object choice =loans.getSelectionModel().getSelectedItem();
            if (choice != null){
                popup(mainController.getLoans().get(loans.getSelectionModel().getFocusedIndex()));
            }
        });
        loans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loans.setItems(loansData);
    }
    public void showClientData() {
        clients.getColumns().clear();
        ObservableList<ClientDTO> clientData = FXCollections.observableArrayList();
        clientData.addAll(mainController.getClients());

        TableColumn<ClientDTO, String> idNameCol = new TableColumn<>("Client name");
        TableColumn<ClientDTO, Integer> currBalanceCol = new TableColumn<>("Balance");
        TableColumn<ClientDTO, Integer> asGiverCol = new TableColumn<>("Total loans as giver");
        TableColumn<ClientDTO, Integer> asTakenCol = new TableColumn<>("Total loans as taken");

        idNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        currBalanceCol.setCellValueFactory(new PropertyValueFactory<>("currBalance"));
        asGiverCol.setCellValueFactory(new PropertyValueFactory<>("sumAsLender"));
        asTakenCol.setCellValueFactory(new PropertyValueFactory<>("sumAsBorrower"));

        clients.getColumns().addAll(idNameCol,currBalanceCol,asGiverCol,asTakenCol);
        clients.setItems(clientData );
   }

    public void popup(LoanDTO loan){
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle(loan.getLoansID());
        Button button = new Button("Close");
        button.setOnAction(error -> popUpWindow.close());

        VBox data=new VBox(5);

        switch (loan.getStatus()) {
            case PENDING:
                data=addPendingData(data,loan);
                break;
            case ACTIVE:
                data=addPayBacksData(data,loan);
                data=addActiveData(data,loan);
                data=addPaymentData(data,loan);
                break;
        }

        data.setAlignment(Pos.CENTER_LEFT);
        data.getChildren().add(button);
        ScrollPane layout = new ScrollPane();
        layout.setContent(data);
        Scene scene = new Scene(data,500,100);
        popUpWindow.setScene(scene);
        popUpWindow.show();//
    }
    public VBox addPayBacksData(VBox data,LoanDTO loanDTO){
        for (int i = 0; i > loanDTO.getPayBacks().size(); i++) {
            PayBackDTO payBackDTO = loanDTO.getPayBacks().get(i);
            Label label = new Label(payBackDTO.getGiversName() + payBackDTO.getAmountInvested());
            data.getChildren().add(label);
        }
        return data;
    }
    public VBox addActiveData(VBox data,LoanDTO loan) {
        addPayBacksData(data, loan);
        data = addActiveTimeData(data, loan);
        return data;
    }
    public VBox addActiveTimeData(VBox data,LoanDTO loan){
        Label label=new Label("Active time is: "+loan.getActiveTime());
        Label label1=new Label("Next payment time: "+loan.getNextPaymentTime());
        return data;
    }
    public VBox addPendingData(VBox data,LoanDTO loan){
        data=addPayBacksData(data,loan);
        Label label=new Label("Total amount collected: "+loan.getAmountCollected());
        Label label1=new Label("Total left to become ACTIVE: "+(loan.getCapital()-loan.getAmountCollected()));
        data.getChildren().addAll(label,label1);
        return data;
    }
    public VBox addPaymentData(VBox data,LoanDTO loan){
        int index=1;
        for (PaymentDTO paymentDTO:loan.getPayments().values()){
            Label label=new Label("Payment "+index+" -- Paying time:"+paymentDTO.getActualPaidTime()+
                    " -- Fund:"+paymentDTO.getAmount()+" -- Interest:"+paymentDTO.getInterestPart()+
                    " -- Total:"+(paymentDTO.getInterestPart()+paymentDTO.getAmount()));
            data.getChildren().add(label);
        }

        loan.calculateInfo();
        Label label=new Label("Total fund paid:"+loan.getFundPaid()+" -- Total interest paid:"+loan.getInterestPaid());
        Label label1=new Label("Total fund left to pay:"+loan.getFundLeftToPay()+" -- Total interest left to pay:"+loan.getInterestLeftToPay());
        data.getChildren().addAll(label,label1);
        return data;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

}


