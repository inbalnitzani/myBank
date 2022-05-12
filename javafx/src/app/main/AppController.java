package app.main;
import app.bodyAdmin.bodyAdmin;
import app.bodyUser.bodyUser;
import app.header.headerController;
import bank.Bank;
import bank.BankInterface;
import dto.ClientDTO;
import exception.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import static app.constParameters.BODY_ADMIN_PATH;
import static app.constParameters.BODY_USER_PATH;

public class AppController {

    @FXML private headerController headerComponentController;
    @FXML private Parent headerComponent;
    @FXML private BorderPane mainComponent;
    private bodyAdmin bodyAdminController;
    private bodyUser bodyUserController;
    private Parent adminComponentRoot;
    private Parent userComponentRoot;
    private BankInterface myBank;
    private SimpleBooleanProperty fileInSystem;
    private SimpleIntegerProperty time;

    public AppController() {
        myBank = new Bank();
        fileInSystem = new SimpleBooleanProperty(false);
        time = new SimpleIntegerProperty();
    }

    @FXML public void initialize() throws IOException {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
        loadAdmin();
        loadUser();
    }

    public void loadAdmin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(BODY_ADMIN_PATH);
        fxmlLoader.setLocation(url);
        adminComponentRoot = fxmlLoader.load(url.openStream());
        bodyAdminController=fxmlLoader.getController();
        bodyAdminController.setMainController(this);
    }
    public void loadUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(BODY_USER_PATH);
        fxmlLoader.setLocation(url);
        userComponentRoot = fxmlLoader.load(url.openStream());
        bodyUserController=fxmlLoader.getController();
        bodyUserController.setMainController(this);
    }
    public void checkBodyToShow(String user) {
        if (user.equals("No clients in system - no file.")) {
            mainComponent.getChildren().remove(mainComponent.getCenter()); //remove existing fxml from center.
        } else {
            if (user.equals("Admin")) {
                mainComponent.setCenter(adminComponentRoot);
            } else {
                mainComponent.setCenter(userComponentRoot);
            }
        }
    }
    public Collection<ClientDTO> getClients() {
        return myBank.getClients();
    }
    public void setUserOptions(){
        headerComponentController.setUsers();
    }
    public boolean isFileInSystem() {
        return fileInSystem.get();
    }
    public void increaseYaz(){
        myBank.promoteTime();
        time.setValue(myBank.getWorldTime());
    }

    public void getFile(String path) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        if (!myBank.getXMLFile(path)) {
            //add exception !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println("invalid");
        } else {
            fileInSystem.setValue(true);
            time.setValue(myBank.getWorldTime());
            headerComponentController.getYaz().textProperty().bind(time.asString());
            headerComponentController.updateFileName(path);
            setUserOptions();
        }
    }

}
