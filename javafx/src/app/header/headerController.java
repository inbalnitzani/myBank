package app.header;

import app.constParameters;
import app.main.AppController;
import com.sun.istack.internal.NotNull;
import dto.ClientDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import util.HttpClientUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class headerController {
    @FXML private ComboBox<String> userOptions;
    @FXML private Label file;
    @FXML private Label yaz;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    private SimpleIntegerProperty currYaz;
    private AppController mainController;

    @FXML public void initialize() {
        yaz.setText("No file in system");
        userOptions.getItems().add("Admin");
    }

    @FXML void chooseUser(ActionEvent event) throws IOException {
        mainController.changeUser(userOptions.getValue());
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/hello/greeter?Name=maiush")
                .newBuilder()
                .addQueryParameter("Name", "mai")
                .build()
                .toString();

        HttpClientUtil.sync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Platform.runLater(() ->
//                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
//                );
                System.out.println("fail");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("good");
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        //chatAppMainController.updateUserName(userName);
                        //chatAppMainController.switchToChatRoom();
                    });
                }
            }
        });
//

    }

    public Label getYaz() {
        return yaz;
    }

    public headerController() {
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUsersComboBox() {
        if (mainController.isFileInSystem()) {
            userOptions.getItems().clear();
            userOptions.getItems().add(constParameters.ADMIN);
        }
        for (ClientDTO clientDTO : mainController.getClients())
            userOptions.getItems().add(clientDTO.getFullName());
    }

    public void updateComponentForNewFile(String path){
        setUsersComboBox();
        updateFileName(path);
    }
    public void updateFileName(String fileName) {
        file.setText(fileName);
    }
}
