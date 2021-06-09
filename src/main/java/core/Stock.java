package core;

import ControllFx.MainController;
import javafx.collections.ObservableList;
import core.exceptions.IdNotUniqueException;
import core.exceptions.ValuesNotValidException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Class to add new items into the local JSON-File
 */
public class Stock{

    private DisplayStock d = new DisplayStock();
    private DisplayCart c = new DisplayCart();
    private boolean unique = true;
    private static int currentitemnr;

    private static Logger log = LogManager.getLogger(Stock.class);

    /**
     * Method to create a new item-type object and save it into the JSON-File
     * @param name name
     * @param itemnr itemnr
     * @param amount amount
     * @param category category
     * @param price price
     * @param description description
     */
    public void createItem(String name, int itemnr, int amount, String category, float price, String description) throws IdNotUniqueException {

        if(checkitemnr(itemnr)) {
            Item item = new Item(name, itemnr, amount, category, price, description);
            d.addItemToList(item);
            /*writeItem(item);*/
        }

    }

    /**
     * write list to JSON-File
     */
    public void writeList(){

        ObservableList itemList = d.getProductData();
        JSONArray stock = new JSONArray();
        for(int i=0;i<itemList.size();i++){
            Item current = (Item)itemList.get(i);
            stock.add(current.getItemAsJSONObject());
        }
        try (FileWriter file = new FileWriter("stock.json")) {
            file.write(stock.toJSONString());
        } catch (IOException e) {
            log.warn(e);
        }
    }

    /**
     * checks if itemnr is unique
     * @param itemnr itemnr
     * @return boolean if itemnr is unique
     */
    private boolean checkitemnr(int itemnr) throws IdNotUniqueException {
        ObservableList<Item> items = d.getProductData();

        unique = true;
        Stream<Item> streamI = items.parallelStream();
        streamI.forEach(i -> checkID(i.getItemnr(),itemnr));

        if(!unique){
            throw new IdNotUniqueException("Item-Number: '"+itemnr+"' is already in use");
        }
        return unique;
    }

    private void checkID(int currentItem, int itemnr) {
        if(currentItem == itemnr){
            unique = false;
        }
    }

    /**
     * sets item details
     * @param currentItem selected item
     */
    public void itemDetails(Item currentItem){
        if(currentItem != null){
            currentitemnr = currentItem.getItemnr();
            MainController m = new MainController();
            m.setDescription(currentItem.getDescription(),currentItem.getCategory());
            m.setImage(Integer.toString(currentItem.getItemnr()));
        }
    }

    /**
     * method to delete selected item from the stock itemlist
     */
    public void deleteItem(){
        Item o = getCurrentItem();
        if(o != null) {
            ObservableList<Item> items = d.getProductData();
            items.remove(o);
            writeList();
            log.info("Item deleted: " + o.getName() + " [" + o.getItemnr() + "]");
        }
    }

    /**
     * methed to delete the image of an selected item
     */
    public void deleteImage(){
        Item o = getCurrentItem();
        if(o != null) {
            File image = new File("src/main/resources/" + o.getItemnr() + ".jpg");
            image.delete();
        }
    }

    /**
     * Returns currently selected Item
     * @return Item
     */
    public Item getCurrentItem(){
        ObservableList<Item> items = d.getProductData();

        for(int i=0;i<items.size();i++){
            Item o = items.get(i);
            if(o.getItemnr()==currentitemnr){
                return o;
            }
        }
        return null;
    }

    /**
     * Checks entered values for an Item-Object
     * @param name
     * @param itemnr
     * @param amount
     * @param category
     * @param price
     * @param description
     * @return
     * @throws ValuesNotValidException
     */
    public boolean checkValues(String name, String itemnr, String amount, String category, String price, String description) throws ValuesNotValidException {

        boolean valid= true;
        String message="";

        if((itemnr==null||name==null||amount==null||category==null||price==null||description==null)&&valid){
            message="Pflichtfeld nicht gesetzt";
            throw new ValuesNotValidException(message);
        }
        if((itemnr.equals("")||amount.equals("")||name.equals("")||category.equals("")||price.equals("")||description.equals(""))&&valid){
            message="Pflichtfeld nicht gesetzt";
            valid = false;
        }


        if(itemnr.length()!=6 && valid) {
            message="Item Nummer muss 6 stellig sein!";
            valid = false;
        }

        if(itemnr.charAt(0)=='0'){
            message="Item Nummer darf nicht mit 0 starten";
            valid = false;
        }

        for(int i=0;i<itemnr.length();i++){
            int check = Integer.valueOf(itemnr.charAt(i));
            if(check<48||check>57){
                message="Nur Zahlen für Item Nummer zulässig!";
                valid = false;
            }
        }

        for(int i=0;i<amount.length();i++){
            int check = Integer.valueOf(amount.charAt(i));
            if(check<48||check>57){
                message="Nur Zahlen für Anzahl zulässig!";
                valid = false;
            }
        }
        int initiallength = price.length();
        price = price.replace(",","");
        price = price.replace(".","");
        if(price.length()<initiallength-1&&valid){
            message="Nur ein , oder . erlaubt";
            valid = false;
        }
        if(price.equals("")&&valid){
            message="Preis muss Zahlenwerte enthalten";
            valid = false;
        }
        for(int i=0;i<price.length();i++){
            int check = Integer.valueOf(price.charAt(i));
            if(check<48||check>57){
                message="Nur Zahlen für Preis zulässig";
                valid = false;
            }
        }

        if(description.length()>100){
            message="Beschreibung zu lang";
            valid = false;
        }



        if(valid){
            return true;
        }else{
            throw new ValuesNotValidException(message);
        }
    }


    /**
     * Modifies amount of current item in stock and cart.
     * @param amount in mainscreen chosen amount.
     */
    public void addItemToCart(int amount) {
        Item o = getCurrentItem();
        if(o != null) {
            if (o.getAmount() > 0) {
                c.addItemToList(o);
                o.setAmountInCart(amount);
                writeList();
            }
        }
    }


}
