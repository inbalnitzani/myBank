package app.inlayController;

import app.bodyUser.bodyUser;
import dto.LoanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import loan.LoanTerms;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class inlayController {

    private bodyUser bodyUser;
    @FXML private VBox vbox;
    @FXML private Label clientName;
    @FXML private Label accountBalance;
    @FXML private TextField amountToInvest;
    @FXML private TextField minTimeToReturn;
    @FXML private ComboBox<String> minInterestForLoan;
    @FXML private CheckComboBox<String> categoriesForLoan;
    @FXML private Label errorAmount;
    @FXML private Label errorMinTime;
    private Set<String> loansToInvest;

    public inlayController(){
        loansToInvest=new HashSet<>();
    }

    @FXML void startInlay(ActionEvent event) {
        if (checkMandatoryCategories()) {
            LoanTerms terms = updateTerms();
            List<LoanDTO> matchLoans = bodyUser.findMatchLoans(bodyUser.getClientDTO().getFullName(), terms);
            showRelevantLoans(matchLoans);
        }
    }

    @FXML public void initialize() {

    }

    @FXML void checkMinTime(ActionEvent event) {
        String input = minTimeToReturn.getCharacters().toString().trim();
        boolean validInput=false;
        int number;
        try {
            number = Integer.parseInt(input);
            if (number < 0) {
                errorMinTime.setText(number + " is a negative number!\nPlease enter a positive number");
            } else {
                validInput=true;
                errorMinTime.setText("");
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorMinTime.setText("");
                minTimeToReturn.clear();
            } else {
                errorMinTime.setText(input + " is not a number.\nPlease enter a number");
            }
        } finally {
            if (!validInput)
                minTimeToReturn.setText("");
        }
    }

    @FXML void checkAmountToInvest(ActionEvent event) {
        String input = amountToInvest.getCharacters().toString().trim();
        boolean validInput = false;
        int number;
        try {
            number = Integer.parseInt(input);
            if (number < 0) {
                errorAmount.setText(number + " is a negative number!\nPlease enter a positive amount.");
            } else {
                double currBalance = bodyUser.getClientBalance();
                if (number > currBalance) {
                    errorAmount.setText("Current balance is: " + currBalance + ".\nPlease enter amount lower than " + currBalance);
                } else {
                    validInput = true;
                    errorAmount.setText("");
                }
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorAmount.setText("");
                amountToInvest.clear();
            } else {
                errorAmount.setText(input + " is not a number.\nPlease enter a number");
            }
        } finally {
            if (!validInput)
                amountToInvest.setText("");
        }
    }

    public void setDataAccordingToClient(){
        clientName.setText(bodyUser.getClientDTO().getFullName());
        accountBalance.setText(String.valueOf(bodyUser.getClientDTO().getCurrBalance()));
        checkIfLoansExist(true);
    }

    private LoanTerms setInterestTerm(LoanTerms terms) {
        String minInterestString = minInterestForLoan.getValue();
        if (minInterestString != null)
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

    private void checkIfLoansExist(boolean changeUser){
        int indexLast=vbox.getChildren().size()-1;
        if (vbox.getChildren().get(indexLast).getClass().getSimpleName().equals("Pane")){
            if (changeUser){
                amountToInvest.setText("");
                minTimeToReturn.setText("");
            }
            vbox.getChildren().remove(indexLast);
        }
    }

    private void showRelevantLoans(List<LoanDTO> loans){
        checkIfLoansExist(false);
        Pane root =new Pane();
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
                                    String id = getTableView().getItems().get(getIndex()).getLoansID();
                                    if (loansToInvest.contains(id)) {
                                        loansToInvest.remove(id);
                                        btn.setText("Invest this loan");
                                    } else if (!loansToInvest.contains(id)) {
                                        btn.setText("invested!");
                                        loansToInvest.add(id);
                                    }});
                                setGraphic(btn);
                                setText(null);
                            }}};
                    return cell;
        }};
        actionCol.setCellFactory(cellFactory);
        optionalLoans.getColumns().addAll(expander,idCol,categoryCol,capitalCol,paceCol,interestCol,statusCol,actionCol);
        optionalLoans.setItems(FXCollections.observableArrayList(loans));

        Button inlay = new Button("Aprrove Inlay");
        inlay.setOnAction(e->{
            startInlayProcess();
        });

        root.getChildren().addAll(optionalLoans,inlay);
        vbox.getChildren().add(root);
    }

    private void startInlayProcess(){}

    private VBox showDataAccordingLoanStatus(VBox data,String str, String value){
        Label label1=new Label();
        TextField a=new TextField();
        a.setText(value);
        label1.setText(str);
        VBox vBox=new VBox(label1,a);
        data.getChildren().add(vBox);
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
        if (amountToInvest.getCharacters().toString().equals("")) {
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
