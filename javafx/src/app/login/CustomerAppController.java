package app.login;

import app.main.AppController;
import engine.Bank;
import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;
import servlet.HttpClientUtil;

import java.io.IOException;

public class CustomerAppController {

    private Bank bank;
    private Stage primaryStage;
    @FXML private ScrollPane mainComponent;
    @FXML private TextField userNameTF;
    @FXML private Button loginBTN;
    @FXML private Label msgLabel;
    private AppController mainController;

    public CustomerAppController() {
        this.bank = new Bank();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void login(ActionEvent event) throws IOException {

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/login")
                .newBuilder()
                .addQueryParameter("Name", userNameTF.getText())
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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    Platform.runLater(() ->
                            msgLabel.setText("There is no " + userNameTF.getText() + " in system!")
                    );
                }
                else {
                    Platform.runLater(() ->
                            msgLabel.setText("hello" + userNameTF.getText())
                    );
                    Platform.runLater(() -> {
                            try {
                                mainController.loginSuccess(userNameTF.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    );
                }
            }
        });
    }
}