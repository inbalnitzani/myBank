package app.bodyUser;
import app.information.informationController;
import app.inlayController.inlayController;
import app.main.AppController;
import app.payment.paymentController;
import dto.ClientDTO;
import dto.LoanDTO;
import exception.NotEnoughMoney;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
          //  this.paymentComponentController.setBodyUser(this);
        }
    }
    public void updateUserViewer(String client) {
        paymentComponentController.setClient(clientDTO);
        scrambleComponentController.setDataAccordingToClient();

    }
    public void updateClientInfo(){
        if (clientDTO!=null){
            clientDTO=mainController.getClientByName(clientDTO.getFullName());
            informationComponentController.updateClientUser();
            paymentComponentController.updateClientUser();
        }
    }

    public void increaseYaz(){
        updateClientInfo();
    }
    public int startInlayProcess(List<LoanDTO> loansToInvest, String clientName){
         return mainController.startInlayProcess(loansToInvest,clientName);
    }
    public bodyUser(){}
    public void setDataForNewFile(){
        scrambleComponentController.initializeInlayData();
        informationComponentController.setInformationDataForNewFile();
    //    paymentComponentController.setPaymentsDataForNewFile();
    }
    public double getClientBalance(){return clientDTO.getCurrBalance();}
    public List<String> getCategories(){return mainController.getCategories();}
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public void showData(){
        informationComponentController.showData();
        paymentComponentController.showData();
    }
    public void chargeAccount(double amount){
        mainController.chargeAccount(clientDTO.getFullName(),amount);
    }
    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms){
        return mainController.findMatchLoans(clientName,terms);
    }
    public ClientDTO getClientDTO(){return clientDTO;}
    public void withdrawFromAccount(String clientName, double amount) throws NotEnoughMoney {
        mainController.withdrawFromAccount(clientName,amount);
    }

}
