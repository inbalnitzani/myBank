package app.header;

import app.constParameters;
import app.main.AppController;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import dto.ClientDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class headerController {
    @FXML private ComboBox<String> userOptions;
    @FXML private Label file;
    @FXML private Label yaz;
    @FXML private ComboBox<String> skin;

    private SimpleIntegerProperty currYaz;
    private AppController mainController;

    @FXML public void initialize() {
        yaz.setText("No file in system");
        initUserOptions();
    }

    @FXML void chooseUser(ActionEvent event) {
        mainController.changeUser(userOptions.getValue());
    }

    public Label getYaz() {
        return yaz;
    }

    public void initUserOptions() {
        userOptions.getItems().add("Admin");
        skin.getItems().add("DEFAULT");
        skin.getItems().add("PINK");
        skin.getItems().add("COLOURFUL");
    }

    @FXML
    void chooseSkin(ActionEvent event) {
        switch (skin.getValue()) {
      //      case DEFAULT:
       //         mainController.setSkin("Default");
       //         break;
        //    case PINK:
         //       mainController.setSkin("Default");
          //      break;
           // case COLOURFUL:
            //    mainController.setSkin("Default");
             //   break;
        }
    }


    public headerController() {
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setUsersComboBox() {
        if (mainController.isFileInSystem()) {
            userOptions.getItems().clear();
            userOptions.getItems().add(constParameters.ADMIN);
        }
        for (ClientDTO clientDTO : mainController.getClients())
            userOptions.getItems().add(clientDTO.getFullName());
    }

    public void updateComponentForNewFile(String path){
        setUsersComboBox();
        updateFileName(path);
    }
    public void updateFileName(String fileName) {
        file.setText(fileName);
    }
}
