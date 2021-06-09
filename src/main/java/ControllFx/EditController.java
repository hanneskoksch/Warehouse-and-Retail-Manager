package ControllFx;

import java.io.IOException;

import core.interfaces.IItemController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import core.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import core.exceptions.IdNotUniqueException;
import core.exceptions.ValuesNotValidException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller of the editItem window
 */
public class EditController implements IItemController {

    @FXML
    private javafx.scene.control.Button secondaryButton;
    @FXML ChoiceBox categories;
    @FXML TextField product;
    @FXML Label itemnr;
    @FXML TextField amount;
    @FXML TextField price;
    @FXML TextField description;
    @FXML
    private Label titleLabel;
    private Stock stock = new Stock();

    private static Logger log = LogManager.getLogger(EditController.class);

    /**
     * method to create item-create UI
     *
     * @throws IOException is never thrown
     */
    @Override
    public void switchToWindow() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditItem.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Artikel bearbeiten");
            stage.setResizable(false);
            stage.getIcons().add(new Image("icon.png"));
            Scene scene = new Scene(root2);
            scene.getStylesheets().add("style_item.css");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            log.warn("Cant load new Window: ");
            log.warn(e);
        }
    }

    /**
     * method to replace old item with new parameters
     */
    @FXML
    public void editItem() throws IdNotUniqueException, ValuesNotValidException {

        try {
            if (stock.checkValues(product.getText(), itemnr.getText(), amount.getText(), (String) categories.getValue(), price.getText(), description.getText())) {

                String productText = product.getText();
                int nrtext = Integer.parseInt(itemnr.getText());
                int amounttext = Integer.parseInt(amount.getText());
                String categorytext = (String) categories.getValue();
                String pricefloat = price.getText();
                pricefloat = pricefloat.replace(",", ".");
                Float pricetext = Float.valueOf(pricefloat);
                String descriptiontext = description.getText();


                stock.deleteItem();
                stock.createItem(productText, nrtext, amounttext, categorytext, pricetext, descriptiontext);
                stock.writeList();
                log.info("Item added edited: " + productText + " [" + nrtext + "]");

                Stage stage = (Stage) secondaryButton.getScene().getWindow();
                stage.close();
            }
        }catch(ValuesNotValidException valueexcep){
            log.warn(valueexcep);
            String message = valueexcep.getMessage();
            titleLabel.setText("Angaben nicht vollständig/fehlerhaft: "+message);
            titleLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12;");
        }


    }

    /**
     * close window
     */
    @FXML
    public void switchToPrimary() {
        Stage stage = (Stage) secondaryButton.getScene().getWindow();
        stage.close();
    }

    /**
     * initialize function - sets old values for Item properties
     */
    @FXML
    public void initialize() {
        categories.setItems(Categories.getCategoryList());
        Item i = stock.getCurrentItem();
        product.setText(i.getName());
        itemnr.setText(Integer.toString(i.getItemnr()));
        amount.setText(Integer.toString(i.getAmount()));
        price.setText(Float.toString(i.getPrice()));
        description.setText(i.getDescription());
        categories.setValue(i.getCategory());
    }

}



