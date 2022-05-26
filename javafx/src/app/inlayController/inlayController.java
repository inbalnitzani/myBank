package app.inlayController;

import app.bodyUser.bodyUser;
import dto.LoanDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;
import loan.LoanTerms;
import org.controlsfx.control.CheckComboBox;
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
    @FXML private AnchorPane center;
    @FXML private AnchorPane buttom;
    @FXML private Label currentLoanId;
    @FXML private AnchorPane rightErea;
    private List<LoanDTO> loansToInvest;
    private Pane investmentStatus;
    private Button approveButton;
    private SimpleDoubleProperty accountBalanceProp;

    @FXML void startInlay(ActionEvent event) {
        boolean validAmount = checkAmountToInvest();
        boolean validTime = checkMinTime();
        if (validAmount && validTime) {
            if (checkMandatoryCategories()) {
                LoanTerms terms = updateTerms();
                List<LoanDTO> matchLoans = bodyUser.findMatchLoans(bodyUser.getClientDTO().getFullName(), terms);
                showRelevantLoans(matchLoans);
            }
        }
    }

    @FXML public void initialize() {
        approveButton = new Button("Approve Inlay");
        for (int i = 1; i <= 100; i++) {
            minInterestForLoan.getItems().add(Integer.toString(i));
        }
        accountBalance.textProperty().bind(accountBalanceProp.asString());
    }

    public boolean checkMinTime() {
        String input = minTimeToReturn.getCharacters().toString().trim();
        boolean validInput=false;
        if(input.equals("")){
            validInput=true;
        } else {
            try {
                int number = Integer.parseInt(input);
                if (number < 0) {
                errorMinTime.setText(number + " is a negative number! Please enter a positive number");
                } else {
                    validInput=true;
                    errorMinTime.setText("");
                }
            } catch (Exception err) {
                errorMinTime.setText(input + " is not a number. Please enter a number");
            } finally {
            if (!validInput)
                minTimeToReturn.setText("");
            }
        }
        return validInput;
    }

    public boolean checkAmountToInvest() {
        String input = amountToInvest.getCharacters().toString().trim();
        boolean validInput = false;
        double currBalance = bodyUser.getClientBalance();
        try {
            int number=Integer.parseInt(input);
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
                errorAmount.setText("Amount is a mandatory field!");
                amountToInvest.clear();
            } else {
                errorAmount.setText("Please enter an Integer!");
            }
        } finally {
            if (!validInput)
                amountToInvest.setText("");
        }
    return validInput;
    }

    public inlayController(){
        loansToInvest=new ArrayList<>();
        investmentStatus=new Pane();
        accountBalanceProp=new SimpleDoubleProperty();
    }

    public void setDataAccordingToClient(){
        clientName.setText(bodyUser.getClientDTO().getFullName());
        accountBalanceProp.set(bodyUser.getClientBalance());
      //  accountBalance.textProperty().bind(accountBalanceProp.asString());
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
        } center.getChildren().clear();
    }

    private void showRelevantLoans(List<LoanDTO> loans) {
        if (loans.size() == 0) {
            center.getChildren().clear();
            center.getChildren().add(new Label("There are no loans that match your applications"));
        } else {
            checkIfLoansExist(false);
            TableView<LoanDTO> optionalLoans = new TableView<>();

            TableColumn<LoanDTO, String> idCol = new TableColumn<>("Id");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<LoanDTO, String> categoryCol = new TableColumn<>("Category");
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

            TableColumn<LoanDTO, String> capitalCol = new TableColumn<>("Original Amount");
            capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

            TableColumn<LoanDTO, String> paceCol = new TableColumn<>("Pace");
            paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));

            TableColumn<LoanDTO, String> interestCol = new TableColumn<>("Interest");
            interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));

            TableColumn<LoanDTO, String> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            TableColumn<LoanDTO, String> finalAmountCol = new TableColumn<>("Final Amount");
            finalAmountCol.setCellValueFactory(new PropertyValueFactory<>("interest"));

            approveButton.setDisable(true);
            approveButton.setOnAction(e -> {
                startInlayProcess();
            });

            TableColumn investLoanActionCol = new TableColumn("Investment");

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
                                            }
                                            setDisableApproveButton();
                                        });
                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };
            investLoanActionCol.setCellFactory(cellFactory);
            optionalLoans.getColumns().addAll(idCol, categoryCol, capitalCol, paceCol, interestCol, statusCol, investLoanActionCol);

            optionalLoans.setItems(FXCollections.observableArrayList(loans));
            center.getChildren().clear();
            buttom.getChildren().clear();
            optionalLoans.setOnMouseClicked(event -> {
                LoanDTO choice =optionalLoans.getSelectionModel().getSelectedItem();
                if (choice != null){
                    addMoreInfo(choice);
                }
            });

            center.getChildren().add(optionalLoans);
            buttom.getChildren().add(approveButton);
        }
    }

    public void addMoreInfo(LoanDTO loan){
        VBox data = new VBox();
        currentLoanId.setText(loan.getId());
        switch (loan.getStatus()) {
            case NEW:
                data.getChildren().clear();
                data.getChildren().add(new Label("No more Info"));
                break;
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
        rightErea.getChildren().clear();
        rightErea.getChildren().add(data);
    }

    private void setDisableApproveButton() {
        boolean disableButton = false;
        if (loansToInvest.isEmpty()) {
            disableButton = true;
        }
        approveButton.setDisable(disableButton);
    }

    public void updateClientUser(){
        accountBalanceProp.set(bodyUser.getClientBalance());
    }

    private void startInlayProcess() {
        int amountLeft = bodyUser.startInlayProcess(loansToInvest, clientName.textProperty().getValue());

        loansToInvest.clear();
        bodyUser.updateClientInfo();
        accountBalanceProp.set(bodyUser.getClientDTO().getCurrBalance());
        bodyUser.updateClientInfo();

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
        minInterestForLoan.setValue("");
    //    investmentStatus.getChildren().clear();
        investmentStatus.getChildren().add(label);
        center.getChildren().clear();
        center.getChildren().add(investmentStatus);
        buttom.getChildren().clear();
        approveButton.setDisable(true);
        rightErea.getChildren().clear();
    }

    private VBox showDataAccordingLoanStatus(VBox data,String str, String value){
        Label label2=new Label(value);
        Label label1=new Label(str);
        data.getChildren().add(new HBox(label1,label2));
        return data;
    }

    public boolean checkMandatoryCategories() {
        if(bodyUser.getClientBalance()==0){
            errorAmount.setText("Current balance is 0. CAN'T INVEST.");
            return false;
        }
        return true;
    }

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setCategoriesChooser(){
        if(categoriesForLoan.getItems().size()>0)
            categoriesForLoan.getItems().clear();
        categoriesForLoan.getItems().addAll(bodyUser.getCategories());
    }
}
