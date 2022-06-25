package app.main;
import app.bodyAdmin.bodyAdmin;
import app.bodyUser.bodyUser;
import app.header.headerController;
import app.loginClient.CustomerAppController;
import engine.Bank;
import engine.BankInterface;
import dto.ClientDTO;
import dto.LoanDTO;
import exception.NotEnoughMoney;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loan.LoanTerms;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import static app.constParameters.BODY_ADMIN_PATH;
import static app.constParameters.BODY_USER_PATH;

public class AppController {

    @FXML private BorderPane mainComponent;
    private headerController headerController;
    private Parent headerComponent;
    private bodyAdmin bodyAdminController;
    private bodyUser bodyUserController;
    private CustomerAppController loginController;
    private Parent loginComponentRoot;
    private Parent adminComponentRoot;
    private Parent userComponentRoot;
    private BankInterface myBank;
    private SimpleBooleanProperty fileInSystem;
    private SimpleIntegerProperty time;

    @FXML public void initialize() throws IOException {
        if (loginController != null) {
            loginController.setMainController(this);
        }
        loadLoginScreen();
        mainComponent.setTop(loginComponentRoot);
    }

    public AppController() {
        myBank = new Bank();
        fileInSystem = new SimpleBooleanProperty(false);
        time = new SimpleIntegerProperty();
    }

    public void setDataUser(){
        bodyUserController.setDataForNewFile();
    }

    public void loadHeader(String clientName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/app/header/header.fxml");
        fxmlLoader.setLocation(url);
        headerComponent = fxmlLoader.load(url.openStream());
        headerController = fxmlLoader.getController();
        headerController.setMainController(this);
        headerController.updateClientName(clientName);
    }

    public void loginClientSuccess(String client) throws IOException {
        loadAdmin();
        loadUser();
        loadHeader(client);
        mainComponent.getChildren().clear();
        mainComponent.setTop(headerComponent);
        headerController.getYaz().textProperty().bind(time.asString());
    }

    public void loadLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/app/loginClient/customerLoginPage.fxml");
        fxmlLoader.setLocation(url);
        loginComponentRoot = fxmlLoader.load(url.openStream());
        loginController = fxmlLoader.getController();
        loginController.setMainController(this);
    }

    public void loadAdmin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(BODY_ADMIN_PATH);
        fxmlLoader.setLocation(url);
        adminComponentRoot = fxmlLoader.load(url.openStream());
        bodyAdminController = fxmlLoader.getController();
        bodyAdminController.setMainController(this);
    }

    public void loadUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(BODY_USER_PATH);
        fxmlLoader.setLocation(url);
        userComponentRoot = fxmlLoader.load(url.openStream());
        bodyUserController = fxmlLoader.getController();
        bodyUserController.setMainController(this);
    }

    public void changeUser(String user) {
        if (user != null) {
            if (user.equals("Admin")) {
                mainComponent.setCenter(adminComponentRoot);
                if (fileInSystem.getValue()) {
                    bodyAdminController.updateData();
                } else {
                    bodyAdminController.updateNoFileAdminScreen();
                }
            } else {
                mainComponent.setCenter(userComponentRoot);
                bodyAdminController.clearErrors();
                bodyUserController.updateUserViewer(user);
                bodyUserController.showData();
            }
        }
    }

    public int startInlayProcess(List<LoanDTO> loansToInvest, String clientName){
        return myBank.startInlayProcess(loansToInvest,clientName);
    }

    public void setUserOptions() {
        headerController.setUsersComboBox();
    }

    public boolean isFileInSystem() {
        return fileInSystem.get();
    }

    public boolean increaseYaz() {
        if(isFileInSystem()){
            myBank.promoteTime();
            time.setValue(myBank.getWorldTime());
            bodyUserController.increaseYaz();
        }
        return isFileInSystem();
    }

    public void showError(Exception err){
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle("Error - opening file");
        Button button = new Button("Close");
        button.setOnAction(error -> popUpWindow.close());
        Label label = new Label(err.toString());
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, button);
        ScrollPane layout = new ScrollPane();
        layout.setContent(vbox);
        Scene scene = new Scene(vbox,500,100);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    public boolean getFile(String path) {
        boolean validFile=false;
        try{
            myBank.getXMLFile(path);
            headerController.updateComponentForNewFile(path);
            fileInSystem.set(true);
            time.setValue(myBank.getWorldTime());
            validFile=true;
        } catch (Exception err) {
            showError(err);
        }return validFile;
    }

    public List<LoanDTO> getLoans(){return myBank.getAllLoans();}

    public Collection<ClientDTO> getClients(){
        return myBank.getClients();
    }

    public ClientDTO getClientByName(String name){
        return myBank.getClientByName(name);
    }

    public List<String> getCategories(){return myBank.getCategories();}

    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms){
        return myBank.findMatchLoans(clientName,terms);
    }

    public void chargeAccount(String clientName, double amount) {
        myBank.loadMoney(clientName,(int)amount);
    }

    public int getTime() {
        return time.get();
    }

    public void payAllBack(String loanID) throws NotEnoughMoney {
        myBank.payAllBack(loanID);
    }

    public void payBackNextPayment(String loanID, double totalAmount,int yaz) throws NotEnoughMoney {
        myBank.payBackNextPayment(loanID,totalAmount,yaz);}

    public void withdrawFromAccount(String clientName, double amount) throws NotEnoughMoney {
        myBank.withdrawMoneyFromAccount(clientName, amount);
    }

    public void payApartOfDebt(String loanID, double amount) throws NotEnoughMoney {
        myBank.payApartOfDebt(loanID,amount);
    }
}
