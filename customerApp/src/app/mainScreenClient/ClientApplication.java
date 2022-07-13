package app.mainScreenClient;

import app.constParameters;
import app.homePage.clientHomePageController;
import com.sun.istack.internal.NotNull;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class ClientApplication extends Application {
    Scene scene;
    VBox data=new VBox(2);
    Button pinkButton=new Button("Pink Version");
    Button colorfulButton=new Button("Colorful Version");
    Button defaultButton=new Button("Boring Version");
    HBox hBox = new HBox(pinkButton,colorfulButton,defaultButton);

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bank");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("mainScreenClient.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane root = fxmlLoader.load(url.openStream());

        root.fitToHeightProperty();
        data.getChildren().addAll(hBox,root);
        scene = new Scene(data, 1000, 500);
        scene.getStylesheets().add(getClass().getResource("Default.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        pinkButton.setOnAction(event -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("PinkMain.css").toExternalForm());
        });

        colorfulButton.setOnAction(event -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("Colourful.css").toExternalForm());
        });

        defaultButton.setOnAction(event -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("Default.css").toExternalForm());
        });
    }
}


