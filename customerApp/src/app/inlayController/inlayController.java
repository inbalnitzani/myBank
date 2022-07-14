package app.inlayController;

import app.bodyUser.bodyUser;
import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LoanDTO;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
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
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.controlsfx.control.CheckComboBox;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class inlayController {

    private bodyUser bodyUser;
    @FXML private TextField amountToInvest;
    @FXML private TextField minTimeToReturn;
    @FXML private ComboBox<String> minInterestForLoan;
    @FXML private CheckComboBox<String> categoriesForLoan;
    @FXML private Label errorAmount;
    @FXML private Label errorMinTime;
    @FXML private Label errorMaxLoans;
    @FXML private AnchorPane center;
    @FXML private AnchorPane buttom;
    @FXML private Label currentLoanId;
    @FXML private AnchorPane rightErea;
    @FXML private TextField maxLoansExist;
    @FXML private ComboBox<Integer> maxOwnership;
    @FXML private Button approveButton;
    private List<LoanDTO> loansToInvest;
    private Pane investmentStatus;
    private TableView<LoanDTO> optionalLoans;
    private clientHomePageController homePageController;

    @FXML public void initialize() {
        for (int i = 1; i <= 100; i++) {
            minInterestForLoan.getItems().add(Integer.toString(i));
            maxOwnership.getItems().add(i);
        }
        initializeOptionalLoans();
        approveButton.setDisable(true);
    }
    @FXML void startInlay(ActionEvent event) {
        boolean amountToInvest =checkAmountToInvest();
        boolean minTimeForLoan = checkMinTime();
        boolean maxLoansExist = checkMaxLoansExist();
        if (amountToInvest && minTimeForLoan && maxLoansExist) {
            LoanTerms terms = updateTerms();
            findMatchLoans(terms);
            //showRelevantLoans(loansToInvest);
        }
    }
    public void findMatchLoans(LoanTerms terms){
        Gson gson = new Gson();
        String json = gson.toJson(terms);

        String finalUrl = HttpUrl
            .parse("http://localhost:8080/demo_Web_exploded/findMatchLoans")
            .newBuilder()
            .addQueryParameter("client", homePageController.getClientName())
            .build()
            .toString();

        okhttp3.Callback callback = new okhttp3.Callback() {
            @Override
            public void onFailure(@org.jetbrains.annotations.NotNull Call call, @org.jetbrains.annotations.NotNull IOException e) {

            }

            @Override
            public void onResponse(@org.jetbrains.annotations.NotNull Call call, @org.jetbrains.annotations.NotNull Response response)  {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            List<LoanDTO> loans= getLoans(response.body().string());
                            loansToInvest.clear();
                            loansToInvest.addAll(loans);
                            showRelevantLoans(loansToInvest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {}
                    );
                }
            }
        };
        HttpClientUtil.runPostReq(finalUrl, json, callback);
    }
    public List<LoanDTO> getLoans(String loansJSON){
        Gson gson = new Gson();
        List<LoanDTO> loans = null;
        try {
            Type listType = new TypeToken<List<LoanDTO>>(){}.getType();
            loans = gson.fromJson(loansJSON, listType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (loans==null){
                loans=new ArrayList<>();
            }
            return loans;
        }
    }
    public void sendLoansToServlet(){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/startInlayProccess")
                .newBuilder()
                .addQueryParameter("client", homePageController.getClientName())
                .build()
                .toString();

        okhttp3.Callback callback = new okhttp3.Callback() {
            @Override
            public void onFailure(@org.jetbrains.annotations.NotNull Call call, @org.jetbrains.annotations.NotNull IOException e) {

            }

            @Override
            public void onResponse(@org.jetbrains.annotations.NotNull Call call, @org.jetbrains.annotations.NotNull Response response)  {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            List<LoanDTO> loans= getLoans(response.body().string());
                            loansToInvest.clear();
                            loansToInvest.addAll(loans);
                            showRelevantLoans(loansToInvest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {}
                    );
                }
            }
        };
    //    HttpClientUtil.runPostReq(finalUrl, json, callback);
    }
    @FXML private void startInlayProcess() {
        int amountLeft = bodyUser.startInlayProcess(loansToInvest,homePageController.getClientName());
        loansToInvest.clear();
        bodyUser.updateClientInfo();
        updateSuccessfully(amountLeft);
        resetDataForNewInvestment(true);
        center.getChildren().add(investmentStatus);
        buttom.getChildren().clear();
        approveButton.setDisable(true);
        rightErea.getChildren().clear();
    }
    public boolean checkMaxLoansExist() {
        String input = maxLoansExist.getCharacters().toString().trim();
        boolean validInput = false;
        if (input.equals("")) {
            validInput = true;
        } else {
            try {
                int number = Integer.parseInt(input);
                if (number < 0) {
                    errorMaxLoans.setText("'" + number + "' is a negative number! Please enter a positive number.");
                } else {
                    validInput = true;
                    errorMinTime.setText("");
                }
            } catch (Exception err) {
                errorMaxLoans.setText("'" + input + "' is not a number. Please enter a number.");
            } finally {
                if (validInput)
                    errorMaxLoans.setText("");
            }
        }
        return validInput;
    }
    public inlayController(){
        loansToInvest=new ArrayList<>();
        investmentStatus=new Pane();
        optionalLoans = new TableView<>();
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
        double currBalance = homePageController.getCurrentBalance();
        if(currBalance==0){
            errorAmount.setText("Current balance is 0. CAN'T INVEST! ");
        } else {
            try {
                int number = Integer.parseInt(input);
                if (number <=0) {
                    errorAmount.setText("Please enter a positive amount!");
                } else if (number > currBalance) {
                    errorAmount.setText("You don't have enough money!!");
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
            }
        }
        if (!validInput)
            amountToInvest.setText("");
        return validInput;
    }
    public void setDataAccordingToClient(){
        resetDataForNewInvestment(true);
        investmentStatus.getChildren().clear();
        approveButton.setDisable(true);
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
        if (userChoose.size()!= 0) {
            for (String category : userChoose)
                chosenCategory.add(category);
        }
        terms.setCategories(chosenCategory);
        return terms;
    }
    private LoanTerms setMaxLoansForOwner(LoanTerms terms){
        String maxLoans =maxLoansExist.getCharacters().toString().trim();
        if(maxLoans.equals(""))
            terms.setMaxLoansForOwner(0);
        else {
            terms.setMaxLoansForOwner(Integer.parseInt(maxLoans));
        }
        return terms;
    }
    private LoanTerms updateTerms() {
        LoanTerms terms = new LoanTerms();
        terms.setMaxAmount(Integer.parseInt(amountToInvest.getCharacters().toString()));
        terms=setMaxLoansForOwner(terms);
        terms=setTimeTerm(terms);
        terms=setCategoriesTerm(terms);
        terms=setInterestTerm(terms);
        terms=setMaxOwnership(terms);
        return terms;
    }
    private LoanTerms setMaxOwnership(LoanTerms terms){
        if(maxOwnership.getValue()!=null)
            terms.setMaxOwnershipPrecent(maxOwnership.getValue());
        return terms;
    }
    private void showRelevantLoans(List<LoanDTO> loans) {
        if (loans.size() == 0) {
            center.getChildren().clear();
            center.getChildren().add(new Label("There are no loans that match your applications"));
        } else {
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
                                                btn.setText("Chosen!");
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

            if (optionalLoans.getColumns().size()!=0) {
                TableColumn column = optionalLoans.getColumns().get(optionalLoans.getColumns().size() - 1);
                column.setCellFactory(cellFactory);
            }
            optionalLoans.setItems(FXCollections.observableArrayList(loans));
            center.getChildren().clear();
            buttom.getChildren().clear();
            center.getChildren().add(optionalLoans);
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
    public void updateSuccessfully(int amountLeft){
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
        investmentStatus.getChildren().clear();
        investmentStatus.getChildren().add(label);
    }
    public void setHomePageController(clientHomePageController controller){
        this.homePageController=controller;
    }
    private void resetDataForNewInvestment(boolean changeUser) {
        if (changeUser) {
            amountToInvest.setText("");
            errorAmount.setText("");
            minTimeToReturn.setText("");
            errorMinTime.setText("");
            minInterestForLoan.setValue("");
            maxLoansExist.setText("");
            maxOwnership.setValue(100);
        }
        center.getChildren().clear();
    }
    private VBox showDataAccordingLoanStatus(VBox data,String str, String value){
        Label label2=new Label(value);
        Label label1=new Label(str);
        data.getChildren().add(new HBox(label1,label2));
        return data;
    }
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }
    public void initializeOptionalLoans(){

        optionalLoans.getColumns().clear();
        TableColumn<LoanDTO, String> idCol = new TableColumn<>("Id");
        TableColumn<LoanDTO, String> categoryCol = new TableColumn<>("Category");
        TableColumn<LoanDTO, String> paceCol = new TableColumn<>("Pace");
        TableColumn<LoanDTO, String> capitalCol = new TableColumn<>("Original Amount");
        TableColumn<LoanDTO, String> interestCol = new TableColumn<>("Interest");
        TableColumn<LoanDTO, String> statusCol = new TableColumn<>("Status");
        TableColumn<LoanDTO, String> finalAmountCol = new TableColumn<>("Final Amount");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        finalAmountCol.setCellValueFactory(new PropertyValueFactory<>("finalAmount"));

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
                                            btn.setText("Chosen!");
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

        optionalLoans.setOnMouseClicked(event -> {
            LoanDTO choice =optionalLoans.getSelectionModel().getSelectedItem();
            if (choice != null){
                addMoreInfo(choice);
            }
        });
    }
    public void setCategoriesOptions(){
        if(categoriesForLoan.getItems().size()>0)
            categoriesForLoan.getItems().clear();
        categoriesForLoan.getItems().addAll(homePageController.getCategories());
    }
    public void initializeInlayData(){
        setCategoriesOptions();
        initializeOptionalLoans();
    }
}
