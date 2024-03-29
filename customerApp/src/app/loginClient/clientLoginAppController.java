package app.loginClient;

import app.constParameters;
import app.mainScreenClient.mainScreenClientController;
import engine.Bank;
import com.sun.istack.internal.NotNull;
import jakarta.servlet.http.HttpServletResponse;
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

public class clientLoginAppController {

    private Bank bank;
    private Stage primaryStage;
    @FXML private ScrollPane mainComponent;
    @FXML private TextField userNameTF;
    @FXML private Button loginBTN;
    @FXML private Label msgLabel;
    private mainScreenClientController mainController;


    public void setMainController(mainScreenClientController mainController){
        this.mainController=mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

     @FXML void login(ActionEvent event) {

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/login")
                .newBuilder()
                .addQueryParameter("Name", userNameTF.getText())
                .addQueryParameter(constParameters.LOGIN_TYPE, constParameters.TYPE_CLIENT)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        msgLabel.setText("ERROR!! Unknown problem occurred. Please try later...")
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
                                msgLabel.setText(userNameTF.getText()+" is already in system. Please enter a different name")
                        );
                    }
                } else {
                    Platform.runLater(() -> {
                        try {
                            mainController.loginClientSuccess(userNameTF.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}