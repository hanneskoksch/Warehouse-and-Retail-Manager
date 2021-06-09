package ControllFx;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import core.Categories;
import core.DisplayStock;
import core.Item;
import core.Stock;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Controller of the main GUI
 */
public class MainController {

    @FXML private ComboBox categoryDropdown;
    @FXML private TableView<Item> productTable;
    @FXML private TableColumn<Item, String> NameColumn;
    @FXML private TableColumn<Item, Integer> NrColumn;
    @FXML private TableColumn<Item, Integer> AmountColumn;
    @FXML private TableColumn<Item, Float> PriceColumn;
    @FXML private ImageView imgview;
    private static ImageView staticimg;
    @FXML private TextField filterField;
    @FXML private TextField AmountToCart ;

    @FXML
    public static Label a;
    public static Label b;
    @FXML
    public Label dtext;
    public Label ctext;

    private DisplayStock d = new DisplayStock();
    private Stock stockitems = new Stock();

    private static Logger log = LogManager.getLogger(MainController.class);


    /**
     * Calls pop-up window to create Items
     */
    @FXML
    private void switchToSecondary() {
        NewItemController n = new NewItemController();
        try{
            n.switchToWindow();
        }catch(Exception e){
            log.warn(e);
        }
    }

    @FXML
    private void switchToEdit() {
        if(stockitems.getCurrentItem()!=null){
            EditController n = new EditController();
            try{
                n.switchToWindow();
            }catch(Exception e) {
                log.warn(e);
            }
        }

    }

    @FXML
    private void switchToCart() {
        CartController m = new CartController();
        try{
            m.switchToCart();
        }catch(Exception e){
            log.warn(e);
        }
    }

    @FXML
    private void PlusAmountToCart(){
        Item i = stockitems.getCurrentItem();
        if(i != null) {
            int amount = Integer.parseInt(AmountToCart.getText());
            if (amount > i.getAmount()) {
                amount = i.getAmount();
            } else if (amount < i.getAmount()) {
                amount++;
            }
            AmountToCart.setText(String.valueOf(amount));
        }
    }
    @FXML
    private void MinusAmountToCart() throws IOException {
        int amount = Integer.parseInt(AmountToCart.getText());
        if(amount >= 2){amount--;}
        AmountToCart.setText(String.valueOf(amount));
    }

    /**
     * initializing the item-table, the category dropdown and text-fields
     */
    @FXML
    private void initialize() {
        d.readJson();
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameFX());
        NrColumn.setCellValueFactory(cellData -> cellData.getValue().getItemnrFX().asObject());
        AmountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountFX().asObject());
        PriceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceFX().asObject());
        productTable.setItems(d.getProductData());
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> stockitems.itemDetails(newValue));
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (amountInCart, oldValue, newValue) -> AmountToCart.setText(String.valueOf(1)));
        a = dtext;
        b = ctext;
        filter();
        AmountToCart.setText("1");
        staticimg = imgview;

        if (categoryDropdown.getItems().size() == 0) {
            // create Categories-List for dropdown menus
            Categories c = new Categories();
            c.readJson();
            categoryDropdown.setItems(Categories.getCategoryList());
        }
    }

    /**
     * Search method
     */
    private void filter(){
        FilteredList<Item> filteredData = new FilteredList<>(d.getProductData());

        categoryDropdown.getSelectionModel().selectedItemProperty().addListener((observableC, oldValueC, newCValue) -> {
                    filteredData.setPredicate((product) -> {

                        String search = filterField.getText();
                        String lowerCaseFilter = search.toLowerCase();
                        String itemNrString = String.valueOf(product.getItemnr());
                        String category = (String) categoryDropdown.getValue();

                        if(newCValue==null){
                            if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;}
                            else if (itemNrString.contains(lowerCaseFilter)) {
                                return true;}
                        }

                        boolean insearch = false;

                        if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                            insearch = true;
                        } else if (itemNrString.contains(lowerCaseFilter)) {
                            insearch = true;
                        }

                        if (product.getCategory().equals(category) && insearch) {
                            return true;
                        }

                        return false; // Does not match.
                    });
                });

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((product) -> {
                if (newValue == null || newValue.isEmpty()) {
                    if(categoryDropdown.getValue() == null){
                        return true;
                    }else{
                        String c = (String)categoryDropdown.getValue();
                        if (product.getCategory().equals(c)) {
                            return true;
                        }
                    }
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String itemNrString = String.valueOf(product.getItemnr());

                boolean inCategory = true;

                if(categoryDropdown.getValue()!=null){
                    String category = (String) categoryDropdown.getValue();
                    if(product.getCategory().equals(category)){
                        inCategory = true;
                    }else{
                        inCategory = false;
                    }
                }

                if (product.getName().toLowerCase().contains(lowerCaseFilter)&&inCategory) {
                    return true;}
                else if (itemNrString.contains(lowerCaseFilter)&&inCategory) {
                    return true;}

                return false; // Does not match.
            });
        });
        SortedList<Item> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedData);

    }


    /**
     * method to display information about an selected item
     * @param description item description
     * @param category item category
     */
    public void setDescription(String description,String category){
        a.setText(description);
        b.setText(category);
    }

    /**
     * method to set the Item-image if image exists
     * @param name name of the selected Item
     */
    public void setImage(String name){
        Image i = new Image("kein_bild_vorhanden.jpg");

        String path2 = "src/main/resources/"+name+".png";
        File fpng = new File(path2);
        String path = "src/main/resources/"+name+".jpg";
        File fjpg = new File(path);

        name = name.toLowerCase();

        if(fjpg.exists()){

            i = new Image(name+".jpg");

        }else if(fpng.exists()){

            i = new Image(name+".png");

        }else{
            System.out.println("Kein Bild vorhanden");
        }
        staticimg.setImage(i);

    }

    /**
     * calls method that deletes an Item from the stock
     */
    @FXML
    private void deleteItem(){
        stockitems.deleteImage();
        stockitems.deleteItem();
    }

    /**
     * Method to clear selection of category dropdown list
     */
    @FXML
    private void clearSelection(){
        categoryDropdown.setValue(null);
    }

    /**
     * Method to pull chosen amount from textfield and transfer it to the cart list.
     */
    @FXML
    public void addItemToCart() {
        stockitems.addItemToCart(Integer.parseInt(AmountToCart.getText()));
        initialize();
    }

}

