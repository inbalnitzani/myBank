package app.mainScreenClient;

import app.homePage.clientHomePageController;
import app.loginClient.clientLoginAppController;
import engine.Bank;
import engine.BankInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class mainScreenClientController {

    @FXML private BorderPane mainClientComponent;
    private clientLoginAppController clientLoginAppController;
    private Parent loginComponent;
    private clientHomePageController homePageController;
    private Parent homePageComponent;

    @FXML public void initialize() throws IOException {
        loadLoginPage();
        mainClientComponent.setTop(loginComponent);
    }


    public Parent getHomePageComponent() {
        return homePageComponent;
    }

    public clientHomePageController getHomePageController() {
        return homePageController;
    }

    public void loadLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/app/loginClient/clientLoginPage.fxml");
        fxmlLoader.setLocation(url);
        loginComponent = fxmlLoader.load(url.openStream());
        clientLoginAppController = fxmlLoader.getController();
        clientLoginAppController.setMainController(this);
    }
    public void loadHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/app/homePage/homePageClient.fxml");
        fxmlLoader.setLocation(url);
        homePageComponent = fxmlLoader.load(url.openStream());
        homePageController = fxmlLoader.getController();
        homePageController.setMainController(this);
    }

    public void loginClientSuccess(String clientName) throws IOException {
        mainClientComponent.getChildren().clear();
        loadHomePage();
        homePageController.setClientName(clientName);
        homePageController.setAccountBalance(0.0);
        mainClientComponent.setTop(homePageComponent);
    }

    public String getClientName(){
        return homePageController.getClientName();
    }


//    @FXML private BorderPane mainComponent;
//    private app.header.headerController headerController;
//    private Parent headerComponent;
//    private bodyAdmin bodyAdminController;
//    private bodyUser bodyUserController;
//    private CustomerAppController loginController;
//    private Parent loginComponentRoot;
//    private Parent adminComponentRoot;
//    private Parent userComponentRoot;
//    private BankInterface myBank;
//    private SimpleBooleanProperty fileInSystem;
//    private SimpleIntegerProperty time;
//
//    @FXML public void initialize() throws IOException {
//        if (loginController != null) {
//            loginController.setMainController(this);
//        }
//        loadLoginScreen();
//        mainComponent.setTop(loginComponentRoot);
//    }
//
//    public AppController() {
//        myBank = new Bank();
//        fileInSystem = new SimpleBooleanProperty(false);
//        time = new SimpleIntegerProperty();
//    }
//
//    public void setDataUser(){
//        bodyUserController.setDataForNewFile();
//    }
//
//    public void loadHeader(String clientName) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("/app/header/header.fxml");
//        fxmlLoader.setLocation(url);
//        headerComponent = fxmlLoader.load(url.openStream());
//        headerController = fxmlLoader.getController();
//        headerController.setMainController(this);
//        headerController.updateClientName(clientName);
//    }
//
//    public void loginClientSuccess(String client) throws IOException {
//        loadAdmin();
//        loadUser();
//        loadHeader(client);
//        mainComponent.getChildren().clear();
//        mainComponent.setTop(headerComponent);
//        headerController.getYaz().textProperty().bind(time.asString());
//    }
//
//    public void loadLoginScreen() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("/app/loginClient/clientLoginPage.fxml");
//        fxmlLoader.setLocation(url);
//        loginComponentRoot = fxmlLoader.load(url.openStream());
//        loginController = fxmlLoader.getController();
//        loginController.setMainController(this);
//    }
//
//    public void loadAdmin() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource(BODY_ADMIN_PATH);
//        fxmlLoader.setLocation(url);
//        adminComponentRoot = fxmlLoader.load(url.openStream());
//        bodyAdminController = fxmlLoader.getController();
//        bodyAdminController.setMainController(this);
//    }
//
//    public void loadUser() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource(BODY_USER_PATH);
//        fxmlLoader.setLocation(url);
//        userComponentRoot = fxmlLoader.load(url.openStream());
//        bodyUserController = fxmlLoader.getController();
//        bodyUserController.setMainController(this);
//    }
//
//    public void changeUser(String user) {
//        if (user != null) {
//            if (user.equals("Admin")) {
//                mainComponent.setCenter(adminComponentRoot);
//                if (fileInSystem.getValue()) {
//                    bodyAdminController.updateData();
//                } else {
//                    bodyAdminController.updateNoFileAdminScreen();
//                }
//            } else {
//                mainComponent.setCenter(userComponentRoot);
//                bodyAdminController.clearErrors();
//                bodyUserController.updateUserViewer(user);
//                bodyUserController.showData();
//            }
//        }
//    }
//
//    public int startInlayProcess(List<LoanDTO> loansToInvest, String clientName){
//        return myBank.startInlayProcess(loansToInvest,clientName);
//    }
//
//    public void setUserOptions() {
//        headerController.setUsersComboBox();
//    }
//
//    public boolean isFileInSystem() {
//        return fileInSystem.get();
//    }
//
//    public boolean increaseYaz() {
//        if(isFileInSystem()){
//            myBank.promoteTime();
//            time.setValue(myBank.getWorldTime());
//            bodyUserController.increaseYaz();
//        }
//        return isFileInSystem();
//    }
//
//    public void showError(Exception err){
//        Stage popUpWindow = new Stage();
//        popUpWindow.setTitle("Error - opening file");
//        Button button = new Button("Close");
//        button.setOnAction(error -> popUpWindow.close());
//        Label label = new Label(err.toString());
//        VBox vbox = new VBox(5);
//        vbox.setAlignment(Pos.CENTER);
//        vbox.getChildren().addAll(label, button);
//        ScrollPane layout = new ScrollPane();
//        layout.setContent(vbox);
//        Scene scene = new Scene(vbox,500,100);
//        popUpWindow.setScene(scene);
//        popUpWindow.show();
//    }
//
//    public boolean getFile(String path) {
//        boolean validFile=false;
//        try{
//            myBank.getXMLFile(path);
//            headerController.updateComponentForNewFile(path);
//            fileInSystem.set(true);
//            time.setValue(myBank.getWorldTime());
//            validFile=true;
//        } catch (Exception err) {
//            showError(err);
//        }return validFile;
//    }
//
//    public List<LoanDTO> getLoans(){return myBank.getAllLoans();}
//
//    public Collection<ClientDTO> getClients(){
//        return myBank.getClients();
//    }
//
//    public ClientDTO getClientByName(String name){
//        return myBank.getClientByName(name);
//    }
//
//    public List<String> getCategories(){return myBank.getCategories();}
//
//    public List<LoanDTO> findMatchLoans(String clientName, LoanTerms terms){
//        return myBank.findMatchLoans(clientName,terms);
//    }
//
//    public void chargeAccount(String clientName, double amount) {
//        myBank.loadMoney(clientName,(int)amount);
//    }
//
//    public int getTime() {
//        return time.get();
//    }
//
//    public void payAllBack(String loanID) throws NotEnoughMoney {
//        myBank.payAllBack(loanID);
//    }
//
//    public void payBackNextPayment(String loanID, double totalAmount,int yaz) throws NotEnoughMoney {
//        myBank.payBackNextPayment(loanID,totalAmount,yaz);}
//
//    public void withdrawFromAccount(String clientName, double amount) throws NotEnoughMoney {
//        myBank.withdrawMoneyFromAccount(clientName, amount);
//    }
//
//    public void payApartOfDebt(String loanID, double amount) throws NotEnoughMoney {
//        myBank.payApartOfDebt(loanID,amount);
//    }
}

