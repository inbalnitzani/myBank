package mainScreenAdmin;

import com.sun.istack.internal.NotNull;
import homePage.adminHomePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

public class AdminApplication extends Application {
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
        URL url = getClass().getResource("mainScreenAdmin.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane root = fxmlLoader.load(url.openStream());
        mainScreenAdminController mainScreenAdminController = fxmlLoader.getController();

        root.fitToHeightProperty();
        data.getChildren().addAll(hBox,root);
        scene = new Scene(data, 1000, 500);
        scene.getStylesheets().add(getClass().getResource("Default.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();


        //////////////////////////////////////////////////////////
        primaryStage.setOnCloseRequest(event -> {
            adminHomePageController homePageController = mainScreenAdminController.getHomePageController();
            if(homePageController!=null){
                logoutFromSystem(mainScreenAdminController.getClientName());
            }
            ((Stage) primaryStage.getScene().getWindow()).close();
        });///////////////////////////////////////////////////////////

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
    public void logoutFromSystem(String name){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/logout")
                .newBuilder()
                .addQueryParameter("Name", name)
                .addQueryParameter("LOGIN_TYPE","client")
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override public void onFailure(@NotNull Call call, @NotNull IOException e) {}
            @Override public void onResponse(@NotNull Call call, @NotNull Response response) {}
        });
    }
}


