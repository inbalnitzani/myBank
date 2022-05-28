package app.main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
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
        Group root1 = new Group();
        Group root2 = new Group();
        Group root3 = new Group();



        Scene scene1 = new Scene(root1, 1000, 500);
        scene1.getStylesheets().add(getClass().getResource("Default.css").toExternalForm());
        Scene scene2 = new Scene(root2, 1000, 500);
        scene2.getStylesheets().add(getClass().getResource("Colourful.css").toExternalForm());
        Scene scene3 = new Scene(root3, 1000, 500);
        scene3.getStylesheets().add(getClass().getResource("PinkMain.css").toExternalForm());

        /*@Override
        public void start(Stage primaryStage) {
//            //Creating a hexagon
//            Polygon hexagon = new Polygon();
//            TriangleMesh triangleMesh=new TriangleMesh();
//
//            triangleMesh.setVertexFormat();
//
//            //Adding coordinates to the hexagon
//            hexagon.getPoints().addAll(new Double[]{
//                    200.0, 50.0,
//                    400.0, 50.0,
//                    450.0, 150.0,
//                    400.0, 250.0,
//                    200.0, 250.0,
//                    150.0, 150.0,
//            });
//            //Setting the fill color for the hexagon
//            hexagon.setFill(Color.BLUE);
//
//            //Creating a rotate transition
//            RotateTransition rotateTransition = new RotateTransition();
//
//            //Setting the duration for the transition
//            rotateTransition.setDuration(Duration.millis(1000));
//
//            //Setting the node for the transition
//            rotateTransition.setNode(hexagon);
//
//            //Setting the angle of the rotation
//            rotateTransition.setByAngle(360);
//
//            //Setting the cycle count for the transition
//            rotateTransition.setCycleCount(50);
//
//            //Setting auto reverse value to false
//            rotateTransition.setAutoReverse(false);
//
//            //Playing the animation
//            rotateTransition.play();
//
//            //Creating a Group object
//            Group root = new Group(hexagon);
//
//            //Creating a scene object
//            Scene scene = new Scene(root, 600, 300);
//
//            //Setting title to the Stage
//            stage.setTitle("Rotate transition example ");
//
//            //Adding scene to the stage
//            stage.setScene(scene);
//
//            //Displaying the contents of the stage
//            stage.show();
            //Drawing a Circle
            Group root1 = new Group();
            Group root2 = new Group();

            Scene scene1 = new Scene(root1, 300, 250);
            Scene scene2 = new Scene(root2, 300, 250);

            Rectangle rectangle2 = new Rectangle(300, 250);
            rectangle2.setFill(Color.BLUE);
            root2.getChildren().add(rectangle2);

            Rectangle rectangle1 = new Rectangle(300, 250);
            rectangle1.setFill(Color.RED);
            root1.getChildren().add(rectangle1);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene1);
            primaryStage.show();

            rectangle1.setOnMouseClicked(e->{

                // Replace root1 with new pane
                root1.getChildren().setAll();
                // create transtition
                Timeline timeline = new Timeline();

                timeline.setOnFinished(t->{
                    // remove pane and restore scene 1
                    root1.getChildren().setAll(rectangle1);
                    // set scene 2
                    primaryStage.setScene(scene2);
                });
                timeline.play();
            });
            rectangle2.setOnMouseClicked(e->{

            });
        }*/

//
//        Button button = new Button();
//        VBox vBox = new VBox();
//        vBox.getChildren().add(new javafx.scene.control.Label());
//        root1.getChildren().add(vBox);
//
//        primaryStage.setScene(scene1);
//        primaryStage.show();
//
//
//
//
//
//        rectangle1.setOnMouseClicked(e->{
//            // Create snapshots with the last state of the scenes
//            WritableImage wi = new WritableImage(300, 250);
//            Image img1 = root1.snapshot(new SnapshotParameters(),wi);
//            ImageView imgView1= new ImageView(img1);
//            wi = new WritableImage(300, 250);
//            Image img2 = root2.snapshot(new SnapshotParameters(),wi);
//            ImageView imgView2= new ImageView(img2);
//            // Create new pane with both images
//            imgView1.setTranslateX(0);
//            imgView2.setTranslateX(300);
//            StackPane pane= new StackPane(imgView1,imgView2);
//            pane.setPrefSize(300,250);
//            // Replace root1 with new pane
//            root1.getChildren().setAll(pane);
//            // create transtition
//            Timeline timeline = new Timeline();
//            KeyValue kv = new KeyValue(imgView2.translateXProperty(), 0, Interpolator.EASE_BOTH);
//            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
//            timeline.getKeyFrames().add(kf);
//            timeline.setOnFinished(t->{
//                // remove pane and restore scene 1
//                root1.getChildren().setAll(rectangle1);
//                // set scene 2
//                primaryStage.setScene(scene2);
//            });
//            timeline.play();
//        });
    }
}
