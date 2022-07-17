package app.mainScreenClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        scene = new Scene(data, 800, 600);
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


