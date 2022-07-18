package app.createLoan;
import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class createLoanController {
    @FXML
    private Button approveButton;
    @FXML
    private TextField name;
    @FXML
    private TextField amount;
    @FXML
    private TextField interest;
    @FXML
    private TextField pace;
    @FXML
    private TextField totalTime;
    @FXML
    private Label errorAmount;
    @FXML
    private Label errorName;
    @FXML
    private Label errorInterest;
    @FXML
    private Label errorTotalTime;
    @FXML
    private Label errorPace;
    @FXML
    private Label errorApprove;
    @FXML
    private Label errorCategory;
    @FXML
    private ComboBox<String> categories;
    @FXML
    private TextField addNewCategory;
    private clientHomePageController homePageController;

    public void setHomePageController(clientHomePageController controller) {
        this.homePageController = controller;
    }
    @FXML public void initialize() {
    }
    @FXML void createNewLoanButton(ActionEvent event) {
        String loanName = name.getText().trim();
        if (loanName != null && loanName != "") {
            boolean validDetails = checkAmount();
            validDetails &= checkInterest();
            validDetails &= checkTotalTime();
            validDetails &= checkPace();
            validDetails &= checkCategory();
            if (validDetails) {
                generateNewLoan();
            }
        }
    }
    public boolean checkAmount() {
        String amountString = amount.getText();
        Double amountNumber;
        boolean vaildInput = false;
        try {
            amountNumber = Double.parseDouble(amountString);
            if (amountNumber > 0) {
                errorAmount.setText("");
                vaildInput = true;
            }
        } finally {
            if (!vaildInput) {
                errorAmount.setText("Please enter a positive amount!");
                amount.clear();
            }
            return vaildInput;
        }
    }
    public List<String> getCategories(String categoriesJSON) {
        Gson gson = new Gson();
        List<String> categories = null;
        try {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            categories = gson.fromJson(categoriesJSON, listType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return categories;
        }
    }
    public void setCategories() {
        categories.getItems().clear();
        categories.getItems().addAll(homePageController.getCategories());
    }
    public boolean checkInterest() {
        String interestString = interest.getText();
        Integer interestNumber;
        boolean validInput = false;
        try {
            interestNumber = Integer.parseInt(interestString);
            if (interestNumber > 0 && interestNumber <= 100) {
                errorInterest.setText("");
                validInput = true;
            }
        } finally {
            if (!validInput) {
                errorInterest.setText("Please enter an integer between 1 to 100!");
                interest.clear();
            }
            return validInput;
        }
    }
    public boolean checkPace() {
        String paceString = pace.getText();
        Integer paceNumber;
        boolean validInput = false;
        try {
            paceNumber = Integer.parseInt(paceString);
            if (paceNumber < 0) {
                errorPace.setText("Please enter a positive number");
            } else {
                String totalTimeForRefundString = totalTime.getText();
                if (totalTimeForRefundString != null) {
                    Integer totalTimeForRefundInteger = Integer.parseInt(totalTimeForRefundString);
                    if (totalTimeForRefundInteger % paceNumber == 0) {
                        errorPace.setText("");
                        validInput = true;
                    } else {
                        errorPace.setText("Please enter a valid pace!");
                    }
                }
            }
        } catch (Exception e) {
            errorPace.setText("Please enter a positive integer!");
            pace.clear();
        } finally {
            return validInput;
        }
    }
    public boolean checkTotalTime() {
        String totalTimeString = totalTime.getText();
        Integer totalTimeNumber;
        boolean validInput = false;
        try {
            totalTimeNumber = Integer.parseInt(totalTimeString);
            if (totalTimeNumber > 0) {
                errorTotalTime.setText("");
                validInput = true;
            }
        } finally {
            if (!validInput) {
                errorTotalTime.setText("Please enter a positive integer!");
                totalTime.clear();
            }
            return validInput;
        }

    }
    public boolean checkCategory() {
        String addNewCategoryString = addNewCategory.getText();
        String chosenCategoryString = categories.getValue();
        boolean validInput = false;
        if (!addNewCategoryString.equals("") && chosenCategoryString != null) {
            errorCategory.setText("Please choose one from the two options..");
            categories.setValue("");
            addNewCategory.setText("");
        } else if (addNewCategoryString.equals("") && chosenCategoryString == null) {
            errorCategory.setText("Please choose category!");
        } else {
            String category = addNewCategory.getText();
            if (category != "") {
                if (categories.getItems().contains(category)) {
                    categories.setValue(category);
                    addNewCategory.setText("");
                }
            }
            errorCategory.setText("");
            validInput = true;
        }
        return validInput;
    }
    public String getChosenCategory() {
        String category = addNewCategory.getText();
        if (category.equals(""))
            return categories.getValue();
        return category;
    }
    public void generateNewLoan() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/newLoan")
                .newBuilder()
                .addQueryParameter("loanName", name.getText())
                .addQueryParameter("owner", homePageController.getClientName())
                .addQueryParameter("Amount", amount.getText())
                .addQueryParameter("Interest", interest.getText())
                .addQueryParameter("Category", getChosenCategory())
                .addQueryParameter("TotalTime", totalTime.getText())
                .addQueryParameter("Pace", pace.getText())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    errorApprove.setText("Unknown error occurred! Loan has not been added to system. PLease try again!");
                    clearAllLabel();
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                int status = response.code();
                if (status == HttpServletResponse.SC_OK) {
                    Platform.runLater(() -> {
                        errorApprove.setText("Loan added successfully!");
                        synchronized (this) {
                            if (addNewCategory.getText() != "") {
                                homePageController.updateCategories();
                                //updateSomeOneToKnowAll!!!!!!!!!!!!!!
                            }
                        }
                        homePageController.newLoansInSystem();
                    });
                } else {
                    Platform.runLater(() -> {
                        String responseBody = null;
                        try {
                            responseBody = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        errorApprove.setText(responseBody);
                    });
                }
            }
        });
        clearAllLabel();
    }
    public void clearAllLabel() {
        name.setText("");
        amount.setText("");
        interest.setText("");
        pace.setText("");
        totalTime.setText("");
        errorAmount.setText("");
        errorName.setText("");
        errorInterest.setText("");
        errorTotalTime.setText("");
        errorPace.setText("");
        errorCategory.setText("");
        addNewCategory.setText("");
    }
    public void refreshData(){
        categories.getItems().clear();;
        categories.getItems().addAll(homePageController.getCategories());
    }

    public void setDisable(){
        name.setDisable(true);
        amount.setDisable(true);
        interest.setDisable(true);
        categories.setDisable(true);
        pace.setDisable(true);
        totalTime.setDisable(true);
        addNewCategory.setDisable(true);
        approveButton.setDisable(true);

    }
    public void setAble(){
        name.setDisable(false);
        amount.setDisable(false);
        interest.setDisable(false);
        categories.setDisable(false);
        pace.setDisable(false);
        totalTime.setDisable(false);
        addNewCategory.setDisable(false);
        approveButton.setDisable(false);
    }
}