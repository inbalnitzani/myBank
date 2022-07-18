package app.saleLoans;

import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LoanDTO;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import servlet.HttpClientUtil;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class saleLoansController {

    @FXML
    private TableView<LoanDTO> myLoans;

    @FXML
    private TableView<LoanDTO> othersLoans;
    private clientHomePageController homePageController;
    private List<LoanDTO> loansToSale;
    private List<LoanDTO> loansToBuy;
    private String name;
    @FXML private ComboBox<String> chooseLoaneToSale;
    @FXML private ComboBox<String> chooseLoanToBuy;
  //  @FXML private Label errorSaleLable;
    //@FXML private Label errorBuyLable;


    public void setName(String name) {
        this.name = name;
    }
    @FXML
    void buyLoan(ActionEvent event) {
        if (chooseLoanToBuy.getValue().isEmpty()){
            // errorSaleLable.setText("you must choose a loan first!");

        }
        else {
            // errorSaleLable.setText("");
            String loan = chooseLoanToBuy.getValue();
            String finalUrl = HttpUrl
                    .parse("http://localhost:8080/demo_Web_exploded/buyLoan")
                    .newBuilder().addQueryParameter("loan",String.valueOf(loan))
                    .addQueryParameter("buyer",String.valueOf(name))
                    .build()
                    .toString();
            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    System.out.println("failed");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                }
            });
        }
    }
    @FXML
    void saleLoan(ActionEvent event) {
        if (chooseLoaneToSale.getValue().isEmpty()){
           // errorSaleLable.setText("you must choose a loan first!");

        }
        else {
           // errorSaleLable.setText("");
            String loan = chooseLoaneToSale.getValue();
            String finalUrl = HttpUrl
                    .parse("http://localhost:8080/demo_Web_exploded/listLoanForSale")
                    .newBuilder().addQueryParameter("loan",String.valueOf(loan))
                    .addQueryParameter("client",String.valueOf(name))
                    .build()
                    .toString();
            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    System.out.println("failed");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //loansToSale.remove(loan);
                }
            });
        }
    }
    public void setHomePageController(clientHomePageController controller) {
        this.homePageController = controller;
    }
    @FXML public void initialize() {
        loansToBuy = new ArrayList<>();
        loansToSale = new ArrayList<>();
        setLoansLenderTables(loansToSale);
        setLoansToBuyTables(loansToBuy);
    }
    public void setLoansLenderTables (List < LoanDTO > loanDTOS) {

        if (loanDTOS != null) {
            myLoans.getColumns().clear();

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

            myLoans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
            myLoans.setItems(FXCollections.observableArrayList(loanDTOS));
            chooseLoaneToSale.getItems().clear();
            for (LoanDTO loan : loanDTOS) {
                chooseLoaneToSale.getItems().add(loan.getLoansID());
            }
        }

    }
    public void setLoansToBuyTables (List < LoanDTO > loanDTOS) {
        if (loanDTOS != null) {
            othersLoans.getColumns().clear();
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

            othersLoans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
            othersLoans.setItems(FXCollections.observableArrayList(loanDTOS));
            chooseLoanToBuy.getItems().clear();
            for (LoanDTO loan : loanDTOS) {
                chooseLoanToBuy.getItems().add(loan.getLoansID());
            }
        }

    }
    public void setDisable(){
        chooseLoanToBuy.setDisable(true);
        chooseLoaneToSale.setDisable(true);
    }
    public void setAble(){
        chooseLoanToBuy.setDisable(false);
        chooseLoaneToSale.setDisable(false);
    }

}