package loginAdmin;

import app.constParameters;
import app.main.AppController;
import com.sun.istack.internal.NotNull;
import engine.Bank;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.IOException;

public class AdminAppController {

    private Bank bank;
    private Stage primaryStage;
    @FXML
    private ScrollPane mainComponent;
    @FXML private TextField userNameTF;
    @FXML private Button loginBTN;
    @FXML private Label msgLabel;
    private AppController mainController;

    public AdminAppController() {
        this.bank = new Bank();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML void login(ActionEvent event) throws IOException {

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/login")
                .newBuilder()
                .addQueryParameter("Name", userNameTF.getText())
                .addQueryParameter(constParameters.LOGIN_TYPE,constParameters.TYPE_ADMIN)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        msgLabel.setText("Failure!")
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status =response.code();
                if (status != HttpServletResponse.SC_OK) {
                    if(status == HttpServletResponse.SC_FORBIDDEN){
                        Platform.runLater(() ->
                                msgLabel.setText("You must enter a name!")
                        );
                    }else {
                        Platform.runLater(() ->
                                msgLabel.setText("There is already admin in system...")
                        );
                    }
                } else {
                    Platform.runLater(() -> {
                        System.out.println("hi");
                            //mainController.loginAdminSuccess(userNameTF.getText());
                    });
                }
            }
        });
    }
}
