package app.bodyUser;
import app.information.informationController;
import app.inlayController.inlayController;
import app.main.AppController;
import app.payment.paymentController;
import dto.ClientDTO;
import dto.LoanDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loan.LoanTerms;

import java.util.List;

public class bodyUser {

    @FXML private inlayController scrambleComponentController;
    @FXML private Parent scrambleComponent;
    @FXML private informationController informationComponentController;
    @FXML private Parent informationComponent;
    @FXML private paymentController paymentComponentController;
    @FXML private Parent paymentComponent;
    private ClientDTO clientDTO;
    public AppController mainController;



    @FXML public void initialize(){
        if (scrambleComponentController!=null && informationComponentController!=null) {
            this.scrambleComponentController.setBodyUser(this);
            this.informationComponentController.setBodyUser(this);
            this.paymentComponentController.setBodyUser(this);
        }
    }
    public void updateUserViewer(String client) {
        clientDTO = mainController.getClientByName(client);
        informationComponentController.setUser(clientDTO);
        paymentComponentController.setClient(clientDTO);
        scrambleComponentController.setDataAccordingToClient();
    }
    public void updateClientInfo(){
        clientDTO=mainController.getClientByName(clientDTO.getFullName());
    }
    public int startInlayProcess(List<LoanDTO> loansToInvest, String clientName){
         return mainController.startInlayProcess(loansToInvest,clientName);
    }
    public bodyUser(){}
    public void setData(){
        scrambleComponentController.setChooseMinInterest();
        scrambleComponentController.setCategoriesChooser();
    }
    public double getClientBalance(){return clientDTO.getCurrBalance();}
    public List<String> getCategories(){return mainController.getCategories();}
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public void showData(){informationComponentController.showData();
    paymentComponentController.showData();
    }
    public void chargeAcount(String clientName,double amount){
        mainController.chargeAccount(clientName,amount);
    }
    public void withdrawFromAcount(String clientName,double amount){mainController.withdrawFromAccount(clientName,amount);}
    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms){
        return mainController.findMatchLoans(clientName,terms);
    }
    public ClientDTO getClientDTO(){return clientDTO;}
    public List<LoanDTO> getLoans(){return mainController.getLoans();}

}
