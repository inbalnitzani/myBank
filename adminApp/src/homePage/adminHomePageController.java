package homePage;

import dto.ClientDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mainScreenAdmin.mainScreenAdminController;

public class adminHomePageController {

    private mainScreenAdminController mainController;
    @FXML
    private TableView<ClientDTO> clientsInfoTable;
    @FXML
    TableColumn<ClientDTO, String> idNameCol;
    @FXML
    TableColumn<ClientDTO, Integer> currBalanceCol;

    public void setMainController(mainScreenAdminController mainController) {
        this.mainController = mainController;
    }

    private void showClients() {
        clientsInfoTable.getColumns().clear();

        idNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        currBalanceCol.setCellValueFactory(new PropertyValueFactory<>("currBalance"));

        clientsInfoTable.getColumns().addAll(idNameCol, currBalanceCol);
        clientsInfoTable.setItems(FXCollections.observableArrayList(mainController.getClients()));
        //clientsDetail.setMasterNode(clientsInfoTable);
        //clientsDetail.setDetailSide(Side.RIGHT);    }

    }
}
