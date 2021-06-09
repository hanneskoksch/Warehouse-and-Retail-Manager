package core;


import core.interfaces.IDisplayEssentials;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Class to deliver data for table in gui
 */
public class DisplayStock implements IDisplayEssentials {

    private static ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static Logger log = LogManager.getLogger(DisplayStock.class);


    /**
     * function to open local JSON-File with saved items
     */
    public void readJson() {

        JSONParser parser = new JSONParser();
        JSONArray stock;

        try (FileReader reader = new FileReader("stock.json")) {

            stock = (JSONArray) parser.parse(reader);
            makeList(stock);

        } catch (Exception e) {
            log.warn(e);
        }


    }

    /**
     * Transforms the content of the local JSON-File into an observable list to add items to the table
     * @param stock contains data from local JSON-File
     */
    private void makeList(JSONArray stock){
        itemList.clear();
        for (int i = 0; i < stock.size(); i++) {

            JSONObject s = (JSONObject) stock.get(i);
            String name = (String) s.get("name");
            long itemnr = (long) s.get("itemnr");
            long amount = (long)s.get("amount");
            String category = (String) s.get("category");
            double currentPrice = (double)s.get("price");
            String description = (String) s.get("description");
            itemList.add(new Item(name, (int)itemnr, (int)amount, category, (float)currentPrice, description));

        }

    }

    /**
     * Adds a transmitted item to the itemList
     * @param itemobject Item object
     */
    @Override
    public void addItemToList(Item itemobject){
        itemList.add(itemobject);
    }



    /**
     * Grands access to itemList for the MainController-Class
     * @return itemList
     */
    @Override
    public ObservableList<Item> getProductData() {
        ObservableList<Item> returnItems = itemList;
        return returnItems;
    }




}
