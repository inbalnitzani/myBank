package app.body;

import app.main.mainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class bodyController {

    @FXML
    private Label body;
    private app.main.mainController mainController;

    public void setMainController(mainController mainController) {

        this.mainController = mainController;
    }
}
