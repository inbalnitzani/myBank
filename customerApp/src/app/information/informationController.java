package app.information;

import app.bodyUser.bodyUser;
import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class informationController {

    @FXML
    private TableView<LoanDTO> loansAsLoner;
    @FXML
    private TableView<LoanDTO> loansAsLender;
    @FXML
    private TableView<MovementDTO> transactionTable;
    @FXML
    private TextField amount;
    @FXML
    private Label balance;
    @FXML
    private Label amountErrorLabel;
    @FXML
    private Button chargeButton;
    @FXML
    private Button withdrawButton;
    private bodyUser bodyUser;
    private ClientDTO user;
    private clientHomePageController homePageController;

    @FXML void chargeListener(ActionEvent event) {
        try {
            double toAdd = Double.parseDouble(amount.getText());
            if (toAdd <= 0) {
                amountErrorLabel.setText("Please enter a positive number!");
            } else {
                changeAccountBalance("charge");
                showAllTransactionsToClient();
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
            if (toWithdraw <= 0) {
                amountErrorLabel.setText("Please Enter a positive number.");
            } else {
                changeAccountBalance("withdraw");
                showAllTransactionsToClient();
            }
        } catch (Exception e) {
            amountErrorLabel.setText("Please Enter a positive number.");
        } finally {
            amount.clear();
        }
    }
    @FXML void amountListener(ActionEvent event) {


    }
    public void setHomePageController(clientHomePageController controller) {
        this.homePageController = controller;
    }
    public void changeAccountBalance(String typeMovement) {

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/changeAccountBalance")
                .newBuilder()
                .addQueryParameter("owner", homePageController.getClientName())
                .addQueryParameter("amount", amount.getText())
                .addQueryParameter("TypeMovement", typeMovement)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        amountErrorLabel.setText("Unknown error occurred! PLease try again!")
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        amountErrorLabel.setText("Finished successfully!");
                        homePageController.setAccountBalance(Double.valueOf(response.header("accountBalance")));
                    });
                } else {
                    Platform.runLater(() -> {
                        String responseBody = null;
                        try {
                            responseBody = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        amountErrorLabel.setText(responseBody);
                    });
                }
            }
        });
        clearAllLabel();
    }
    public void clearAllLabel() {
        amount.setText("");
    }
    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;

    }
    public void updateUserViewer(ClientDTO user) {
        this.user = user;
        balance.setText("Your current balance is: " + user.getCurrBalance());
    }
    public void showData() {
        showLoansByType("borrower");
        showLoansByType("lender");
        showAllTransactionsToClient();
    }
    public void createTransactionTable(Map<Integer, List<MovementDTO>> movementDTOList) {
        ObservableList<MovementDTO> transactionData = FXCollections.observableArrayList();
        List<List<MovementDTO>> movements = new ArrayList<>(movementDTOList.values());
        for (List<MovementDTO> moveList : movements) {
            for (MovementDTO movement : moveList) {
                transactionData.add(movement);
            }
        }
        transactionTable.getItems().clear();
        transactionTable.setItems(transactionData);
    }
    public void showAllTransactionsToClient() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/movements")
                .newBuilder()
                .addQueryParameter("client", homePageController.getClientName())
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            Map<Integer, List<MovementDTO>> movements = getMovements(response.body().string());
                            createTransactionTable(movements);
                            setTransactionTable();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                            }
                    );
                }
            }
        });
    }
    public List<LoanDTO> getLoans(String loansJSON) {
        Gson gson = new Gson();
        List<LoanDTO> loans = new ArrayList<>();
        try {
            Type listType = new TypeToken<List<LoanDTO>>() {
            }.getType();
            loans = gson.fromJson(loansJSON, listType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return loans;
        }
    }
    public Map<Integer, List<MovementDTO>> getMovements(String movementJson) {
        Gson gson = new Gson();
        Map<Integer, List<MovementDTO>> movements = null;
        try {
            Type listType = new TypeToken<Map<Integer, List<MovementDTO>>>() {
            }.getType();
            movements = gson.fromJson(movementJson, listType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return movements;
        }
    }
    public void showLoansByType(String loansType) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/loans")
                .newBuilder()
                .addQueryParameter("client", homePageController.getClientName())
                .addQueryParameter("loansType", loansType)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_FORBIDDEN) {
                    Platform.runLater(() -> {
                            }
                    );
                } else if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            List<LoanDTO> loanDTOS = getLoans(response.body().string());
                            switch (loansType) {
                                case "borrower":
                                    setLoansLonerTables(loanDTOS);
                                    loansAsLoner.setItems(FXCollections.observableArrayList(loanDTOS));
                                    break;
                                case "lender":
                                    setLoansLenderTables(loanDTOS);
                                    loansAsLender.setItems(FXCollections.observableArrayList(loanDTOS));
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
    public void updateClientUser() {
        updateUserViewer(bodyUser.getClientDTO());
        showData();
    }
    public void setInformationDataForNewFile() {
        setLoansLonerTables(null);
        setLoansLenderTables(null);
        setTransactionTable();
    }
    public void setLoansLonerTables(List<LoanDTO> loanDTOS) {
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loansAsLoner.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLoner.setItems(FXCollections.observableArrayList(loanDTOS));

    }
    public void setLoansLenderTables(List<LoanDTO> loanDTOS) {
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loansAsLender.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
        loansAsLender.setItems(FXCollections.observableArrayList(loanDTOS));

    }
    public void setTransactionTable() {
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
    public void refreshMovementsData(Map<Integer, List<MovementDTO>> movements) {
        createTransactionTable(movements);
        setTransactionTable();
    }
    public void refreshLoansLonerData(List<LoanDTO> loans) {
        setLoansLonerTables(loans);
    }
    public void refreshLenderLonerData(List<LoanDTO> loans) {
        setLoansLenderTables(loans);
    }
    public void setDisable() {
        amount.setDisable(true);
        chargeButton.setDisable(true);
        withdrawButton.setDisable(true);
    }
    public void setAble() {
        amount.setDisable(false);
        chargeButton.setDisable(false);
        withdrawButton.setDisable(false);
    }
}