package app.bodyUser;
import app.information.informationController;
import app.inlayController.inlayController;
import app.main.AppController;
import dto.ClientDTO;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.List;

public class bodyUser {

    @FXML private inlayController scrambleComponentController;
    @FXML private Parent scrambleComponent;
    @FXML private informationController informationComponentController;
    @FXML private Parent informationComponent;
    private ClientDTO clientDTO;
    public AppController mainController;

    @FXML public void initialize(){
        if (scrambleComponentController!=null && informationComponentController!=null) {
            this.scrambleComponentController.setBodyUser(this);
            this.informationComponentController.setBodyUser(this);
        }
    }
    public void updateUserViewer(String client) {
        clientDTO = mainController.getClientByName(client);
        informationComponentController.setUser(clientDTO);
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
    public void showData(){informationComponentController.showData();}
}
