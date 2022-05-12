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
import java.io.IOException;

public class headerController {

    private static final String ADMIN  = "Admin";
    @FXML private ComboBox<String> userOptions;
    @FXML private Label file;
    @FXML private Button button;
    @FXML private Label yaz;

    private SimpleBooleanProperty fileInSystem;
    private SimpleIntegerProperty currYaz;
    private AppController mainController;

    @FXML public void initialize() {
        yaz.textProperty().bind(currYaz.asString());
        initUserOptions();
    }

    @FXML void chooseUser(ActionEvent event) throws IOException {
        mainController.checkBodyToShow(userOptions.getValue());
    }

    @FXML void uploadFile(ActionEvent event) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
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
    }

    public void initUserOptions(){
        userOptions.getItems().add("Admin");
        userOptions.getItems().add("No clients in system - no file.");
    }

    public headerController() {
        fileInSystem = new SimpleBooleanProperty(false);
        currYaz = new SimpleIntegerProperty();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUsers() {
        if (fileInSystem.getValue()) {
            userOptions.getItems().clear();
            userOptions.getItems().add(ADMIN);
            for (ClientDTO clientDTO : mainController.getClients())
                userOptions.getItems().add(clientDTO.getFullName());
        }
    }
}
