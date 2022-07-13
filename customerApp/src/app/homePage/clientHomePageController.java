package app.homePage;

import app.createLoan.createLoanController;
import app.information.informationController;
import app.mainScreenClient.mainScreenClientController;
import app.payment.paymentController;
import com.sun.istack.internal.NotNull;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class clientHomePageController {

    @FXML private Label clientName;
    @FXML private Label currentYaz;
    @FXML private Label accountBalance;
    @FXML private Button insertFile;
    private mainScreenClientController mainController;
    private SimpleIntegerProperty yazProperty;
    @FXML private createLoanController createLoanComponentController;
    @FXML private Parent createLoanComponent;
    @FXML private app.information.informationController informationComponentController;
    @FXML private Parent informationComponent;
    @FXML private paymentController paymentComponentController;
    @FXML private Parent paymentComponent;

    @FXML public void initialize() {
        yazProperty.setValue(1);
        currentYaz.textProperty().bind(yazProperty.asString());
        createLoanComponentController.setHomePageController(this);
        informationComponentController.setHomePageController(this);
        paymentComponentController.setHomePageController(this);
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
