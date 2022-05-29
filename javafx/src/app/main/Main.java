package app.main;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Main extends Application {

        @Override
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
        }
        public static void main(String args[]){
            launch(args);
        }
    }


/*
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

<<<<<<< HEAD
        Scene scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add(getClass().getResource("PinkMain.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/


