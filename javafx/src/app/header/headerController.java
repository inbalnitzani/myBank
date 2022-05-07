package app.header;

import app.main.mainController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class headerController {

    @FXML
    private ComboBox<?> user;

    @FXML
    private Label file;

    @FXML
    private TextField yaz;
    private app.main.mainController mainController;

    public void setMainController(mainController mainController) {
        this.mainController = mainController;
    }
}
