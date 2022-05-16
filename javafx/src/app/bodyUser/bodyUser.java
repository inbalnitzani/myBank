package app.bodyUser;

import app.main.AppController;
import dto.ClientDTO;
import dto.LoanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class bodyUser {

    @FXML private Label body;
    @FXML private Tab information,scramble,payment;
    @FXML private information informationComponentController;
    @FXML private Parent informationComponent;
    private AppController mainController;

    private ClientDTO user;


    public void setUser(ClientDTO user) {
        this.user = user;
        informationComponentController.setUser(user);
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public void showData(ClientDTO clientDTO) {
    informationComponentController.showData(clientDTO);
    }

}



