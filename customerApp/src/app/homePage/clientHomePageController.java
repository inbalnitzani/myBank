package app.homePage;

import app.constParameters;
import app.mainScreenClient.mainScreenClientController;
import com.sun.istack.internal.NotNull;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.File;
import java.io.IOException;

public class clientHomePageController {

    @FXML private Label clientName;
    @FXML private Label currentYaz;
    private mainScreenClientController mainController;
    private SimpleIntegerProperty yazProperty;
    @FXML private Button insertFile;

    @FXML public void initialize() {
        yazProperty.setValue(1);
        currentYaz.textProperty().bind(yazProperty.asString());
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
        return clientName.toString();
    }
}