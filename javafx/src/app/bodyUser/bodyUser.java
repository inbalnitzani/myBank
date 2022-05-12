package app.bodyUser;

import app.main.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class bodyUser {

    @FXML private Label body;
    @FXML private Tab information,scramble,payment;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

}
