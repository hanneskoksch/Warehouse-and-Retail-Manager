package ControllFx;

import java.io.*;

import core.interfaces.IItemController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import core.Categories;
import core.exceptions.IdNotUniqueException;
import core.Stock;
import core.exceptions.ValuesNotValidException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewItemController implements IItemController{

    @FXML
    public TextField productField;
    @FXML
    public TextField itemnrField;
    @FXML
    public TextField amountField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField descriptionField;
    @FXML
    private javafx.scene.control.Button secondaryButton;
    @FXML
    private ChoiceBox categoryDropdown;
    @FXML
    private Label dropped;
    @FXML
    private Label label;
    @FXML
    private Label itemnrLabel;
    @FXML
    private Label titleLabel;

    private static Logger log = LogManager.getLogger(NewItemController.class);

    private File dest;
    private File src;

    /**
     * method to create item-create UI
     *
     * @throws IOException is never thrown
     */
    public void switchToWindow() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewItem.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Neuen Artikel anlegen");
            stage.setResizable(false);
            stage.getIcons().add(new Image("icon.png"));
            Scene scene = new Scene(root1);
            scene.getStylesheets().add("style_item.css");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            log.warn("Cant load new Window:");
            log.warn(e);
        }
    }

    /**
     * creates item and closes the item-create UI
     */
    public void editItem() throws IdNotUniqueException, ValuesNotValidException {

        Stock stock = new Stock();
        try {
            if (stock.checkValues(productField.getText(), itemnrField.getText(), amountField.getText(), (String) categoryDropdown.getValue(), priceField.getText(), descriptionField.getText())) {

                String product = productField.getText();
                int itemnr = Integer.parseInt(itemnrField.getText());
                int amount = Integer.parseInt(amountField.getText());
                String category = (String) categoryDropdown.getValue();
                String pricefloat = priceField.getText();
                pricefloat = pricefloat.replace(",", ".");
                Float price = Float.valueOf(pricefloat);
                String description = descriptionField.getText();

                stock.createItem(product, itemnr, amount, category, price, description);
                stock.writeList();
                log.info("Item added: " +  product + " [" + itemnr + "]");

                dest = new File("src/main/resources/"+itemnrField.getText()+".jpg");
                try {
                    FileUtils.copyFile(src,dest);
                } catch (IOException e) {
                    titleLabel.setText("Angaben nicht vollständig/fehlerhaft: "+"kein Bild vorhanden!");
                    log.warn(e);
                } catch (Exception e) {
                    titleLabel.setText("Angaben nicht vollständig/fehlerhaft: "+"kein Bild vorhanden!");
                    log.warn(e);
                }

                Stage stage = (Stage) secondaryButton.getScene().getWindow();
                stage.close();

            } else {
                itemnrLabel.setText("Artikelnummer");
                itemnrLabel.setStyle("-fx-text-fill: black;");
                titleLabel.setText("Angaben nicht vollständig/fehlerhaft");
                titleLabel.setStyle("-fx-text-fill: red;");
                System.out.println("Angaben fehlerhaft");
            }
        }catch(IdNotUniqueException idexcep){
            log.warn(idexcep);
            titleLabel.setText("Neuen Artikel anlegen");
            titleLabel.setStyle("-fx-text-fill: black;");
            itemnrLabel.setText("Artikelnummer muss eindeutig sein!");
            itemnrLabel.setStyle("-fx-text-fill: red;");
        }catch(ValuesNotValidException valueexcep){
            log.warn(valueexcep);
            itemnrLabel.setText("Artikelnummer");
            itemnrLabel.setStyle("-fx-text-fill: black;");
            String message = valueexcep.getMessage();
            titleLabel.setText("Angaben nicht vollständig/fehlerhaft: "+message);
            titleLabel.setStyle("-fx-text-fill: red;-fx-font-size: 12;");
        }


    }


    /**
     * initializing the category dropdown and the drag & drop field
     */
    @FXML
    public void initialize() {
        // get the dropdown content
        categoryDropdown.setItems(Categories.getCategoryList());

        // Drag & Drop
        label.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != label && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            }
        });

        label.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    dropped.setText(db.getFiles().toString());
                    String file = db.getFiles().toString();
                    src = new File(file.substring(1, file.length()-1));
                    if(src.isFile()){
                        log.debug("Added Image for Item successfully");
                    }
                    success = true;
                }
                /* let the source know whether the string was successfully transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }


    /**
     * closes the item-create UI
     */
    public void switchToPrimary() {
        Stage stage = (Stage) secondaryButton.getScene().getWindow();
        stage.close();
    }

}


