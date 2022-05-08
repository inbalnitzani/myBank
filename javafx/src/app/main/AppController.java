package app.main;

import app.bodyUser.bodyController;
import app.header.headerController;
import bank.Bank;
import bank.BankInterface;
import dto.ClientDTO;
import exception.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Collection;

public class AppController {

    private BankInterface myBank;
    private SimpleBooleanProperty fileInSystem;

    @FXML private headerController headerComponentController;
    @FXML private HBox headerComponent;

    @FXML private bodyController bodyComponentController;
    @FXML private HBox bodyComponent;

    public AppController(){
        myBank=new Bank();
        fileInSystem=new SimpleBooleanProperty(false);
    }

    @FXML public void initialize() {
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
        }
    }
    public void setHeaderComponentController(headerController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setBodyComponentController(bodyController bodyComponentController) {
        this.bodyComponentController = bodyComponentController;
        bodyComponentController.setMainController(this);
    }

    public Collection<ClientDTO> getClients() {
        return myBank.getClients();
    }

    public void getFile(String path) throws CustomerException, NegativeTimeException, JAXBException, FileNotFoundException, NamesException, NegativeLoanCapitalException, PaceException, CategoriesException, XmlException, NegativeBalanceException, InterestException, IdException {
        if(!myBank.getXMLFile(path))
        {
            System.out.println("invalid");
        }

    }
}
