package app.homePage;

import app.createLoan.createLoanController;
import app.inlayController.inlayController;
import app.mainScreenClient.mainScreenClientController;
import app.payment.paymentController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class clientHomePageController {

    @FXML private Label clientName;
    @FXML private Label currentYaz;
    @FXML private Label accountBalance;
    @FXML private Button insertFile;
    private mainScreenClientController mainController;
    private SimpleIntegerProperty yazProperty;
    private List<String> categories;
    @FXML private createLoanController createLoanComponentController;
    @FXML private Parent createLoanComponent;
    @FXML private app.information.informationController informationComponentController;
    @FXML private Parent informationComponent;
    @FXML private paymentController paymentComponentController;
    @FXML private Parent paymentComponent;
    @FXML private Parent inlayComponent;
    @FXML private inlayController inlayComponentController;

    @FXML public void initialize() {
        getYazFromBank();
        createLoanComponentController.setHomePageController(this);
        informationComponentController.setHomePageController(this);
        paymentComponentController.setHomePageController(this);
        inlayComponentController.setHomePageController(this);
        categories=new ArrayList<>();
        synchronized (this){
            updateCategories();
        }
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
                } else {
                    Platform.runLater(() -> {
                        String responseBody = null;
                        try {
                            responseBody = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
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
//                    Platform.runLater(() ->
//                            msgLabel.setText("Failure!")
//                    );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
//                    int status =response.code();
//                    if (status != HttpServletResponse.SC_OK) {
//                        if(status == HttpServletResponse.SC_FORBIDDEN){
//                            Platform.runLater(() ->
//                                    msgLabel.setText("You must enter a name!")
//                            );
//                        }else {
//                            Platform.runLater(() ->
//                                    msgLabel.setText(userNameTF.getText()+" is already in system. Please enter a different name")
//                            );
//                        }
//                    } else {
//                        Platform.runLater(() ->
//                                {
//                                    try {
//                                        mainController.loginClientSuccess(userNameTF.getText());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                        );
//                    }
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
    }
    public String getClientName(){
        return clientName.getText();
    }
    public void setAccountBalance(Double balance){
        this.accountBalance.setText(String.valueOf(balance));
    }
}
