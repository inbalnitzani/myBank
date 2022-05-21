package app.inlayController;

import dto.LoanDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import loan.LoanTerms;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class inlayController {

    private bodyUser bodyUser;
    @FXML private TextField amountToInvest;
    @FXML private TextField chooseMinTime;
    @FXML private ComboBox<String> chooseMinInterest;
    @FXML private CheckComboBox<String> CheckComboBox;
    @FXML private Label errorAmount;
    @FXML private Label errorMinTime;
    @FXML private TableView<LoanDTO> optionalLoans;

    private LoanTerms setInterestTerm(LoanTerms terms) {
        String minInterestString = chooseMinInterest.getValue();
        if (minInterestString != null)
            terms.setMinInterestForTimeUnit(Integer.parseInt(minInterestString));
        return terms;
    }

    private LoanTerms setTimeTerm(LoanTerms terms){
        String minTimeString = chooseMinTime.getCharacters().toString();
        if (!minTimeString.equals("")) {
            terms.setMinTimeForLoan(Integer.parseInt(minTimeString));
        }
        return terms;
    }

    private LoanTerms setCategoriesTerm(LoanTerms terms) {
        Set<String> chosenCategory = new HashSet<>();
        ObservableList<String> userChoose = CheckComboBox.getCheckModel().getCheckedItems();
        if (userChoose.size() == 0) {
            chosenCategory.addAll(bodyUser.getCategories());
        } else {
            for (String category : userChoose)
                chosenCategory.add(category);
        }
        terms.setCategories(chosenCategory);
        return terms;
    }

    private LoanTerms updateTerms() {
        LoanTerms terms = new LoanTerms();
        terms.setMaxAmount(Integer.parseInt(amountToInvest.getCharacters().toString()));
        terms= setTimeTerm(terms);
        terms=setCategoriesTerm(terms);
        terms=setInterestTerm(terms);
        return terms;
    }

    @FXML void startInlay(ActionEvent event) {
        if (checkMandatoryCategories())
        {
            LoanTerms terms=updateTerms();
            List<LoanDTO> matchLoans= bodyUser.findMatchLoans(bodyUser.getClientDTO().getFullName(),terms);

            TableRowExpanderColumn<LoanDTO> expander = new TableRowExpanderColumn<>(this::createEditor);

            TableColumn<LoanDTO,String> idCol= new TableColumn<>("id");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<LoanDTO,String> ownerCol= new TableColumn<>("owner");
            ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));

            optionalLoans.getColumns().addAll(expander,idCol,ownerCol);
            optionalLoans.setItems(FXCollections.observableArrayList(matchLoans));
        }
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<LoanDTO> arg) {
        Pane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("src/app/bodyAdmin/bodyAdmin.fxml"));
        } catch (Exception err) {
            pane = new Pane();
        }
        return pane;
    }
    public boolean checkMandatoryCategories() {
        if (amountToInvest.getCharacters().toString().equals("")) {
            errorAmount.setText("Please choose amount!");
            return false;
        }
        return true;
    }

    @FXML public void initialize() {

    }

    @FXML void checkMinTime(ActionEvent event) {
        String input = chooseMinTime.getCharacters().toString().trim();
        boolean validInput=false;
        int number;
        try {
            number = Integer.parseInt(input);
            if (number < 0) {
                errorMinTime.setText(number + " is a negative number!\nPlease enter a positive number");
            } else {
                validInput=true;
                errorMinTime.setText("");
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorMinTime.setText("");
                chooseMinTime.clear();
            } else {
                errorMinTime.setText(input + " is not a number.\nPlease enter a number");
            }
        } finally {
            if (!validInput)
                chooseMinTime.setText("");
        }
    }

    @FXML void checkAmountToInvest(ActionEvent event) {
        String input = amountToInvest.getCharacters().toString().trim();
        boolean validInput = false;
        double number;
        try {
            number = Double.parseDouble(input);
            if (number < 0) {
                errorAmount.setText(number + " is a negative number!\nPlease enter a positive amount.");
            } else {
                double currBalance = bodyUser.getClientBalance();
                if (number > currBalance) {
                    errorAmount.setText("Current balance is: " + currBalance + ".\nPlease enter amount lower than +" + currBalance);
                } else {
                    validInput = true;
                    errorAmount.setText("");
                }
            }
        } catch (Exception err) {
            if (input.equals("")) {
                errorAmount.setText("");
                amountToInvest.clear();
            } else {
                errorAmount.setText(input + " is not a number.\nPlease enter a number");
            }
        } finally {
            if (!validInput)
                amountToInvest.setText("");
        }
    }

    public void setBodyUser(bodyUser bodyUser) {
        this.bodyUser = bodyUser;
    }

    public void setChooseMinInterest() {
        for (int i = 1; i <= 100; i++) {
            chooseMinInterest.getItems().add(Integer.toString(i));
        }
    }

    public void setCategoriesChooser(){
        CheckComboBox.getItems().addAll(bodyUser.getCategories());
    }


}
