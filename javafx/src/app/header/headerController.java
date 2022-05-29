package app.header;

import app.constParameters;
import app.main.AppController;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import dto.ClientDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class headerController {
    @FXML private ComboBox<String> userOptions;
    @FXML private Label file;
    @FXML private Label yaz;

    private SimpleIntegerProperty currYaz;
    private AppController mainController;

    @FXML public void initialize() {
        yaz.setText("No file in system");
        userOptions.getItems().add("Admin");
    }

    @FXML void chooseUser(ActionEvent event) {
        mainController.changeUser(userOptions.getValue());
    }

    public Label getYaz() {
        return yaz;
    }

    public headerController() {
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUsersComboBox() {
        if (mainController.isFileInSystem()) {
            userOptions.getItems().clear();
            userOptions.getItems().add(constParameters.ADMIN);
        }
        for (ClientDTO clientDTO : mainController.getClients())
            userOptions.getItems().add(clientDTO.getFullName());
    }

    public void updateComponentForNewFile(String path){
        setUsersComboBox();
        updateFileName(path);
    }
    public void updateFileName(String fileName) {
        file.setText(fileName);
    }
}
