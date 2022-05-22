package app.inlayController;

import app.bodyUser.bodyUser;
import dto.LoanDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;
import loan.LoanTerms;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.table.TableRowExpanderColumn;

import javax.script.Bindings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class inlayController {

    private bodyUser bodyUser;
    @FXML private Label clientName;
    @FXML private Label accountBalance;
    @FXML private TextField amountToInvest;
    @FXML private TextField minTimeToReturn;
    @FXML private ComboBox<String> minInterestForLoan;
    @FXML private CheckComboBox<String> categoriesForLoan;
    @FXML private Label errorAmount;
    @FXML private Label errorMinTime;
    @FXML private AnchorPane canter;
    @FXML private AnchorPane button;
    private List<LoanDTO> loansToInvest;
    private Pane investmentStatus;
    private Button approveButton;
    private SimpleDoubleProperty accountBalanceProp;

    @FXML void startInlay(ActionEvent event) {
        if (checkMandatoryCategories()) {
            LoanTerms terms = updateTerms();
            List<LoanDTO> matchLoans = bodyUser.findMatchLoans(bodyUser.getClientDTO().getFullName(), terms);
            showRelevantLoans(matchLoans);
        }
    }

    @FXML public void initialize() {
        accountBalance.textProperty().bind(accountBalanceProp.asString());
    }

    @FXML void checkMinTime(ActionEvent event) {
        String input = minTimeToReturn.getCharacters().toString().trim();
        boolean validInput=false;
        int number;
        try {
            number = Integer.parseInt(input);
            if (number < 0) {
                errorMinTime.setText(number + " is a negative number! Please enter a positive number");
            } else {
                validInput=true;
                errorMinTime.setText("");
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorMinTime.setText("");
                minTimeToReturn.clear();
            } else {
                errorMinTime.setText(input + " is not a number. Please enter a number");
            }
        } finally {
            if (!validInput)
                minTimeToReturn.setText("");
        }
    }

    @FXML void checkAmountToInvest(ActionEvent event) {
        String input = amountToInvest.getCharacters().toString().trim();
        boolean validInput = false;
        int number=Integer.parseInt(input);
        double currBalance = bodyUser.getClientBalance();
        try {
            if(currBalance==0){
                errorAmount.setText("Current balance is 0. CAN'T INVEST! ");
            } else if (number < 0) {
                errorAmount.setText(number + " is a negative number! Please enter a positive amount.");
            } else if(number==0) {
                errorAmount.setText("Please enter a positive amount.");
            } else if (number > currBalance) {
                errorAmount.setText("Current balance is: " + currBalance + ". Please enter amount lower than " + currBalance);
            } else {
                validInput = true;
                errorAmount.setText("");
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorAmount.setText("");
                amountToInvest.clear();
            } else {
                errorAmount.setText(input + " is not a number. Please enter a number");
            }
        } finally {
            if (!validInput)
                amountToInvest.setText("");
        }
    }

    public inlayController(){
        loansToInvest=new ArrayList<>();
        investmentStatus=new Pane();
        accountBalanceProp=new SimpleDoubleProperty();
    }

    public void setDataAccordingToClient(){
        clientName.setText(bodyUser.getClientDTO().getFullName());
        accountBalanceProp.set(bodyUser.getClientBalance());
        accountBalance.textProperty().bind(accountBalanceProp.asString());
        checkIfLoansExist(true);
    }

    private LoanTerms setInterestTerm(LoanTerms terms) {
        String minInterestString = minInterestForLoan.getValue();
        if (minInterestString != null&& minInterestString!="")
            terms.setMinInterestForTimeUnit(Integer.parseInt(minInterestString));
        return terms;
    }

    private LoanTerms setTimeTerm(LoanTerms terms){
        String minTimeString = minTimeToReturn.getCharacters().toString();
        if (!minTimeString.equals("")) {
            terms.setMinTimeForLoan(Integer.parseInt(minTimeString));
        }
        return terms;
    }

    private LoanTerms setCategoriesTerm(LoanTerms terms) {
        Set<String> chosenCategory = new HashSet<>();
        ObservableList<String> userChoose = categoriesForLoan.getCheckModel().getCheckedItems();
        if (userChoose.size() == 0) {
            chosenCategory.addAll(bodyUser.getCategories());
        } else {
            for (String category : userChoose)
                chosenCategory.add(category);
        }
        terms.setCategories(chosenCategory);
        return terms;
    }

    private LoanTerms updateTerms() {
        LoanTerms terms = new LoanTerms();
        terms.setMaxAmount(Integer.parseInt(amountToInvest.getCharacters().toString()));
        terms= setTimeTerm(terms);
        terms=setCategoriesTerm(terms);
        terms=setInterestTerm(terms);
        return terms;
    }

    private void checkIfLoansExist(boolean changeUser) {
        if (changeUser) {
            amountToInvest.setText("");
            minTimeToReturn.setText("");
            errorAmount.setText("");
            errorMinTime.setText("");
            minInterestForLoan.setValue("");
        } canter.getChildren().clear();
        //borderPane.setCenter(null);
    }

    private void showRelevantLoans(List<LoanDTO> loans){
        checkIfLoansExist(false);
        TableView<LoanDTO> optionalLoans=new TableView<>();
        TableRowExpanderColumn<LoanDTO> expander = new TableRowExpanderColumn<>(this::createEditor);

        TableColumn<LoanDTO,String> idCol= new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<LoanDTO,String> categoryCol= new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<LoanDTO,String> capitalCol= new TableColumn<>("Original Amount");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        TableColumn<LoanDTO,String> paceCol= new TableColumn<>("Pace");
        paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));

        TableColumn<LoanDTO,String> interestCol= new TableColumn<>("Interest");
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));

        TableColumn<LoanDTO,String> statusCol= new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<LoanDTO,String> finalAmountCol= new TableColumn<>("Final Amount");
        finalAmountCol.setCellValueFactory(new PropertyValueFactory<>("interest"));

        approveButton = new Button("Approve Inlay");
        approveButton.setDisable(true);
        approveButton.setOnAction(e->{
            startInlayProcess();
        });

        TableColumn actionCol = new TableColumn("Investment");

        Callback<TableColumn<LoanDTO, String>, TableCell<LoanDTO, String>> cellFactory =
            new Callback<TableColumn<LoanDTO, String>, TableCell<LoanDTO, String>>() {
                public TableCell call(final TableColumn<LoanDTO, String> param) {
                    final TableCell<LoanDTO, String> cell = new TableCell<LoanDTO, String>() {
                        final Button btn = new Button("Invest this loan");
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    LoanDTO loan = getTableView().getItems().get(getIndex());
                                    if (loansToInvest.contains(loan)) {
                                        loansToInvest.remove(loan);
                                        btn.setText("Invest this loan");
                                    } else if (!loansToInvest.contains(loan)) {
                                        btn.setText("invested!");
                                        loansToInvest.add(loan);
                                    } setDisableApproveButton();
                                });
                                setGraphic(btn);
                                setText(null);
                            }}};
                    return cell;
        }};
        actionCol.setCellFactory(cellFactory);
        optionalLoans.getColumns().addAll(expander,idCol,categoryCol,capitalCol,paceCol,interestCol,statusCol,actionCol);

        optionalLoans.setItems(FXCollections.observableArrayList(loans));
        canter.getChildren().add(optionalLoans);
        button.getChildren().add(approveButton);
    }

    private void setDisableApproveButton() {
        boolean disableButton = false;
        if (loansToInvest.isEmpty()) {
            disableButton = true;
        }
        approveButton.setDisable(disableButton);
    }

    private void startInlayProcess() {
        int amountLeft = bodyUser.startInlayProcess(loansToInvest, clientName.textProperty().getValue());
        loansToInvest.clear();
        bodyUser.updateClientInfo();
        accountBalanceProp.set(bodyUser.getClientDTO().getCurrBalance());

        Label label = new Label();
        if (amountLeft == 0) {
            label.setText("Investment successfully completed!");
        } else {
            int originalAmountToInvest = Integer.parseInt(amountToInvest.getCharacters().toString());
            if (originalAmountToInvest == amountLeft) {
                label.setText("Sorry, an unknown error has occurred, please try again.");
            } else {
                label.setText("Invested " + (originalAmountToInvest - amountLeft) + " out of " + originalAmountToInvest + " successfully");
            }
        }
        amountToInvest.setText("");
        errorAmount.setText("");
        errorMinTime.setText("");
   //     minInterestForLoan.setValue("");
    //    investmentStatus.getChildren().clear();
        investmentStatus.getChildren().add(label);
        canter.getChildren().clear();
        canter.getChildren().add(investmentStatus);
        button.getChildren().clear();
        approveButton.setDisable(true);
    }

    private VBox showDataAccordingLoanStatus(VBox data,String str, String value){
        Label label2=new Label(value);
        Label label1=new Label(str);
        data.getChildren().add(new HBox(label1,label2));
        return data;
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<LoanDTO> param) {
        VBox data = new VBox();
        LoanDTO loan = param.getValue();
        switch (loan.getStatus()) {
            case PENDING:
                data = showDataAccordingLoanStatus(data, "Amount left for being Active: ",
                        String.valueOf(loan.getCapital() - loan.getAmountCollectedPending()));
                break;
            case ACTIVE:
                data = showDataAccordingLoanStatus(data, "Next Payment Time: ", String.valueOf(loan.getNextPaymentTime()));
                data = showDataAccordingLoanStatus(data, "Next Payment Amount: ", String.valueOf(loan.getNextPaymentAmount()));
                break;
            case RISK:
                data = showDataAccordingLoanStatus(data, "Sum missing payments: ", String.valueOf(loan.getMissingMoneyPaymentTimes()));
                data = showDataAccordingLoanStatus(data, "Next Payment Amount: ", String.valueOf(loan.getNextPaymentAmount()));
                break;
            case FINISHED:
                data = showDataAccordingLoanStatus(data, "Starting Time: ", String.valueOf(loan.getActiveTime()));
                data = showDataAccordingLoanStatus(data, "Last Payment Time: ", String.valueOf(loan.getLastPaymentTime()));
                break;
        }
        return data;
    }

    public boolean checkMandatoryCategories() {
        if(bodyUser.getClientBalance()==0){
            errorAmount.setText("Current balance is 0. CAN'T INVEST.");
            return false;
        } else if (amountToInvest.getCharacters().toString().equals("")) {
            errorAmount.setText("Please choose amount!");
            return false;
        }
        return true;
    }

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setChooseMinInterest() {
        for (int i = 1; i <= 100; i++) {
            minInterestForLoan.getItems().add(Integer.toString(i));
        }
    }

    public void setCategoriesChooser(){
        categoriesForLoan.getItems().addAll(bodyUser.getCategories());
    }


}
