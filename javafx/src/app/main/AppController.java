package app.main;
import app.bodyAdmin.bodyAdmin;
import app.bodyInterface;
import app.bodyUser.bodyController;
import app.header.headerController;
import bank.Bank;
import bank.BankInterface;
import dto.ClientDTO;
import exception.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class AppController {

    @FXML private headerController headerComponentController;
    @FXML private Parent headerComponent;

    private bodyInterface bodyComponentController;
    private Parent bodyComponent;

    @FXML private BorderPane mainComponent;

    private BankInterface myBank;
    private SimpleBooleanProperty fileInSystem;

    public AppController() {
        myBank = new Bank();
        fileInSystem = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
    }

    public void checkBodyToShow(String user) throws IOException {
        if(user.equals("Admin")) {
            bodyComponentController = new bodyAdmin();

            FXMLLoader fxmlLoader = new FXMLLoader();
            mainComponent.getChildren().remove(mainComponent.getCenter()); //remove existing fxml from center.

            URL url=getClass().getResource("../bodyAdmin/bodyAdmin.fxml");
            fxmlLoader.setLocation(url);
            Parent root=fxmlLoader.load(url.openStream());
            mainComponent.setCenter(root);

        } else{
            mainComponent.getChildren().remove(mainComponent.getCenter()); //remove existing fxml from center.

        }
    }

    public Collection<ClientDTO> getClients() {
        return myBank.getClients();
    }

    public void getFile(String path) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        if (!myBank.getXMLFile(path)) {
            //add exception !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println("invalid");
        }
    }
}
