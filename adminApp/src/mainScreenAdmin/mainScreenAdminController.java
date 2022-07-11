package mainScreenAdmin;

import dto.ClientDTO;
import homePage.adminHomePageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import loginAdmin.adminAppController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class mainScreenAdminController {

    @FXML private BorderPane mainComponent;
    private Parent homePageComponent;
    private Parent loginComponent;
    private adminHomePageController homePageController;
    private adminAppController LoginAppController;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    //public String getClientName(){
    //    return homePageController.getAdminName();
    //}

    public adminHomePageController getHomePageController() {
        return homePageController;
    }

    @FXML public void initialize() throws IOException {
        loadLoginPage();
        mainComponent.setTop(loginComponent);
    }

    public void loadLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/loginAdmin/adminLogin.fxml");
        fxmlLoader.setLocation(url);
        loginComponent = fxmlLoader.load(url.openStream());
        LoginAppController = fxmlLoader.getController();
        LoginAppController.setMainController(this);
    }

    public void loginSuccess(/*String name*/) throws IOException {
        mainComponent.getChildren().clear();
        loadHomePage();
       // homePageController.setName(name);
        mainComponent.setTop(homePageComponent);
    }

    public void loadHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/homePage/adminHomePage.fxml");
        fxmlLoader.setLocation(url);
        homePageComponent = fxmlLoader.load(url.openStream());
        homePageController = fxmlLoader.getController();
        homePageController.setMainController(this);
    }


    public List<ClientDTO> getClients() {
        return LoginAppController.getClients();
    }
}
