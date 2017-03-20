package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Product;
import model.ProductType;

/**
 * Created by qwerty on 20-Mar-17.
 */
public class EditController {

    @FXML
    private TextField name;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox<ProductType> type;
    @FXML
    CheckBox available;

    private Stage editStage;
    private Product product;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
        type.getItems().addAll(ProductType.Drink,ProductType.Food,ProductType.Medicament);
        type.getSelectionModel().selectFirst();
    }

    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }

    public void setProduct(Product product) {
        this.product = product;

        name.setText(product.getName());
        amount.setText(Integer.toString(product.getAmount()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if(isInputValid()) {
            product.setName(name.getText());
            product.setAmount(Integer.parseInt(amount.getText()));
            product.setType(type.getValue());
            product.setAvailable(available.isSelected());

            okClicked = true;
            editStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        editStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Nie podales imienia!\n";
        }

        if (amount.getText() == null || amount.getText().length() == 0) {
            errorMessage += "Nie podales ilosci produktow!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(amount.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Bledna ilosc produktow. Podaj liczbe!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(editStage);
            alert.setTitle("Blednie podane pola");
            alert.setHeaderText("Popraw pola");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
