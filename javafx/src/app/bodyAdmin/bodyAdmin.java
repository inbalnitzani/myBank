package app.bodyAdmin;

import app.main.AppController;
import exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class bodyAdmin {

    @FXML private VBox bodyAdmin;
    @FXML private HBox loadFileButton;
    @FXML private Button loadFile;
    @FXML private Button increaseYaz;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    @FXML void increaseYaz(ActionEvent event) {
        mainController.increaseYaz();
    }
    @FXML void loadNewFile(ActionEvent event) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();
        mainController.getFile(path);
    }

}


