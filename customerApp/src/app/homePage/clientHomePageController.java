package app.homePage;

import app.createLoan.createLoanController;
import app.refreshdata.dataRefresher;
import app.inlayController.inlayController;
import app.mainScreenClient.mainScreenClientController;
import app.payment.paymentController;
import app.saleLoans.saleLoansController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import dto.LoanDTO;
import dto.MovementDTO;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import loan.Status;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class clientHomePageController {

    @FXML private Button insertFile;
    @FXML private Label clientName;
    @FXML private Label currentYaz;
    @FXML private Label accountBalance;
    @FXML private Label errorFile;
    private List<String> categories;
    private mainScreenClientController mainController;
    private SimpleIntegerProperty yazProperty;
    @FXML private createLoanController createLoanComponentController;
    @FXML private Parent createLoanComponent;
    @FXML private app.information.informationController informationComponentController;
    @FXML private Parent informationComponent;
    @FXML private paymentController paymentComponentController;
    @FXML private Parent paymentComponent;
    @FXML private Parent inlayComponent;
    @FXML private inlayController inlayComponentController;
    @FXML private Parent saleLoansComponent;
    @FXML private saleLoansController saleLoansComponentController;
    private int version;
    private Timer timer;
  //  private TimerTask listRefresher;


    public int getCurrentYaz() {
        return Integer.parseInt(currentYaz.getText());
    }

    @FXML public void initialize() {
        getYazFromBank();
        createLoanComponentController.setHomePageController(this);
        informationComponentController.setHomePageController(this);
        paymentComponentController.setHomePageController(this);
        inlayComponentController.setHomePageController(this);
        saleLoansComponentController.setHomePageController(this);
        categories=new ArrayList<>();
        synchronized (this){
            updateCategories();
        }
        version=1;
    }
    @FXML void insertFileToSystem(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String filePath = selectedFile.getAbsolutePath();

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/addFileServlet")
                .newBuilder()
                .addQueryParameter("Name", clientName.getText())
                .addQueryParameter("Path", filePath)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    errorFile.setText(e.getMessage());
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int code = response.code();
                if (code == HttpServletResponse.SC_OK) {
                    Platform.runLater(() ->
                            errorFile.setText("File loaded successfully!")
                    );
                } else {
                    Platform.runLater(() -> {
                        String responseBody = null;
                        try {
                            responseBody = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(responseBody.equals("null"))
                            responseBody="";
                        errorFile.setText("ERROR! Failed to read file. " + responseBody);
                    });
                }
            }
        });
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public int getVersion() {
        return version;
    }
    public void getYazFromBank(){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/yaz")
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                });
            }
            @Override public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            String yaz = response.header("yaz");
                            currentYaz.setText(yaz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {});
                }
            }
        });
    }
    public List<String> getCategories(){
        return categories;
    }
    public List<String> readCategoriesFromJson(String categoriesJSON) {
        Gson gson = new Gson();
        List<String> categories = null;
        try {
            Type listType = new TypeToken<List<String>>() {}.getType();
            categories = gson.fromJson(categoriesJSON, listType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (categories == null)
                categories = new ArrayList<>();
            return categories;
        }
    }
    public void updateCategories() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/categories")
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        try {
                            List<String> allCategories = readCategoriesFromJson(response.body().string());
                            categories.clear();
                            categories.addAll(allCategories);
                            inlayComponentController.setCategoriesOptions();
                            createLoanComponentController.setCategories();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
    public void newLoansInSystem(){
        informationComponentController.showData();
    }
    public double getCurrentBalance(){
        return Double.parseDouble(accountBalance.getText());
    }
    public clientHomePageController(){
            yazProperty=new SimpleIntegerProperty();
    }
    public void setMainController(mainScreenClientController mainController){
        this.mainController=mainController;
    }
    public void setClientName(String clientName){
        this.clientName.setText(clientName);
        saleLoansComponentController.setName(clientName);
    }
    public String getClientName(){
        return clientName.getText();
    }
    public void setAccountBalance(Double balance){
        this.accountBalance.setText(String.valueOf(balance));
    }
    public void startDataRefresher() {
        dataRefresher refresher = new dataRefresher(this::showCategories, this::showBalance, this::showYaz, this::showMovements, this::showLoanLender,this::showLoanLoner,this::showVersion,this::setRewind,this::setLoansForSale);
        refresher.setHomePageController(this);
        timer = new Timer();
        timer.schedule(refresher, 0, 2000);
    }
    public void setLoansForSale(List<LoanDTO> loans){
        saleLoansComponentController.setLoansToBuyTables(loans);
    }
    public void setRewind(Integer lookingBack){
        Platform.runLater(()->{
            if (lookingBack != 0) {
                currentYaz.setText("REWIND looking back on yaz: " + lookingBack);
                insertFile.setDisable(true);
                createLoanComponentController.setDisable();
                informationComponentController.setDisable();
                paymentComponentController.setDisable();
                inlayComponentController.setDisable();
                saleLoansComponentController.setDisable();

            }
            else {
                insertFile.setDisable(false);
                createLoanComponentController.setAble();
                informationComponentController.setAble();
                paymentComponentController.setAble();
                inlayComponentController.setAble();
                saleLoansComponentController.setAble();
            }


        });
    }
    public void showCategories(List<String> newCategories) {
        Platform.runLater(() -> {
            categories.clear();
            this.categories=newCategories;
            inlayComponentController.refreshData();
            createLoanComponentController.refreshData();
        });
    }
    public void showMovements(Map<Integer,List<MovementDTO>> movements) {
        Platform.runLater(() -> {
            informationComponentController.refreshMovementsData(movements);
        });
    }
    public void showYaz(int yaz) {
        Platform.runLater(() ->
           currentYaz.setText(String.valueOf(yaz))
        );
    }
    public void showVersion(int version) {
        Platform.runLater(() ->
                this.version=version
        );
    }
    public void showBalance(Double balance) {
        Platform.runLater(() ->
            accountBalance.setText(String.valueOf(balance))
        );
    }
    public void showLoanLender(List<LoanDTO> loanDTOS) {
        Platform.runLater(() -> {
            informationComponentController.refreshLenderLonerData(loanDTOS);
            saleLoansComponentController.setLoansLenderTables(loanDTOS.stream().filter(loanDTO -> !loanDTO.getListedForSale())
                    .filter(loanDTO -> loanDTO.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList()));
        });
    }
    public void showLoanLoner(List<LoanDTO> loanDTOS) {
        Platform.runLater(() -> {
            informationComponentController.refreshLoansLonerData(loanDTOS);
            paymentComponentController.refreshPayment(loanDTOS);
        });
    }
}
