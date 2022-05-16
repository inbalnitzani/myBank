package app.inlayController;

import app.bodyUser.bodyUser;
import app.main.AppController;
import exception.NegativeAmountException;
import exception.NegativeBalanceException;
import exception.NotEnoughMoney;
import exception.StringInsteadOfNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class inlayController {

    private bodyUser bodyUser;
    @FXML private TextField amountToInvest;
    @FXML private ComboBox<String> categoryToInvest;
    @FXML private TextField chooseMinTime;
    @FXML private ComboBox<Integer> chooseMinInterest;

    @FXML public void initialize() {}

    @FXML
    void chooseCategory(ActionEvent event) {
        System.out.println(categoryToInvest.getValue());
    }

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    @FXML void checkAmountToInvest(ActionEvent event) throws StringInsteadOfNumber {
        String input = amountToInvest.getCharacters().toString().trim();
        double number;
        try {
            number = Double.parseDouble(input);
            if(number<0){
                bodyUser.showErrorPopUp(new NegativeAmountException(number));
            }
            else{
                double currBalance=bodyUser.getClientBalance();
                if(number>currBalance){
                    bodyUser.showErrorPopUp(new NotEnoughMoney(currBalance));
                }
            }
        } catch (Exception err) {
            amountToInvest.clear();
            bodyUser.showErrorPopUp(new StringInsteadOfNumber(input));
        }

    }
    public void setChooseMinInterest(){
        for (int i=1;i<100;i++){
            chooseMinInterest.getItems().add(i);
        }
    }
    public void setCategories(){
        categoryToInvest.getItems().addAll(bodyUser.getCategories());
    }

}
