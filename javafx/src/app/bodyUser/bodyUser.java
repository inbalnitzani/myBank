package app.bodyUser;
import app.information.informationController;
import app.inlayController.inlayController;
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
    @FXML private inlayController scrambleComponentController;
    @FXML private Parent scrambleComponent;
    private ClientDTO clientDTO;
    private AppController mainController;
    private ClientDTO user;

    public void setUser(ClientDTO user) {
        this.user = user;
        informationComponentController.setUser(user);
    }

    @FXML public void initialize(){
        if (scrambleComponentController!=null && informationComponentController!=null) {
            this.scrambleComponentController.setBodyUser(this);
            this.informationComponentController.setBodyUser(this);
        }
    }
    public void updateUserViewer(String client) {
        clientDTO = mainController.getClientByName(client);
    }

    public void showErrorPopUp(Exception err){
        mainController.showError(err);
    }
    public bodyUser(){}
    public void setData(){
        scrambleComponentController.setCategories();
        scrambleComponentController.setChooseMinInterest();
    }
    public double getClientBalance(){return clientDTO.getCurrBalance();}
    public List<String> getCategories(){return mainController.getCategories();}
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public void showData(ClientDTO clientDTO) {
    informationComponentController.showData(clientDTO);
    }

}



