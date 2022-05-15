package app.inlayController;

import app.bodyUser.bodyUser;
import app.main.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class inlayController {

    private bodyUser bodyUser;
    @FXML private TextField amountToInvest;
    @FXML private ComboBox<String> categoryToInvest;
    @FXML private TextField chooseMinTime;
    @FXML private ComboBox<?> ChooseMinInterest;

    @FXML public void initialize() {

    }

    @FXML
    void chooseCategory(ActionEvent event) {
        System.out.println(categoryToInvest.getValue());
    }

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setCategories(){
        categoryToInvest.getItems().addAll(this.bodyUser.getCategories());
    }
    private void gh(){

    }

}
