package app.bodyAdmin;

import app.main.AppController;
import dto.LoanDTO;
import dto.PayBackDTO;
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

public class bodyAdmin {

    @FXML private VBox bodyAdmin;
    @FXML private HBox loadFileButton;
    @FXML private Button loadFile;
    @FXML private Button increaseYaz;
    @FXML private Label clientInfo;
    @FXML private TableView<LoanDTO> loans;
    private AppController mainController;

    public void showData() {
        showLoanData();
    }

    public void showLoanData() {
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

        loans.setOnMouseClicked(event -> {
            Object choice =loans.getSelectionModel().getSelectedItem();
            if (choice != null){
                popup(mainController.getLoans().get(loans.getSelectionModel().getFocusedIndex()));
            }
        });
        loans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loans.setItems(loansData);
    }

    public void popup(LoanDTO loanDTO){
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle(loanDTO.getLoansID());
        Button button = new Button("Close");
        button.setOnAction(error -> popUpWindow.close());

        VBox data=new VBox(5);

        switch (loanDTO.getStatus()) {
            case PENDING:
                data=addPendingData(data,loanDTO);
                break;
            case ACTIVE:
        }

        data.setAlignment(Pos.CENTER_LEFT);
        data.getChildren().add(button);
        ScrollPane layout = new ScrollPane();
        layout.setContent(data);
        Scene scene = new Scene(data,500,100);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    public VBox addPendingData(VBox data,LoanDTO loanDTO){

        for (int i = 0; i > loanDTO.getPayBacks().size(); i++) {
            PayBackDTO payBackDTO = loanDTO.getPayBacks().get(i);
            Label label = new Label(payBackDTO.getGiversName() + payBackDTO.getAmountInvested());
            data.getChildren().add(label);
        }
        Label label=new Label("Total amount collected: "+loanDTO.getAmountCollected());
        Label label1=new Label("Total left to become ACTIVE: "+(loanDTO.getCapital()-loanDTO.getAmountCollected());
        data.getChildren().addAll(label,label1);
        return data;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML void increaseYaz(ActionEvent event) {
        mainController.increaseYaz();
    }

    @FXML void loadNewFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();
        mainController.getFile(path);
        showLoanData();
    }

}


