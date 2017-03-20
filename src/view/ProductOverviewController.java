package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Main;
import model.Product;
import model.ProductType;

import java.io.IOException;

/**
 * Created by qwerty on 19-Mar-17.
 */
public class ProductOverviewController {

    @FXML
    private TableView<Product> glowna;

    @FXML
    private TableColumn<Product, String> pierwsza;
    @FXML
    private TableColumn<Product, Double> druga;
    @FXML
    private Label typ;
    @FXML
    private Label nazwa;
    @FXML
    private Label ilosc;
    @FXML
    private Label dostep;
    @FXML
    private Label typinfo;
    @FXML
    private Label nazwainfo;
    @FXML
    private Label iloscinfo;
    @FXML
    private Label dostepinfo;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    public ObservableList<Product> getproducts()
    {
        products.add(new Product("Jajko",3, ProductType.Food,true));
        products.add(new Product("Cola",57,ProductType.Drink,true));
        products.add(new Product("Sprite",20,ProductType.Drink,true));
        products.add(new Product("Ibuprofen",2,ProductType.Medicament,false));
        return products;
    }

    public ObservableList<Product> getProductData() {
        return products;
    }

    public ProductOverviewController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        pierwsza.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        druga.setCellValueFactory(new PropertyValueFactory<Product, Double>("amount"));
        glowna.setItems(getproducts());

        showProductdetails(null);

        glowna.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductdetails(newValue));
    }

    public void showProductdetails(Product product)
    {
        if(product != null)
        {
            typinfo.setText(product.getType().toString());
            nazwainfo.setText(product.getName());
            iloscinfo.setText(Integer.toString(product.getAmount()));
            if(product.isAvailable()==true)
            {
                dostepinfo.setText("Dostepny");
            }
            else {
                dostepinfo.setText("Niedostepny");
            }
        }
        else
        {
            typinfo.setText("");
            nazwainfo.setText("");
            iloscinfo.setText("");
            dostepinfo.setText("");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        int selectedIndex = glowna.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            glowna.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Blad");
            alert.setHeaderText("Nie wybrano produktu");
            alert.setContentText("Najpierw wybierz produkt z tabeli a nastepnie ponow probe");

            alert.showAndWait();
        }
    }

    public boolean showPersonEditDialog(Product product) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/Edit.fxml"));
            GridPane page = (GridPane) loader.load();
            Stage editStage = new Stage();
            editStage.setTitle("Edycja Osoby");
            editStage.initModality(Modality.WINDOW_MODAL);
            //editStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editStage.setScene(scene);

            EditController controller = loader.getController();
            controller.setEditStage(editStage);
            controller.setProduct(product);


            editStage.showAndWait();
            glowna.refresh();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleNewProduct() {
        Product product = new Product();
        boolean okClicked = showPersonEditDialog(product);
        if (okClicked) {
            getProductData().add(product);
        }
    }
    @FXML
    private void handleEditPerson() {
        Product selectedProduct = glowna.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = showPersonEditDialog(selectedProduct);
            if (okClicked) {
                showProductdetails(selectedProduct);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //alert.initOwner(Main.getPrimaryStage());
            alert.setTitle("Nie wybrano osoby");
            alert.setHeaderText("Nie wybrano osoby");
            alert.setContentText("Wybierz osobe z tabeli");

            alert.showAndWait();
        }
    }
}
