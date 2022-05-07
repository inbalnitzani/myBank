package app.main;

import app.body.bodyController;
import app.header.headerController;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class mainController {

    @FXML private headerController headerComponentController;
    @FXML private bodyController bodyComponentController;
    @FXML private Parent headerComponent;
    @FXML private Parent bodyComponent;

    @FXML public void initialize() {
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
        }
    }
//    public void setHeaderComponentController(headerController headerComponentController) {
//        this.headerComponentController = headerComponentController;
//        headerComponentController.setMainController(this);
//    }
//
//    public void setBodyComponentController(bodyController bodyComponentController) {
//        this.bodyComponentController = bodyComponentController;
//        bodyComponentController.setMainController(this);
//    }

}
