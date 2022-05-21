package app.information;

import app.bodyUser.bodyUser;
import dto.ClientDTO;
import dto.LoanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Collection;

public class informationController {

    @FXML TableView<LoanDTO> loansAsLoner;
    @FXML TableView<LoanDTO> loansAsLender;
    @FXML private TextField amount;
    @FXML private Button chargeButton;
    @FXML private Button withdrawButton;

        private bodyUser bodyUser;
    private ClientDTO user;
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }
    @FXML void amountListener(ActionEvent event) {


    }
    public void setUser(ClientDTO user){this.user = user;}
    public void showData() {
        showLonersLoans(user.getLoansAsBorrower());
        showLoansAsLender(user.getLoansAsGiver());

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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        loansAsLoner.getColumns().addAll(idCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLoner.setItems(loansData);

    }
  @FXML void chargeListener(ActionEvent event) {
   try {
        double toAdd = Double.parseDouble(amount.getText());
        bodyUser.chargeAcount(user.getFullName(),toAdd);
    }
   catch (Exception exception)
    {
       System.out.println( "not good");
   }

       }
    public void showLoansAsLender(Collection<LoanDTO> loans) {

        loansAsLender.getColumns().clear();

        ObservableList<LoanDTO> loansData = FXCollections.observableArrayList();
        loansData.addAll(loans);

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


        loansAsLender.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLender.setItems(loansData);

    }
}
