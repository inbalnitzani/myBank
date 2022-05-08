package app.header;

import app.main.AppController;
import dto.ClientDTO;
import exception.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import javax.script.Bindings;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class headerController {

    @FXML private ComboBox<String> userOptions;
    @FXML private Label file;
    @FXML private Button button;
    @FXML private Label yaz;

    private SimpleBooleanProperty fileInSystem;
    private SimpleIntegerProperty currYaz;
    private AppController mainController;

    @FXML void chooseUserAction(ActionEvent event) {    }

    public headerController() {
        fileInSystem = new SimpleBooleanProperty(false);
        currYaz = new SimpleIntegerProperty();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML public void initialize() {
        yaz.textProperty().bind(currYaz.asString());
        userOptions.getItems().add("Admin");
        userOptions.getItems().add("No clients in system - no file.");
    }

    @FXML void onActionButton(ActionEvent event) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();
        file.setText(path);
        mainController.getFile(path);
        fileInSystem.setValue(true);
        setUsers();
        button.setText(userOptions.getValue());
    }

    public void setUsers() {
        if (fileInSystem.getValue()) {
            for (ClientDTO clientDTO : mainController.getClients())
                userOptions.getItems().add(clientDTO.getFullName());
            userOptions.getItems().remove("No clients in system - no file.");
        }
    }
}
