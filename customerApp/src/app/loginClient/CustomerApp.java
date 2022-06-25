package app.loginClient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.net.URL;

public class CustomerApp extends Application {
    @Override public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Login Page");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("customerLoginPage.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane root = fxmlLoader.load(url.openStream());

        CustomerAppController customerAppController = fxmlLoader.getController();
        customerAppController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            customerAppController.logout(primaryStage);
        });

    }
}
