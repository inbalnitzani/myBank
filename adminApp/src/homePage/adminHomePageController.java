package homePage;

import clientsList.clientsListRefresher;
import com.google.gson.Gson;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.stateDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mainScreenAdmin.mainScreenAdminController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.controlsfx.control.ToggleSwitch;
import org.jetbrains.annotations.NotNull;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class adminHomePageController {

    private mainScreenAdminController mainController;
    @FXML private TableView<ClientDTO> clientsInfoTable;
    @FXML TableColumn<ClientDTO, String> idNameCol;
    @FXML TableColumn<ClientDTO, Integer> currBalanceCol;
    @FXML private TableView<LoanDTO> loans;
    @FXML private Label hello;
    @FXML private Label YAZlabel;
    @FXML private TextField chooseYaz;
    @FXML private Button rewindButton;
    @FXML private Label enterYazLabel;
    private Integer time;
    private Boolean refresh = true;

    private Timer timer;
    private TimerTask listRefresher;


    @FXML
    void Rewind(ActionEvent event) {
        if (rewindButton.getText().equals("REWIND")) {
            chooseYaz.setDisable(false);
            enterYazLabel.setText("Please enter a Yaz to review and press ENTER ");
            refresh = false;
        }
        else {
            stopRewind();
        }

    }
    void stopRewind(){
        chooseYaz.setDisable(true);
        rewindButton.setText("REWIND");
        enterYazLabel.setText(" ");
        chooseYaz.clear();
        refresh = true;

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/stopRewind")
                .newBuilder().addQueryParameter("yaz",String.valueOf(0))
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
            }
        });
    }
    void lookingBack(Integer yaz){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/rewind")
                .newBuilder().addQueryParameter("yaz",String.valueOf(yaz))
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                stateDTO state = gson.fromJson(json,stateDTO.class);

                Platform.runLater(()->{
                    refresh = true;
                    showClients(state.getClients());
                    showLoanData(state.getLoans());
                    refresh = false;
                });
            }
        });
    }
    @FXML
    void adminnPickRewind(ActionEvent event) {
        try {
            Integer wantedYaz = Integer.parseInt(chooseYaz.getText());
            if (wantedYaz > time - 1 || wantedYaz < 1) {
                enterYazLabel.setText("Please enter a positive integer between 1 and the current yaz (" + time + ")");
            }
            else {
                enterYazLabel.setText("looking back on yaz: " + wantedYaz +
                        ". Please enter a Yaz to review and press ENTER ");
                rewindButton.setText("STOP REWIND");
                lookingBack(wantedYaz);
            }
        } catch (Exception e) {
            enterYazLabel.setText("Please enter a positive integer between 1 and the current yaz (" + time+ ")");
        } finally {
            chooseYaz.clear();
        }
    }
    @FXML
    public void initialize(String name) {
        hello.setText("Hello " + name);
        startListRefresher();
        setYaz();
        chooseYaz.setDisable(true);
    }
    public void setYAZlabel(int currYaz){        YAZlabel.setText("Current yaz is: " + currYaz);
    }
    public void setYaz(){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/yaz")
                .newBuilder()
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                String  currYaz = response.header("yaz");

                Platform.runLater(()->{
                    setYAZlabel(Integer.parseInt(currYaz));
                    time = Integer.parseInt(currYaz);
                });
            }
        });
    }

    @FXML
    void increaseYaz(ActionEvent event) {
      String finalUrl = HttpUrl
              .parse("http://localhost:8080/demo_Web_exploded/increaseYaz")
              .newBuilder()
              .build()
              .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                int currYaz = gson.fromJson(json,int.class);
                Platform.runLater(()->{
                    setYAZlabel(currYaz);
                    time = currYaz;
                });
            }
        });

    }

    public void startListRefresher() {
            listRefresher = new clientsListRefresher(this::showClients, this::showLoanData);
            timer = new Timer();
            timer.schedule(listRefresher, 0, 2000);
    }
    public void setMainController(mainScreenAdminController mainController) {
        this.mainController = mainController;
    }

    public void showClients(List<ClientDTO> clients) {
        if(refresh) {

            Platform.runLater(() -> {
                clientsInfoTable.getColumns().clear();

                idNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                currBalanceCol.setCellValueFactory(new PropertyValueFactory<>("currBalance"));

                clientsInfoTable.getColumns().addAll(idNameCol, currBalanceCol);
                clientsInfoTable.setItems(FXCollections.observableArrayList(clients));


            });
        }
    }


    public void showLoanData(List<LoanDTO> loansList) {
        if(refresh) {

            Platform.runLater(() -> {
                loans.getColumns().clear();
                ObservableList<LoanDTO> loansData = FXCollections.observableArrayList();
                loansData.addAll(mainController.getLoans());

                TableColumn<LoanDTO, String> idCol = new TableColumn<>("ID loan");
                TableColumn<LoanDTO, String> ownerNameCol = new TableColumn<>("Owner");
                TableColumn<LoanDTO, String> categoryCol = new TableColumn<>("Category");
                TableColumn<LoanDTO, Integer> capitalCol = new TableColumn<>("Capital");
                TableColumn<LoanDTO, Integer> totalTimeCol = new TableColumn<>("Total time");
                TableColumn<LoanDTO, Integer> interestCol = new TableColumn<>("Interest");
                TableColumn<LoanDTO, Integer> paceCol = new TableColumn<>("Payment pace");
                TableColumn<LoanDTO, String> statusCol = new TableColumn<>("Status");

                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                ownerNameCol.setCellValueFactory(new PropertyValueFactory<>("owner"));
                categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
                capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));
                totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalYazTime"));
                interestCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
                paceCol.setCellValueFactory(new PropertyValueFactory<>("pace"));
                statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        /*loans.setOnMouseClicked(event -> {
            LoanDTO loan =loans.getSelectionModel().getSelectedItem();
            if (loan != null){
                VBox vBox= createDetailNodeByLoanStatus(loan);
                loansDetail.setDetailNode(new ScrollPane(vBox));
            }
        });*/
                loans.getColumns().addAll(idCol, ownerNameCol, categoryCol, capitalCol, totalTimeCol, interestCol, paceCol, statusCol);
                loans.setItems(FXCollections.observableArrayList(loansList));
                //setLoansInfo();
            });
        }
    }

}
