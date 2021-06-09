package ControllFx;

import java.io.IOException;

import extentions.BillFactory;
import core.interfaces.IBills;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import core.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartController {

    @FXML
    private Label PriceTotal;
    @FXML
    private TableView<Item> productTable;
    @FXML
    private TableColumn<Item, String> NameColumn;
    @FXML
    private TableColumn<Item, Integer> NrColumn;
    @FXML
    private TableColumn<Item, Integer> AmountColumn;
    @FXML
    private TableColumn<Item, Float> PriceColumn;
    @FXML
    private TableColumn<Item, Float> PriceTotalColumn;
    @FXML
    private ChoiceBox billCategory;
    @FXML
    private javafx.scene.control.Button success;

    private static ObservableList<String> billtypes = FXCollections.observableArrayList("Gewerbekunden", "Privatkunden");
    private static Logger log = LogManager.getLogger(CartController.class);





    /**
     * method to create shopping cart UI
     *
     * @throws IOException is never thrown
     */
    @FXML
    public void switchToCart() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cart.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Warenkorb");
            stage.setResizable(false);
            stage.getIcons().add(new Image("icon.png"));
            Scene scene = new Scene(root2);
            scene.getStylesheets().add("style_item.css");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            log.warn("Cant load new Window: " + e);
        }
    }

    /**
     * Is called on start-up of the window
     */
    @FXML
    private void initialize() {
        DisplayCart d = new DisplayCart();
        Cart c = new Cart();
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameFX());
        NrColumn.setCellValueFactory(cellData -> cellData.getValue().getItemnrFX().asObject());
        AmountColumn.setCellValueFactory(cellData -> cellData.getValue().getamountInCartFX().asObject());
        PriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceFX().asObject());
        PriceTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceTotalFX().asObject());
        productTable.setItems(d.getProductData());
        PriceTotal.setText("Gesamt: " + c.calculateTotalPrice() + "€");


        if (billCategory.getItems().size() == 0) {
            billCategory.setItems(billtypes);
            billCategory.getSelectionModel().selectFirst();
        }

    }

    /**
     * Creates bill of the entire order
     */
    public void CompleteOrder() {
        IBills bill = BillFactory.createBill(billCategory.getValue().toString());
        bill.printBill();

        DisplayCart dc = new DisplayCart();
        dc.resetCart();

        Stage stage = (Stage) success.getScene().getWindow();
        stage.close();


    }


}
