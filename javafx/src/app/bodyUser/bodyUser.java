package app.bodyUser;

import app.main.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class bodyUser {

    @FXML private Label body;
    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

}
