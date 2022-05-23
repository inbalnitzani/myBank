package app.main;
import app.bodyAdmin.bodyAdmin;
import app.bodyUser.bodyUser;
import app.header.headerController;
import bank.Bank;
import bank.BankInterface;
import dto.ClientDTO;
import dto.LoanDTO;
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

    @FXML private headerController headerComponentController;
    @FXML private Parent headerComponent;
    @FXML private BorderPane mainComponent;
    private bodyAdmin bodyAdminController;
    private bodyUser bodyUserController;
    private Parent adminComponentRoot;
    private Parent userComponentRoot;
    private BankInterface myBank;
    private SimpleBooleanProperty fileInSystem;
    private SimpleIntegerProperty time;

    @FXML
    public void initialize() throws IOException {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
        loadAdmin();
        loadUser();
    }

    public AppController() {
        myBank = new Bank();
        fileInSystem = new SimpleBooleanProperty(false);
        time = new SimpleIntegerProperty();
    }

    public void setDataUser(){
        bodyUserController.setData();
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

    public void updateDataByViewer(String user) {
        if (user != null) {
            if (user.equals("Admin")) {
                mainComponent.setCenter(adminComponentRoot);
            } else {
                mainComponent.setCenter(userComponentRoot);
                bodyUserController.updateUserViewer(user);
                bodyUserController.showData();
            }
        }
    }

    public int startInlayProcess(List<LoanDTO> loansToInvest, String clientName){
        return myBank.startInlayProcess(loansToInvest,clientName);
    }

    public void setUserOptions() {
        headerComponentController.setUsers();
    }

    public boolean isFileInSystem() {
        return fileInSystem.get();
    }

    public void increaseYaz() {
        myBank.promoteTime();
        time.setValue(myBank.getWorldTime());
    }

    public void showError(Exception err){
        Stage popUpWindow = new Stage();
        Button button = new Button("Close");
        popUpWindow.setTitle("Error - opening file");
        button.setOnAction(error -> popUpWindow.close());
        Label label = new Label();
        label.setText(err.toString());
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, button);
        ScrollPane layout = new ScrollPane();
        layout.setContent(vbox);
        Scene scene = new Scene(vbox,500,100);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }

    public void getFile(String path) {
        try {
            myBank.getXMLFile(path);
            fileInSystem.setValue(true);
            time.setValue(myBank.getWorldTime());
            headerComponentController.getYaz().textProperty().bind(time.asString());
            headerComponentController.updateFileName(path);
            setUserOptions();
        } catch (Exception err) {
            showError(err);
        }
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
        myBank.loadMoney(clientName,amount);
    }
public void withdrawFromAccount(String clientName, double amount){myBank.withdrawMoneyFromAccount(clientName,amount);}
}
