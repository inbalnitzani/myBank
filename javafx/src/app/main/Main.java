package app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bank");
        FXMLLoader fxmlLoader=new FXMLLoader();
        URL url=getClass().getResource("mainController.fxml");
        fxmlLoader.setLocation(url);
        Parent root=fxmlLoader.load(url.openStream());

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
