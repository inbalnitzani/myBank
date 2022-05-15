package app.bodyUser;
import app.information.informationController;
import app.inlayController.inlayController;
import app.main.AppController;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.List;

public class bodyUser {

    @FXML private inlayController scrambleComponentController;
    @FXML private Parent scrambleComponent;
    @FXML private informationController informationComponentController;
    @FXML private Parent informationComponent;
    public AppController mainController;

    @FXML public void initialize(){
        if (scrambleComponentController!=null && informationComponentController!=null) {
            this.scrambleComponentController.setBodyUser(this);
            this.informationComponentController.setBodyUser(this);
        }
    }

    public bodyUser(){}
    public void setData(){
        scrambleComponentController.setCategories();
    }
    public List<String> getCategories(){return mainController.getCategories();}
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

}
